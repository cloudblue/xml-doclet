package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.Annotation12;
import com.automation.xmldoclet.simpledata.Enum1;
import com.automation.xmldoclet.simpledata.Enum2;
import com.automation.xmldoclet.simpledata.Enum3;
import com.automation.xmldoclet.simpledata.Enum4;
import com.automation.xmldoclet.simpledata.Enum5;
import com.automation.xmldoclet.simpledata.Enum6;
import com.automation.xmldoclet.xjc.AnnotationArgument;
import com.automation.xmldoclet.xjc.AnnotationInstance;
import com.automation.xmldoclet.xjc.Enum;
import com.automation.xmldoclet.xjc.EnumConstant;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test group for Enumerations
 */
@SuppressWarnings("deprecation")
public class EnumTest extends AbstractTestParent {

    /**
     * Rigorous Parser :-)
     */
    @Test
    public void testSampledoc() {
        executeJavadoc(".", new String[] {"./src/test/java"}, null, null, new String[] {"com"},
            new String[] {"-dryrun"});
    }

    /**
     * testing a simple enum
     */
    @Test
    public void testEnum1() {
        String[] sourceFiles = new String[] {getSourceFile(Enum1.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Enum enumNode = packageNode.getEnum().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(1, packageNode.getEnum().size());
        assertEquals(0, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Enum1", enumNode.getName());
        assertEquals("Enum1", enumNode.getComment());
        assertEquals(getSimpledataPackage() + ".Enum1", enumNode.getQualified());
        assertEquals(3, enumNode.getConstant().size());
        assertEquals("a", enumNode.getConstant().get(0).getName());
        assertEquals("b", enumNode.getConstant().get(1).getName());
        assertEquals("c", enumNode.getConstant().get(2).getName());
    }

    /**
     * testing an empty enum
     */
    @Test
    public void testEnum2() {
        String[] sourceFiles = new String[] {getSourceFile(Enum2.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Enum enumNode = packageNode.getEnum().get(0);

        assertEquals("Enum2", enumNode.getName());
        assertEquals("Enum2", enumNode.getComment());
        assertEquals(getSimpledataPackage() + ".Enum2", enumNode.getQualified());
        assertEquals(0, enumNode.getConstant().size());
    }

    /**
     * testing enum comment
     */
    @Test
    public void testEnum3() {
        String[] sourceFiles = new String[] {getSourceFile(Enum3.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Enum enumNode = packageNode.getEnum().get(0);
        assertEquals("Enum3", enumNode.getComment());
    }

    /**
     * testing enum field comment
     */
    @Test
    public void testEnum4() {
        String[] sourceFiles = new String[] {getSourceFile(Enum4.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Enum enumNode = packageNode.getEnum().get(0);

        EnumConstant enumConstantNode = enumNode.getConstant().get(0);
        assertEquals("field1", enumConstantNode.getComment());
    }

    /**
     * testing single annotation
     */
    @Test
    public void testEnum5() {
        String[] sourceFiles = new String[] {getSourceFile(Enum5.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Enum enumNode = packageNode.getEnum().get(0);
        assertEquals(1, enumNode.getAnnotation().size());
        AnnotationInstance annotationInstanceNode = enumNode.getAnnotation().get(0);
        assertEquals("java.lang.Deprecated", annotationInstanceNode.getQualified());
    }

    /**
     * testing multiple annotation
     */
    @Test
    public void testEnum6() {
        String[] sourceFiles = new String[] {getSourceFile(Enum6.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Enum enumNode = packageNode.getEnum().get(0);
        assertEquals(2, enumNode.getAnnotation().size());

        AnnotationInstance annotationInstanceNode = enumNode.getAnnotation().get(0);
        assertEquals("java.lang.Deprecated", annotationInstanceNode.getQualified());
        assertEquals("Deprecated", annotationInstanceNode.getName());
        assertEquals(0, annotationInstanceNode.getArgument().size());

        annotationInstanceNode = enumNode.getAnnotation().get(1);
        assertEquals(Annotation12.class.getName(), annotationInstanceNode.getQualified());
        assertEquals(Annotation12.class.getSimpleName(), annotationInstanceNode.getName());
        assertEquals(1, annotationInstanceNode.getArgument().size());

        AnnotationArgument annotationArgumentNode = annotationInstanceNode.getArgument().get(0);
        assertEquals("value", annotationArgumentNode.getName());
        assertEquals("mister", annotationArgumentNode.getValue().get(0));

    }

}
