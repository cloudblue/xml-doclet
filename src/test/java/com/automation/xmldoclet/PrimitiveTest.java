package com.automation.xmldoclet;

import org.junit.jupiter.api.Test;

/**
 * Unit test group for Primitives
 */
public class PrimitiveTest extends AbstractTestParent {

    /**
     * Rigorous Parser :-)
     */
    @Test
    public void test() {
        executeJavadoc(null, new String[] {"./src/test/java"}, null, null, new String[] {"com"},
            new String[] {"-dryrun"});
    }
}
