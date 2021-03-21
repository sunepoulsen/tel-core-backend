package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.db

import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.docker.DockerDeployment
import dk.sunepoulsen.tech.enterprise.labs.core.component.tests.docker.DockerIntegrator
import groovy.sql.Sql
import groovy.util.logging.Slf4j

@Slf4j
class DbHelper {
    private DockerDeployment deployment
    private String composeName
    private String containerName
    private String user
    private String password
    private String database

    DbHelper(DockerDeployment deployment, String composeName, String containerName, String user, String password, String database) {
        this.deployment = deployment
        this.composeName = composeName
        this.containerName = containerName
        this.user = user
        this.password = password
        this.database = database
    }

    void clearDatabase(List<String> tableNames) {
        String port = DockerIntegrator.findExternalPort("${composeName}_${containerName}_1", DockerIntegrator.PRIVATE_POSTGRES_PORT)

        Sql sql = Sql.newInstance("jdbc:postgresql://localhost:${port}/${database}", user, password, "org.postgresql.Driver")

        tableNames.each { it ->
            String executeSql = "DELETE FROM ${it}"
            log.info("Execute SQL against localhost:${port}/${database}: ${executeSql}")

            sql.execute(executeSql)
        }

        sql.close()
    }
}
