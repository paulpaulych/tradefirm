package paulpaulych.tradefirm.config.orm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import paulpaulych.utils.ResourceLoader
import simpleorm.core.CachingDefaultRepoFactory
import simpleorm.core.RepoRegistry
import simpleorm.core.RepoRegistryProvider
import simpleorm.core.delegate.JdbcDelegateCreator
import simpleorm.core.filter.HashMapFilterResolverRepo
import simpleorm.core.proxy.CglibDelegateProxyGenerator
import simpleorm.core.proxy.repository.CglibRepoProxyGenerator
import simpleorm.core.schema.OrmSchema
import simpleorm.core.schema.naming.INamingStrategy
import simpleorm.core.schema.naming.toSnakeCase
import simpleorm.core.schema.yaml.ast.YamlSchemaCreator
import simpleorm.core.sql.SimpleQueryGenerator
import javax.annotation.PostConstruct

@Configuration
class OrmConfig(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    private val namingStrategy = PlainObjectNamingStrategy()

    fun ormSchema(): OrmSchema{
        return YamlSchemaCreator(
                ResourceLoader.loadText("orm-schema.yml"),
                namingStrategy
        ).create()
    }

    @PostConstruct
    fun repoRegistry(){
        val ormSchema = ormSchema()
        val jdbcOperations = SpringJdbcAdapter(jdbcTemplate)
        val queryGenerator = SimpleQueryGenerator()
        val filterResolverRepo = HashMapFilterResolverRepo(ormSchema)
        val repoProxyGenerator = CglibRepoProxyGenerator(
                ormSchema,
                jdbcOperations,
                SimpleQueryGenerator(),
                CglibDelegateProxyGenerator(
                        ormSchema,
                        JdbcDelegateCreator(
                                jdbcOperations,
                                queryGenerator
                        )
                ),
                filterResolverRepo
        )
        RepoRegistryProvider.repoRegistry = RepoRegistry(
                ormSchema().entities.map {
                    (kClass, _) -> kClass to repoProxyGenerator.createRepoProxy(kClass)
                }.toMap(),
                jdbcOperations,
                CachingDefaultRepoFactory(
                        jdbcOperations,
                        queryGenerator,
                        filterResolverRepo,
                        namingStrategy
                )
        )
    }

}

class PlainObjectNamingStrategy: INamingStrategy {
    override fun toColumnName(s: String): String {
        return toSnakeCase(s)
    }

    override fun toTableName(s: String): String {
        val s = toSnakeCase(s).removePrefix("plain_")
        println("table_name: $s")
        return s
    }
}