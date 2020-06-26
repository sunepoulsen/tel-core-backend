package dk.sunepoulsen.tech.enterprise.labs.core.component.tests.util

import groovy.util.logging.Slf4j

@Slf4j
class ProcessUtils {

    static boolean execute(String exec, File directory) {
        log.info( 'Executing {}', exec )
        Process process = exec.execute([], directory)
        log.info( 'Standard output:\n{}', process.text)
        log.info( 'Exit code: {}', process.exitValue())

        return process.exitValue() == 0
    }

}
