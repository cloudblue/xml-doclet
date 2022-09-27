package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.RecordClass;
import com.automation.xmldoclet.xjc.Root;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordClassTest extends AbstractTestParent {

    @Test
    public void testRecord() {
        String[] sourceFiles = new String[] {SimpledataSupport.getSourceFile(RecordClass.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});
        System.out.println(getAsXml(rootNode));
        assertEquals(1, rootNode.getPackage().size());
    }
}
