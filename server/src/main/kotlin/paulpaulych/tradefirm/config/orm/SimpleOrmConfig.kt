package paulpaulych.tradefirm.config.orm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import paulpaulych.utils.ResourceLoader
import simpleorm.core.CachingDefaultRepoFactory
import simpleorm.core.RepoRegistry
import simpleorm.core.RepoRegistryProvider
import simpleorm.core.delegate.JdbcDelegateCreator
import simpleorm.core.filter.*
import simpleorm.core.proxy.CglibDelegateProxyGenerator
import simpleorm.core.proxy.repository.CglibRepoProxyGenerator
import simpleorm.core.schema.OrmSchema
import simpleorm.core.schema.naming.INamingStrategy
import simpleorm.core.schema.naming.toSnakeCase
import simpleorm.core.schema.yaml.ast.YamlSchemaCreator
import simpleorm.core.sql.SimpleQueryGenerator
import javax.annotation.PostConstruct

@Configuration
class SimpleOrmConfig(
        @Autowired val jdbcTemplate: JdbcTemplate
) {

    private val namingStrategy = CrudObjectNamingStrategy()
    private val filterResolverRepo: IFilterResolverRepo
    private val ormSchema: OrmSchema

    init {
        this.ormSchema = YamlSchemaCreator(
                ResourceLoader.loadText("orm-schema.yml"),
                namingStrategy
        ).create()
        this.filterResolverRepo = HashMapFilterResolverRepo(ormSchema, mapOf(
                AndFilter::class to AndFilterResolver()
        ))
    }

    @PostConstruct
    fun repoRegistry(){
        val jdbcOperations = SpringJdbcAdapter(jdbcTemplate)
        val queryGenerator = SimpleQueryGenerator()

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
                ormSchema.entities.map {
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

class CrudObjectNamingStrategy: INamingStrategy {
    override fun toColumnName(s: String): String {
        val column = toSnakeCase(s)
        if(column.endsWith("_flag")){
            return "is_" + column.removeSuffix("_flag")
        }
        return column
    }

    override fun toTableName(s: String): String {
        return toSnakeCase(s).removePrefix("crud_")
    }
}

