package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.Annotation3;
import com.automation.xmldoclet.simpledata.AnnotationCascadeChild;
import com.automation.xmldoclet.simpledata.Class1;
import com.automation.xmldoclet.simpledata.Class13;
import com.automation.xmldoclet.simpledata.Class14;
import com.automation.xmldoclet.simpledata.Class15;
import com.automation.xmldoclet.simpledata.Class16;
import com.automation.xmldoclet.simpledata.Class17;
import com.automation.xmldoclet.simpledata.Class18;
import com.automation.xmldoclet.simpledata.Class19;
import com.automation.xmldoclet.simpledata.Class2;
import com.automation.xmldoclet.simpledata.Class20;
import com.automation.xmldoclet.simpledata.Class21;
import com.automation.xmldoclet.simpledata.Class22;
import com.automation.xmldoclet.simpledata.Class23;
import com.automation.xmldoclet.simpledata.Class24;
import com.automation.xmldoclet.simpledata.Class3;
import com.automation.xmldoclet.simpledata.Class4;
import com.automation.xmldoclet.simpledata.Class5;
import com.automation.xmldoclet.simpledata.Class6;
import com.automation.xmldoclet.simpledata.Class7;
import com.automation.xmldoclet.simpledata.Class8;
import com.automation.xmldoclet.simpledata.Class9;
import com.automation.xmldoclet.simpledata.ClassAnnotationCascade;
import com.automation.xmldoclet.simpledata.Enum1;
import com.automation.xmldoclet.xjc.AnnotationArgument;
import com.automation.xmldoclet.xjc.AnnotationInstance;
import com.automation.xmldoclet.xjc.Class;
import com.automation.xmldoclet.xjc.Constructor;
import com.automation.xmldoclet.xjc.Field;
import com.automation.xmldoclet.xjc.Method;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import com.automation.xmldoclet.xjc.TypeInfo;
import com.automation.xmldoclet.xjc.TypeParameter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test group for Classes
 */
@SuppressWarnings("deprecation")
public class ClassTest extends AbstractTestParent {

    /**
     * Testing nested Annotations
     *
     * @see ClassAnnotationCascade
     */
    @Test
    public void testClassAnnotationCascade() {
        String[] sourceFiles = new String[] {getSourceFile(ClassAnnotationCascade.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getClazz().size(), 1);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);

        assertEquals("ClassAnnotationCascade", classNode.getComment());
        assertEquals("ClassAnnotationCascade", classNode.getName());

        assertEquals(ClassAnnotationCascade.class.getName(), classNode.getQualified());

        assertEquals(classNode.getAnnotation().size(), 1);
        AnnotationInstance annotationNode = classNode.getAnnotation().get(0);

        assertEquals("AnnotationCascade", annotationNode.getName());
        assertEquals(1, annotationNode.getArgument().size());

        AnnotationArgument annotationArgNode = annotationNode.getArgument().get(0);

        // Two nested annotations in child attribute
        assertEquals("children", annotationArgNode.getName());
        assertEquals(0, annotationArgNode.getValue().size());
        assertEquals(2, annotationArgNode.getAnnotation().size());

        AnnotationInstance annonNodePrimitive = annotationArgNode.getAnnotation().get(0);
        AnnotationInstance annonNodeNested = annotationArgNode.getAnnotation().get(1);

        // Equal attribs
        assertEquals(AnnotationCascadeChild.class.getSimpleName(), annonNodePrimitive.getName());
        assertEquals(AnnotationCascadeChild.class.getSimpleName(), annonNodeNested.getName());
        assertEquals(AnnotationCascadeChild.class.getName(), annonNodePrimitive.getQualified());
        assertEquals(AnnotationCascadeChild.class.getName(), annonNodeNested.getQualified());
        assertEquals(2, annonNodePrimitive.getArgument().size());
        assertEquals(2, annonNodeNested.getArgument().size());
        assertEquals("name", annonNodePrimitive.getArgument().get(0).getName());
        assertEquals("name", annonNodeNested.getArgument().get(0).getName());

        // Primitive
        AnnotationArgument annArgNodePrimitive = annonNodePrimitive.getArgument().get(1);
        assertEquals("dummyData", annArgNodePrimitive.getName());
        assertEquals("java.lang.String", annArgNodePrimitive.getType().getQualified());
        assertEquals(0, annArgNodePrimitive.getAnnotation().size());
        assertEquals(3, annArgNodePrimitive.getValue().size());
        assertEquals("A", annArgNodePrimitive.getValue().get(0));
        assertEquals("B", annArgNodePrimitive.getValue().get(1));
        assertEquals("C", annArgNodePrimitive.getValue().get(2));

        // Nested
        AnnotationArgument annArgNodeNested = annonNodeNested.getArgument().get(1);
        assertEquals("subAnnotations", annArgNodeNested.getName());
        assertEquals(Annotation3.class.getName(), annArgNodeNested.getType().getQualified());
        assertEquals(3, annArgNodeNested.getAnnotation().size());
        assertEquals(0, annArgNodeNested.getValue().size());
        assertEquals(Annotation3.class.getSimpleName(), annArgNodeNested.getAnnotation().get(0).getName());
        assertEquals(Annotation3.class.getName(), annArgNodeNested.getAnnotation().get(1).getQualified());
        assertEquals(1, annArgNodeNested.getAnnotation().get(2).getArgument().size());

        assertEquals("666", annArgNodeNested.getAnnotation().get(2).getArgument().get(0).getValue().get(0));
    }


    /**
     * Rigorous Parser :-)
     */
    @Test
    public void testSampledoc() {
        executeJavadoc(".", new String[] {"./src/test/java"}, null, null, new String[] {"com"},
            new String[] {"-dryrun"});
    }

    /**
     * testing a class with nothing defined EMPIRICAL OBSERVATION: The default
     * constructor created by the java compiler is not marked synthetic. um
     * what?
     */
    @Test
    public void testClass1() {
        String[] sourceFiles = new String[] {getSourceFile(Class1.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class1");
        assertEquals(classNode.getName(), Class1.class.getSimpleName());
        assertEquals(classNode.getQualified(), Class1.class.getName());
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);
    }

    /**
     * testing a class with 1 constructor
     */
    @Test
    public void testClass2() {
        String[] sourceFiles = new String[] {getSourceFile(Class2.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        Constructor constructor = classNode.getConstructor().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class2");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), Class2.class.getSimpleName());
        assertEquals(classNode.getQualified(), Class2.class.getName());
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);

        assertEquals(constructor.getComment(), "Constructor1");
        assertEquals(constructor.getName(), "Class2");
        assertEquals(constructor.getParameter().size(), 0);
        assertEquals(constructor.getAnnotation().size(), 0);
    }

    /**
     * testing a class with 1 method
     */
    @Test
    public void testClass3() {
        String[] sourceFiles = new String[] {getSourceFile(Class3.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        Method method = classNode.getMethod().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class3");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), Class3.class.getSimpleName());
        assertEquals(classNode.getQualified(), Class3.class.getName());
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getMethod().size(), 1);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);

        assertEquals(method.getComment(), "method1");
        assertEquals(method.getName(), "method1");
        assertEquals(method.getSignature(), "()");
        assertFalse(method.isFinal());
        assertFalse(method.isNative());
        assertFalse(method.isStatic());
        assertFalse(method.isSynchronized());
        assertFalse(method.isVarArgs());
        assertEquals(method.getQualified(), getSimpledataPackage() + ".Class3.method1");
        assertEquals(method.getScope(), "public");
        assertEquals(method.getAnnotation().size(), 0);
        assertEquals(method.getParameter().size(), 0);
        assertEquals(method.getException().size(), 0);

        TypeInfo returnNode = method.getReturn();
        assertEquals(returnNode.getQualified(), "int");
        assertNull(returnNode.getDimension());
        assertEquals(returnNode.getGeneric().size(), 0);
        assertNull(returnNode.getWildcard());
    }

    /**
     * testing a class with 1 field
     */
    @Test
    public void testClass4() {
        String[] sourceFiles = new String[] {getSourceFile(Class4.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        Field field = classNode.getField().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class4");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), Class4.class.getSimpleName());
        assertEquals(classNode.getQualified(), Class4.class.getName());
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getField().size(), 1);
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);

        // test field
        assertEquals(field.getComment(), "field1");
        assertEquals(field.getName(), "field1");
        assertEquals(field.getScope(), "public");
        assertEquals(field.getType().getQualified(), "int");
        assertNull(field.getType().getDimension());
        assertEquals(field.getType().getGeneric().size(), 0);
        assertNull(field.getType().getWildcard());
        assertFalse(field.isStatic());
        assertFalse(field.isTransient());
        assertFalse(field.isVolatile());
        assertFalse(field.isFinal());
        assertNull(field.getConstant());
        assertEquals(field.getAnnotation().size(), 0);
    }

    /**
     * testing a class that extends another class with 1 method
     */
    @Test
    public void testClass5() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class5.class),
            getSourceFile(Class3.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 2);

        assertEquals(classNode.getComment(), "Class5");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), Class5.class.getSimpleName());
        assertEquals(classNode.getQualified(), Class5.class.getName());
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), getSimpledataPackage() + ".Class3");
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);
    }

    /**
     * testing a class that implements one interface
     */
    @Test
    public void testClass6() {
        String[] sourceFiles = new String[] {getSourceFile(Class6.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        TypeInfo interfaceNode = classNode.getInterface().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class6");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), Class6.class.getSimpleName());
        assertEquals(classNode.getQualified(), Class6.class.getName());
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 1);
        assertEquals(classNode.getInterface().size(), 1);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);

        // the particular interface chosen for this test also will change this
        // flag to true!
        assertTrue(classNode.isSerializable());

        // verify interface
        assertEquals(interfaceNode.getQualified(), java.io.Serializable.class.getName());
    }

    /**
     * testing one annotation instance on the class
     */
    @Test
    public void testClass7() {
        String[] sourceFiles = new String[] {getSourceFile(Class7.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance annotationNode = classNode.getAnnotation().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class7");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), Class7.class.getSimpleName());
        assertEquals(classNode.getQualified(), Class7.class.getName());
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 1);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);

        // test annotation 'deprecated' on class
        assertEquals(annotationNode.getQualified(), "java.lang.Deprecated");
        assertEquals(annotationNode.getName(), "Deprecated");
        assertEquals(annotationNode.getArgument().size(), 0);
    }

    /**
     * testing abstract keyword on class
     */
    @Test
    public void testClass8() {
        String[] sourceFiles = new String[] {getSourceFile(Class8.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class8");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), Class8.class.getSimpleName());
        assertEquals(classNode.getQualified(), Class8.class.getName());
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertTrue(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);
    }

    /**
     * testing java.io.Externalizable interface on class
     */
    @Test
    public void testClass9() {
        String[] sourceFiles = new String[] {getSourceFile(Class9.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class9");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), Class9.class.getSimpleName());
        assertEquals(classNode.getQualified(), Class9.class.getName());
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getMethod().size(), 2);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 1);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertTrue(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertTrue(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);
    }

    /**
     * testing difference of scope modifier on class
     */
    @Test
    public void testClass10() {
        String[] sourceFiles = new String[] {getSourceFile("Class10")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class10");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), "Class10");
        assertEquals(classNode.getQualified(), getSimpledataPackage() + ".Class10");
        assertEquals(classNode.getScope(), "");
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);
    }

    /**
     * testing if isException is populated correctly
     */
    @Test
    public void testClass11() {
        String[] sourceFiles = new String[] {getSourceFile("Class11")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class11");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), "Class11");
        assertEquals(classNode.getQualified(), getSimpledataPackage() + ".Class11");
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 1);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), java.lang.Exception.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertTrue(classNode.isSerializable());
        assertTrue(classNode.isException());
        assertFalse(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);
    }

    /**
     * testing if isError is populated correctly
     */
    @Test
    public void testClass12() {
        String[] sourceFiles = new String[] {getSourceFile("Class12")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class12");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), "Class12");
        assertEquals(classNode.getQualified(), getSimpledataPackage() + ".Class12");
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 1);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), java.lang.Error.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertTrue(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertTrue(classNode.isError());
        assertEquals(classNode.getGeneric().size(), 0);
    }

    /**
     * testing if type variables can be determined
     */
    @Test
    public void testClass13() {
        String[] sourceFiles = new String[] {getSourceFile(Class13.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        TypeParameter typeParameter = classNode.getGeneric().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class13");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), "Class13");
        assertEquals(classNode.getQualified(), getSimpledataPackage() + ".Class13");
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getGeneric().size(), 1);
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());

        // check the 'fun' type var
        assertEquals(typeParameter.getName(), "Fun");
        assertEquals(typeParameter.getBound().size(), 0);
    }

    /**
     * testing if a single bounds can be determined
     */
    @Test
    public void testClass14() {
        String[] sourceFiles = new String[] {getSourceFile(Class14.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        TypeParameter typeParameter = classNode.getGeneric().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class14");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), "Class14");
        assertEquals(classNode.getQualified(), getSimpledataPackage() + ".Class14");
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getGeneric().size(), 1);
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());

        // check the 'fun' type var

        assertEquals(typeParameter.getName(), "Fun");
        assertEquals(typeParameter.getBound().size(), 1);
        assertEquals(typeParameter.getBound().get(0), Number.class.getName());
    }

    /**
     * testing if a multiple bounds can be determined
     */
    @Test
    public void testClass15() {
        String[] sourceFiles = new String[] {getSourceFile(Class15.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        TypeParameter typeParameter = classNode.getGeneric().get(0);

        assertEquals(rootNode.getPackage().size(), 1);
        assertNull(packageNode.getComment());
        assertEquals(packageNode.getName(), getSimpledataPackage());
        assertEquals(packageNode.getAnnotation().size(), 0);
        assertEquals(packageNode.getEnum().size(), 0);
        assertEquals(packageNode.getInterface().size(), 0);
        assertEquals(packageNode.getClazz().size(), 1);

        assertEquals(classNode.getComment(), "Class15");
        assertEquals(classNode.getConstructor().size(), 1);
        assertEquals(classNode.getName(), "Class15");
        assertEquals(classNode.getQualified(), getSimpledataPackage() + ".Class15");
        assertEquals(classNode.getScope(), "public");
        assertEquals(classNode.getGeneric().size(), 1);
        assertEquals(classNode.getMethod().size(), 0);
        assertEquals(classNode.getField().size(), 0);
        assertEquals(classNode.getAnnotation().size(), 0);
        assertEquals(classNode.getInterface().size(), 0);
        assertEquals(classNode.getClazz().getQualified(), Object.class.getName());
        assertFalse(classNode.isAbstract());
        assertFalse(classNode.isExternalizable());
        assertTrue(classNode.isIncluded());
        assertFalse(classNode.isSerializable());
        assertFalse(classNode.isException());
        assertFalse(classNode.isError());

        // check the 'fun' type var
        assertEquals(typeParameter.getName(), "Fun");
        assertEquals(typeParameter.getBound().size(), 2);
        assertEquals(typeParameter.getBound().get(0), Number.class.getName());
        assertEquals(typeParameter.getBound().get(1), Runnable.class.getName());
    }

    /**
     * testing integer annotation argument
     */
    @Test
    public void testClass16() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class16.class),
            getSourceFile(Annotation3.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance instance = classNode.getAnnotation().get(0);
        AnnotationArgument argument = instance.getArgument().get(0);
        assertEquals(argument.getName(), "id");
        assertEquals(argument.getType().getQualified(), "int");
        assertEquals(argument.getValue().size(), 1);
        assertEquals(argument.getValue().get(0), "3");
        assertTrue(argument.isPrimitive());
        assertFalse(argument.isArray());
    }

    /**
     * testing integer array annotation argument
     */
    @Test
    public void testClass17() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class17.class),
            getSourceFile("Annotation5")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance instance = classNode.getAnnotation().get(0);
        AnnotationArgument argument = instance.getArgument().get(0);
        assertEquals(argument.getType().getQualified(), "int");
        assertEquals(argument.getValue().size(), 2);
        assertEquals(argument.getValue().get(0), "1");
        assertEquals(argument.getValue().get(1), "2");
        assertTrue(argument.isPrimitive());
        assertTrue(argument.isArray());
    }

    /**
     * testing integer array annotation argument
     */
    @Test
    public void testClass18() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class18.class),
            getSourceFile("Annotation6")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance instance = classNode.getAnnotation().get(0);
        AnnotationArgument argument = instance.getArgument().get(0);
        assertEquals(argument.getType().getQualified(), "java.lang.String");
        assertEquals(argument.getValue().size(), 1);
        assertEquals(argument.getValue().get(0), "hey");
        assertFalse(argument.isPrimitive());
        assertFalse(argument.isArray());
    }

    /**
     * testing enum annotation argument
     */
    @Test
    public void testClass19() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class19.class),
            getSourceFile("Annotation7"),
            getSourceFile(Enum1.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance instance = classNode.getAnnotation().get(0);
        AnnotationArgument argument = instance.getArgument().get(0);
        assertEquals(argument.getType().getQualified(), getSimpledataPackage() + ".Enum1");
        assertEquals(argument.getValue().size(), 1);
        assertEquals(argument.getValue().get(0), "a");
        assertFalse(argument.isPrimitive());
        assertFalse(argument.isArray());
    }

    /**
     * testing class annotation argument
     */
    @Test
    public void testClass20() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class20.class),
            getSourceFile("Annotation8")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance instance = classNode.getAnnotation().get(0);
        AnnotationArgument argument = instance.getArgument().get(0);
        assertEquals(argument.getType().getQualified(), "java.lang.Class");
        assertEquals(argument.getValue().size(), 1);
        assertEquals(argument.getValue().get(0), "java.lang.String");
        assertFalse(argument.isPrimitive());
        assertFalse(argument.isArray());
    }

    /**
     * testing character annotation argument
     */
    @Test
    public void testClass21() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class21.class),
            getSourceFile("Annotation10")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance instance = classNode.getAnnotation().get(0);
        AnnotationArgument argument = instance.getArgument().get(0);
        assertEquals(argument.getType().getQualified(), "char");
        assertEquals(argument.getValue().size(), 1);
        assertEquals(argument.getValue().get(0), Integer.toString('a'));
        assertTrue(argument.isPrimitive());
        assertFalse(argument.isArray());
    }

    /**
     * testing 0 character annotation argument
     */
    @Test
    public void testClass22() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class22.class),
            getSourceFile("Annotation10")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance instance = classNode.getAnnotation().get(0);
        AnnotationArgument argument = instance.getArgument().get(0);
        assertEquals(argument.getType().getQualified(), "char");
        assertEquals(argument.getValue().size(), 1);
        assertEquals(argument.getValue().get(0), Integer.toString(0));
        assertTrue(argument.isPrimitive());
        assertFalse(argument.isArray());
    }

    /**
     * testing boolean annotation argument
     */
    @Test
    public void testClass23() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class23.class),
            getSourceFile("Annotation11")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance instance = classNode.getAnnotation().get(0);
        AnnotationArgument argument = instance.getArgument().get(0);
        assertEquals(argument.getType().getQualified(), "boolean");
        assertEquals(argument.getValue().size(), 1);
        assertEquals(argument.getValue().get(0), Boolean.TRUE.toString());
        assertTrue(argument.isPrimitive());
        assertFalse(argument.isArray());
    }

    /**
     * testing empty int array annotation argument
     */
    @Test
    public void testClass24() {
        String[] sourceFiles = new String[] {
            getSourceFile(Class24.class),
            getSourceFile("Annotation5")};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        AnnotationInstance instance = classNode.getAnnotation().get(0);
        AnnotationArgument argument = instance.getArgument().get(0);
        assertEquals(argument.getType().getQualified(), "int");
        assertEquals(argument.getValue().size(), 0);
        assertTrue(argument.isPrimitive());
        assertTrue(argument.isArray());
    }
}
