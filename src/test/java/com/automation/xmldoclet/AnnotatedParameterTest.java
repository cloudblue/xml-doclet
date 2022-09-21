package com.automation.xmldoclet;

import com.automation.xmldoclet.xjc.Method;
import com.automation.xmldoclet.xjc.Root;
import com.automation.xmldoclet.xjc.TypeInfo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnnotatedParameterTest extends AbstractTestParent {

    @Test
    public void testAnnotatedParameter() {
        String[] sourceFiles = new String[] {"./src/test/java/com/automation/xmldoclet/simpledata/AnnotatedParameter.java"};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Method methodNode = rootNode
            .getPackage().get(0)
            .getClazz().get(0)
            .getMethod().get(0);

        assertEquals("java.lang.String", getParamType(methodNode, 0).getQualified());
        assertEquals("java.util.List", getParamType(methodNode, 1).getQualified());
        assertEquals("java.lang.String", getParamType(methodNode, 1).getGeneric().get(0).getQualified());
        assertEquals("java.lang.String", getParamType(methodNode, 2).getQualified());
        assertEquals("[]", getParamType(methodNode, 2).getDimension());
        assertEquals("char", getParamType(methodNode, 3).getQualified());
        assertEquals("char", getParamType(methodNode, 3).getQualified());
        assertEquals("java.util.Map", getParamType(methodNode, 4).getQualified());
        assertEquals("[]", getParamType(methodNode, 5).getDimension());


        assertEquals("(java.lang.String, java.util.List<java.lang.String>, java.lang.String..., char, java.util.Map<java.lang.String,java.lang.Integer>, java.lang.String...)", methodNode.getSignature());
    }

    private static TypeInfo getParamType(Method methodNode, int index) {
        return methodNode.getParameter().get(index).getType();
    }
}
