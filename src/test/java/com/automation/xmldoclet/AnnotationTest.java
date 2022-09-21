package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.Annotation1;
import com.automation.xmldoclet.simpledata.Annotation2;
import com.automation.xmldoclet.simpledata.Annotation3;
import com.automation.xmldoclet.xjc.Annotation;
import com.automation.xmldoclet.xjc.AnnotationElement;
import com.automation.xmldoclet.xjc.AnnotationInstance;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test group for Annotations
 */
@SuppressWarnings("deprecation")
public class AnnotationTest extends AbstractTestParent {

    /**
     * Rigorous Parser :-)
     */
    @Test
    public void testSampledoc() {
        executeJavadoc(null, new String[] {"./src/test/java"}, null, null, new String[] {"com"},
            new String[] {"-dryrun"});
    }

    /**
     * testing an annotation with nothing defined
     */
    @Test
    public void testAnnotation1() {
        String[] sourceFiles = new String[] {getSourceFile(Annotation1.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Annotation annotationNode = packageNode.getAnnotation().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(1, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(0, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Annotation1", annotationNode.getComment());
        assertEquals(Annotation1.class.getSimpleName(), annotationNode.getName());
        assertEquals(Annotation1.class.getName(), annotationNode.getQualified());
        assertEquals("public", annotationNode.getScope());
        assertEquals(0, annotationNode.getAnnotation().size());
        assertEquals(0, annotationNode.getElement().size());
        assertTrue(annotationNode.isIncluded());
    }

    /**
     * testing an annotation with an annotation decorating it
     */
    @Test
    public void testAnnotation2() {
        String[] sourceFiles = new String[] {getSourceFile(Annotation2.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Annotation annotationNode = packageNode.getAnnotation().get(0);
        AnnotationInstance annotationInstance = annotationNode.getAnnotation().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(1, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(0, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Annotation2", annotationNode.getComment());
        assertEquals(Annotation2.class.getSimpleName(), annotationNode.getName());
        assertEquals(Annotation2.class.getName(), annotationNode.getQualified());
        assertEquals("public", annotationNode.getScope());
        assertEquals(1, annotationNode.getAnnotation().size());
        assertEquals(0, annotationNode.getElement().size());
        assertTrue(annotationNode.isIncluded());

        // test annotation 'deprecated' on class
        assertEquals("java.lang.Deprecated", annotationInstance.getQualified());
        assertEquals("Deprecated", annotationInstance.getName());
        assertEquals(0, annotationInstance.getArgument().size());
    }

    /**
     * testing an annotation with one element field
     */
    @Test
    public void testAnnotation3() {
        String[] sourceFiles = new String[] {getSourceFile(Annotation3.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Annotation annotationNode = packageNode.getAnnotation().get(0);
        AnnotationElement element = annotationNode.getElement().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(1, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(0, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Annotation3", annotationNode.getComment());
        assertEquals(Annotation3.class.getSimpleName(), annotationNode.getName());
        assertEquals(Annotation3.class.getName(), annotationNode.getQualified());
        assertEquals("public", annotationNode.getScope());
        assertEquals(0, annotationNode.getAnnotation().size());
        assertEquals(1, annotationNode.getElement().size());
        assertTrue(annotationNode.isIncluded());

        // test annotation element
        assertEquals("id", element.getName());
        assertEquals(getSimpledataPackage() + ".Annotation3.id", element.getQualified());
        assertEquals("int", element.getType().getQualified());
        assertEquals(Integer.toString(3), element.getDefault());
    }

    /**
     * testing an annotation with non-public definition
     */
    @Test
    public void testAnnotation4() {
        String[] sourceFiles = new String[] {getSourceFile("Annotation4")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Annotation annotationNode = packageNode.getAnnotation().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(1, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(0, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Annotation4", annotationNode.getComment());
        assertEquals("Annotation4", annotationNode.getName());
        assertEquals(getSimpledataPackage() + ".Annotation4", annotationNode.getQualified());
        assertEquals("", annotationNode.getScope());
        assertEquals(0, annotationNode.getAnnotation().size());
        assertEquals(0, annotationNode.getElement().size());
        assertTrue(annotationNode.isIncluded());
    }
}