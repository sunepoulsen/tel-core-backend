package dk.sunepoulsen.tech.enterprise.labs.core.service.utils

import org.junit.Test
import spock.lang.Specification

class SpringBootApplicationUtilsSpec extends Specification {
    @Test
    void testConstants() {
        expect: 'Scan components includes all packages across Tech Enterprise Labs'
            SpringBootApplicationUtils.COMPONENT_SCAN_PACKAGES == 'dk.sunepoulsen.tech.enterprise.labs'
    }
}
