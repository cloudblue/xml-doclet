package com.automation.xmldoclet;

import com.automation.xmldoclet.xjc.Class;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test group for Tags
 */
public class TagTest extends AbstractTestParent {

    /**
     * testing a simple tags
     */
    @Test
    public void testTag1() {
        String[] sourceFiles = new String[] {"./src/test/java/com/automation/xmldoclet/simpledata/Tag1.java"};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), "com.automation.xmldoclet.simpledata");
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getTag().size(), 7);
        assertEquals(classNode.getMethod().get(0).getTag().size(), 3);
    }

}
