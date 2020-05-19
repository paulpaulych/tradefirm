package paulpaulych.tradefirm.config.orm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import paulpaulych.utils.ResourceLoader
import simpleorm.core.RepoRegistry
import simpleorm.core.RepoRegistryProvider
import simpleorm.core.delegate.JdbcDelegateCreator
import simpleorm.core.filter.HashMapFilterResolverRepo
import simpleorm.core.proxy.CglibDelegateProxyGenerator
import simpleorm.core.proxy.repository.CglibRepoProxyGenerator
import simpleorm.core.schema.OrmSchema
import simpleorm.core.schema.yaml.ast.YamlSchemaCreator
import simpleorm.core.sql.SimpleQueryGenerator
import javax.annotation.PostConstruct

@Configuration
class OrmConfig(
        @Autowired val jdbcTemplate: JdbcTemplate
) {


    fun ormSchema(): OrmSchema{
        return YamlSchemaCreator(
                ResourceLoader.loadText("orm-schema.yml")
        ).create()
    }

    @PostConstruct
    fun repoRegistry(){
        val ormSchema = ormSchema()
        val jdbcOperations = SpringJdbcAdapter(jdbcTemplate)

        val repoProxyGenerator = CglibRepoProxyGenerator(
                ormSchema,
                jdbcOperations,
                SimpleQueryGenerator(),
                CglibDelegateProxyGenerator(
                        ormSchema,
                        JdbcDelegateCreator(
                                jdbcOperations,
                                SimpleQueryGenerator()
                        )
                ),
                HashMapFilterResolverRepo(ormSchema)
        )
        RepoRegistryProvider.repoRegistry = RepoRegistry(
                ormSchema().entities.map {
                    (kClass, _) -> kClass to repoProxyGenerator.createRepoProxy(kClass)
                }.toMap(),
                jdbcOperations
        )
    }


}