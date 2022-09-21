package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.Interface1;
import com.automation.xmldoclet.simpledata.Interface2;
import com.automation.xmldoclet.simpledata.Interface3;
import com.automation.xmldoclet.simpledata.Interface4;
import com.automation.xmldoclet.simpledata.Interface6;
import com.automation.xmldoclet.simpledata.Interface7;
import com.automation.xmldoclet.simpledata.Interface8;
import com.automation.xmldoclet.xjc.AnnotationInstance;
import com.automation.xmldoclet.xjc.Interface;
import com.automation.xmldoclet.xjc.Method;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import com.automation.xmldoclet.xjc.TypeParameter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test group for Interfaces
 */
@SuppressWarnings("deprecation")
public class InterfaceTest extends AbstractTestParent {

    /**
     * Rigorous Parser :-)
     */
    @Test
    public void testSampledoc() {
        executeJavadoc(".", new String[] {"./src/test/java"}, null, null, new String[] {"com"},
            new String[] {"-dryrun"});
    }

    /**
     * testing an interface with nothing defined
     */
    @Test
    public void testInterface1() {
        String[] sourceFiles = new String[] {getSourceFile(Interface1.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Interface interfaceNode = packageNode.getInterface().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(1, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Interface1", interfaceNode.getComment());
        assertEquals(Interface1.class.getSimpleName(), interfaceNode.getName());
        assertEquals(Interface1.class.getName(), interfaceNode.getQualified());
        assertEquals("public", interfaceNode.getScope());
        assertEquals(0, interfaceNode.getMethod().size());
        assertEquals(0, interfaceNode.getAnnotation().size());
        assertEquals(0, interfaceNode.getInterface().size());
        assertTrue(interfaceNode.isIncluded());
    }

    /**
     * testing an interface with 1 method
     */
    @Test
    public void testInterface2() {
        String[] sourceFiles = new String[] {getSourceFile(Interface2.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Interface interfaceNode = packageNode.getInterface().get(0);
        Method method = interfaceNode.getMethod().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(1, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Interface2", interfaceNode.getComment());
        assertEquals(Interface2.class.getSimpleName(), interfaceNode.getName());
        assertEquals(Interface2.class.getName(), interfaceNode.getQualified());
        assertEquals("public", interfaceNode.getScope());
        assertEquals(1, interfaceNode.getMethod().size());
        assertEquals(0, interfaceNode.getAnnotation().size());
        assertEquals(0, interfaceNode.getInterface().size());
        assertTrue(interfaceNode.isIncluded());

        // verify method
        assertEquals("method1", method.getComment());
        assertEquals("method1", method.getName());
        assertEquals("()", method.getSignature());
        assertFalse(method.isFinal());
        assertFalse(method.isNative());
        assertFalse(method.isStatic());
        assertFalse(method.isSynchronized());
        assertFalse(method.isVarArgs());
        assertEquals(getSimpledataPackage() + ".Interface2.method1", method.getQualified());
        assertEquals("public", method.getScope());
        assertEquals(0, method.getAnnotation().size());
        assertEquals(0, method.getParameter().size());
        assertEquals(0, method.getException().size());

    }

    /**
     * testing an interface that extends another interface
     */
    @Test
    public void testInterface3() {
        String[] sourceFiles = new String[] {getSourceFile(Interface3.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Interface interfaceNode = packageNode.getInterface().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(1, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Interface3", interfaceNode.getComment());
        assertEquals(Interface3.class.getSimpleName(), interfaceNode.getName());
        assertEquals(Interface3.class.getName(), interfaceNode.getQualified());
        assertEquals("public", interfaceNode.getScope());
        assertEquals(0, interfaceNode.getMethod().size());
        assertEquals(0, interfaceNode.getAnnotation().size());
        assertEquals(1, interfaceNode.getInterface().size());
        assertTrue(interfaceNode.isIncluded());

        // verify interface
        assertEquals(java.io.Serializable.class.getName(), interfaceNode.getInterface().get(0).getQualified());
    }

    /**
     * testing an interface that implements one annotation
     */
    @Test
    public void testInterface4() {
        String[] sourceFiles = new String[] {getSourceFile(Interface4.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Interface interfaceNode = packageNode.getInterface().get(0);
        AnnotationInstance annotationInstanceNode = interfaceNode.getAnnotation().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(1, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Interface4", interfaceNode.getComment());
        assertEquals(Interface4.class.getSimpleName(), interfaceNode.getName());
        assertEquals(Interface4.class.getName(), interfaceNode.getQualified());
        assertEquals("public", interfaceNode.getScope());
        assertEquals(0, interfaceNode.getMethod().size());
        assertEquals(1, interfaceNode.getAnnotation().size());
        assertEquals(0, interfaceNode.getInterface().size());
        assertTrue(interfaceNode.isIncluded());

        // verify deprecated annotation
        // test annotation 'deprecated' on class
        assertEquals("java.lang.Deprecated", annotationInstanceNode.getQualified());
        assertEquals("Deprecated", annotationInstanceNode.getName());
        assertEquals(0, annotationInstanceNode.getArgument().size());
    }

    /**
     * testing an interface that is abstract
     */
    @Test
    public void testInterface5() {
        String[] sourceFiles = new String[] {getSourceFile("Interface5")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Interface interfaceNode = packageNode.getInterface().get(0);
        Method method = interfaceNode.getMethod().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(1, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Interface5", interfaceNode.getComment());
        assertEquals("Interface5", interfaceNode.getName());
        assertEquals(getSimpledataPackage() + ".Interface5", interfaceNode.getQualified());
        assertEquals("", interfaceNode.getScope());
        assertEquals(1, interfaceNode.getMethod().size());
        assertEquals(0, interfaceNode.getAnnotation().size());
        assertEquals(0, interfaceNode.getInterface().size());
        assertTrue(interfaceNode.isIncluded());

        // verify method
        assertEquals("method1", method.getComment());
        assertEquals("method1", method.getName());
        assertEquals("()", method.getSignature());
        assertFalse(method.isFinal());
        assertFalse(method.isNative());
        assertFalse(method.isStatic());
        assertFalse(method.isSynchronized());
        assertFalse(method.isVarArgs());
        assertEquals(getSimpledataPackage() + ".Interface5.method1", method.getQualified());

        // all interface methods are public
        assertEquals("public", method.getScope());
        assertEquals(0, method.getAnnotation().size());
        assertEquals(0, method.getParameter().size());
        assertEquals(0, method.getException().size());
    }

    /**
     * testing an interface that has a type variable
     */
    @Test
    public void testInterface6() {
        String[] sourceFiles = new String[] {getSourceFile(Interface6.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Interface interfaceNode = packageNode.getInterface().get(0);
        TypeParameter typeParameterNode = interfaceNode.getGeneric().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(1, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Interface6", interfaceNode.getComment());
        assertEquals("Interface6", interfaceNode.getName());
        assertEquals(getSimpledataPackage() + ".Interface6", interfaceNode.getQualified());
        assertEquals("public", interfaceNode.getScope());
        assertEquals(0, interfaceNode.getMethod().size());
        assertEquals(0, interfaceNode.getAnnotation().size());
        assertEquals(0, interfaceNode.getInterface().size());
        assertTrue(interfaceNode.isIncluded());

        assertEquals("Fun", typeParameterNode.getName());
        assertEquals(0, typeParameterNode.getBound().size());
    }

    /**
     * testing an interface that has a type variable with extends
     */
    @Test
    public void testInterface7() {
        String[] sourceFiles = new String[] {getSourceFile(Interface7.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Interface interfaceNode = packageNode.getInterface().get(0);
        TypeParameter typeParameterNode = interfaceNode.getGeneric().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(1, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Interface7", interfaceNode.getComment());
        assertEquals("Interface7", interfaceNode.getName());
        assertEquals(getSimpledataPackage() + ".Interface7", interfaceNode.getQualified());
        assertEquals("public", interfaceNode.getScope());
        assertEquals(0, interfaceNode.getMethod().size());
        assertEquals(0, interfaceNode.getAnnotation().size());
        assertEquals(0, interfaceNode.getInterface().size());
        assertTrue(interfaceNode.isIncluded());

        assertEquals(1, typeParameterNode.getBound().size());
        assertEquals("java.lang.Number", typeParameterNode.getBound().get(0));
    }

    /**
     * testing an interface that has a type variable with extends of a class and
     * interface
     */
    @Test
    public void testInterface8() {
        String[] sourceFiles = new String[] {getSourceFile(Interface8.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Interface interfaceNode = packageNode.getInterface().get(0);
        TypeParameter typeParameterNode = interfaceNode.getGeneric().get(0);

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(1, packageNode.getInterface().size());
        assertEquals(0, packageNode.getClazz().size());

        assertEquals("Interface8", interfaceNode.getComment());
        assertEquals("Interface8", interfaceNode.getName());
        assertEquals(getSimpledataPackage() + ".Interface8", interfaceNode.getQualified());
        assertEquals("public", interfaceNode.getScope());
        assertEquals(0, interfaceNode.getMethod().size());
        assertEquals(0, interfaceNode.getAnnotation().size());
        assertEquals(0, interfaceNode.getInterface().size());
        assertTrue(interfaceNode.isIncluded());

        assertEquals(2, typeParameterNode.getBound().size());
        assertEquals("java.lang.Number", typeParameterNode.getBound().get(0));
        assertEquals("java.lang.Runnable", typeParameterNode.getBound().get(1));
    }
}
