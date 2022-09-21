package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.Tag1;
import com.automation.xmldoclet.xjc.Class;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test group for Tags
 */
@SuppressWarnings("deprecation")
public class TagTest extends AbstractTestParent {

    /**
     * testing a simple tags
     */
    @Test
    public void testTag1() {
        String[] sourceFiles = new String[] {getSourceFile(Tag1.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(0, packageNode.getInterface().size());
        assertEquals(1, packageNode.getClazz().size());

        assertEquals(7, classNode.getTag().size());
        assertEquals(3, classNode.getMethod().get(0).getTag().size());
    }

}
