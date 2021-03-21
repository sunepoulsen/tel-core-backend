package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.docker

import java.util.regex.Matcher
import java.util.regex.Pattern

class DockerIntegrator {
    static final String CONTAINER_NAME_FORMAT = "%s_%s_1"
    static final String CONTAINER_NAME_SUB_FORMAT = "%s_%s-%s_1"
    static Integer PRIVATE_NGINX_PORT = 80
    static Integer PRIVATE_POSTGRES_PORT = 5432
    static Integer PRIVATE_SERVICE_PORT = 8080

    static String findExternalPort( String containerName, Integer privatePort ) throws IOException {
        if( containerName == null ) {
            throw new IllegalArgumentException( "containerName can not be (null)" )
        }

        String processResult = "docker port ${containerName} ${privatePort}".execute().text

        Pattern pattern = Pattern.compile( "^(\\d\\.){3}\\d:(\\d+)\n" )
        Matcher matcher = pattern.matcher( processResult )

        if( matcher.matches() ) {
            return matcher.group( 2 )
        }

        throw new IOException( String.format( "Unable to find port for container %s: %s", containerName, processResult ) )
    }
}
