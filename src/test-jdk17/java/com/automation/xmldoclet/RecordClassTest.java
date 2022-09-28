package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.RecordClass;
import com.automation.xmldoclet.xjc.Class;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import org.junit.jupiter.api.Test;

import static com.automation.xmldoclet.ReflectionSupport.assertStructureEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordClassTest extends AbstractTestParent {

    @Test
    public void testRecord() {
        String[] sourceFiles = new String[] {SimpledataSupport.getSourceFile(RecordClass.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});
        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        assertEquals(1, rootNode.getPackage().size());
        assertStructureEquals(RecordClass.class, classNode);
    }
}
