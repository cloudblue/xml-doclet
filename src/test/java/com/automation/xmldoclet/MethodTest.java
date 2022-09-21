package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.Annotation12;
import com.automation.xmldoclet.simpledata.Method1;
import com.automation.xmldoclet.simpledata.Method2;
import com.automation.xmldoclet.simpledata.Method3;
import com.automation.xmldoclet.xjc.AnnotationArgument;
import com.automation.xmldoclet.xjc.AnnotationInstance;
import com.automation.xmldoclet.xjc.Class;
import com.automation.xmldoclet.xjc.Method;
import com.automation.xmldoclet.xjc.MethodParameter;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import com.automation.xmldoclet.xjc.TypeInfo;
import com.automation.xmldoclet.xjc.Wildcard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test group for Methods
 */
public class MethodTest extends AbstractTestParent {

    /**
     * Rigorous Parser :-)
     */
    @Test
    public void testSampledoc() {
        executeJavadoc(".", new String[] {"./src/test/java"}, null, null, new String[] {"com"},
            new String[] {"-dryrun"});
    }

    /**
     * testing a returns of methodNodes
     */
    @Test
    public void testMethod1() {
        String[] sourceFiles = new String[] {getSourceFile(Method1.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        List<Method> testMethods = classNode.getMethod();

        // with methodNode1 we are checking that a simple methodNode can exist
        // with no arguments and no return
        Method methodNode = findByMethodName("method1", testMethods);
        assertEquals("void", methodNode.getReturn().getQualified());
        assertEquals(0, methodNode.getReturn().getGeneric().size());
        assertNull(methodNode.getReturn().getWildcard());
        assertNull(methodNode.getReturn().getDimension());

        // methodNode2 - checking Object based returns
        methodNode = findByMethodName("method2", testMethods);
        assertEquals("java.lang.Integer", methodNode.getReturn().getQualified());
        assertEquals(0, methodNode.getReturn().getGeneric().size());
        assertNull(methodNode.getReturn().getWildcard());
        assertNull(methodNode.getReturn().getDimension());

        // methodNode 3 - checking primitive based returns
        methodNode = findByMethodName("method3", testMethods);
        assertEquals("int", methodNode.getReturn().getQualified());
        assertEquals(0, methodNode.getReturn().getGeneric().size());
        assertNull(methodNode.getReturn().getWildcard());
        assertNull(methodNode.getReturn().getDimension());
    }

    /**
     * testing arguments of methodNodes
     */
    @Test
    public void testMethod2() {
        String[] sourceFiles = new String[] {getSourceFile(Method2.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        List<Method> testMethods = classNode.getMethod();

        // methodNode - methodNode with no arguments
        Method methodNode = findByMethodName("method1", testMethods);
        assertEquals(0, methodNode.getParameter().size());
        assertEquals("()", methodNode.getSignature());

        // methodNode2 - methodNode with one Object-derived argument
        methodNode = findByMethodName("method2", testMethods);
        assertEquals(1, methodNode.getParameter().size());
        assertEquals("(" + Integer.class.getName() + ")", methodNode.getSignature());

        // one should be able to reliably access getParameter() in this fashion
        // since XML order is important, and order of getParameter() to
        // methodNodes is
        // likewise important. ORDER MATTERS AND SHOULD BE TRUSTY!
        MethodParameter methodParameterNode = methodNode.getParameter().get(0);
        assertEquals("java.lang.Integer", methodParameterNode.getType().getQualified());

        // methodNode3 - check primitive argument
        methodNode = findByMethodName("method3", testMethods);
        assertEquals(1, methodNode.getParameter().size());
        assertEquals("(int)", methodNode.getSignature());

        methodParameterNode = methodNode.getParameter().get(0);
        assertEquals("int", methodParameterNode.getType().getQualified());
        assertNull(methodParameterNode.getType().getDimension());
        assertEquals(0, methodParameterNode.getType().getGeneric().size());
        assertNull(methodParameterNode.getType().getWildcard());

        // methodNode4 - check that two args are OK
        methodNode = findByMethodName("method4", testMethods);
        assertEquals(2, methodNode.getParameter().size());
        assertEquals("(" + Integer.class.getName() + ", " + Integer.class.getName() + ")", methodNode.getSignature());

        methodParameterNode = methodNode.getParameter().get(0);
        assertEquals("java.lang.Integer", methodParameterNode.getType().getQualified());

        methodParameterNode = methodNode.getParameter().get(1);
        assertEquals("java.lang.Integer", methodParameterNode.getType().getQualified());

        // methodNode5 - check that a generic argument is valid
        methodNode = findByMethodName("method5", testMethods);
        assertEquals(1, methodNode.getParameter().size());
        assertEquals("(java.util.ArrayList<java.lang.String>)", methodNode.getSignature());

        methodParameterNode = methodNode.getParameter().get(0);
        assertEquals("arg1", methodParameterNode.getName());
        assertEquals("java.util.ArrayList", methodParameterNode.getType().getQualified());
        assertNull(methodParameterNode.getType().getDimension());
        assertNull(methodParameterNode.getType().getWildcard());
        assertEquals(1, methodParameterNode.getType().getGeneric().size());

        TypeInfo type = methodParameterNode.getType().getGeneric().get(0);
        assertEquals("java.lang.String", type.getQualified());
        assertNull(type.getDimension());
        assertNull(type.getWildcard());
        assertEquals(0, type.getGeneric().size());

        // methodNode6 - check that a wildcard argument is valid
        methodNode = findByMethodName("method6", testMethods);
        assertEquals(1, methodNode.getParameter().size());
        assertEquals("(java.util.ArrayList<?>)", methodNode.getSignature());

        methodParameterNode = methodNode.getParameter().get(0);
        assertEquals("arg1", methodParameterNode.getName());
        assertEquals("java.util.ArrayList", methodParameterNode.getType().getQualified());
        assertNull(methodParameterNode.getType().getDimension());
        assertNull(methodParameterNode.getType().getWildcard());
        assertEquals(1, methodParameterNode.getType().getGeneric().size());

        type = methodParameterNode.getType().getGeneric().get(0);
        assertEquals("?", type.getQualified());
        assertNull(type.getDimension());
        assertNotNull(type.getWildcard());
        assertEquals(0, type.getGeneric().size());

        Wildcard wildcard = type.getWildcard();
        assertEquals(0, wildcard.getExtendsBound().size());
        assertEquals(0, wildcard.getSuperBound().size());

        // methodNode7 - check that a wildcard argument is valid with extends
        // clause
        methodNode = findByMethodName("method7", testMethods);
        assertEquals(1, methodNode.getParameter().size());
        assertEquals("(java.util.ArrayList<? extends java.lang.String>)", methodNode.getSignature());

        methodParameterNode = methodNode.getParameter().get(0);
        assertEquals("arg1", methodParameterNode.getName());
        assertEquals("java.util.ArrayList", methodParameterNode.getType().getQualified());
        assertNull(methodParameterNode.getType().getDimension());
        assertEquals(1, methodParameterNode.getType().getGeneric().size());
        assertNull(methodParameterNode.getType().getWildcard());

        type = methodParameterNode.getType().getGeneric().get(0);
        assertEquals("?", type.getQualified());
        assertNull(type.getDimension());
        assertEquals(0, type.getGeneric().size());
        assertNotNull(type.getWildcard());

        wildcard = type.getWildcard();
        assertEquals(1, wildcard.getExtendsBound().size());
        assertEquals(0, wildcard.getSuperBound().size());

        TypeInfo extendsBound = wildcard.getExtendsBound().get(0);
        assertEquals("java.lang.String", extendsBound.getQualified());
        assertNull(extendsBound.getDimension());
        assertEquals(0, extendsBound.getGeneric().size());
        assertNull(extendsBound.getWildcard());

        // methodNode8 - check that a wildcard argument is valid with super
        // clause
        methodNode = findByMethodName("method8", testMethods);
        assertEquals(1, methodNode.getParameter().size());
        assertEquals("(java.util.ArrayList<? super java.lang.String>)", methodNode.getSignature());

        methodParameterNode = methodNode.getParameter().get(0);
        assertEquals("arg1", methodParameterNode.getName());
        assertEquals("java.util.ArrayList", methodParameterNode.getType().getQualified());
        assertNull(methodParameterNode.getType().getDimension());
        assertEquals(1, methodParameterNode.getType().getGeneric().size());
        assertNull(methodParameterNode.getType().getWildcard());

        type = methodParameterNode.getType().getGeneric().get(0);
        assertEquals("?", type.getQualified());
        assertNull(type.getDimension());
        assertEquals(0, type.getGeneric().size());
        assertNotNull(type.getWildcard());

        wildcard = type.getWildcard();
        assertEquals(1, wildcard.getSuperBound().size());
        assertEquals(0, wildcard.getExtendsBound().size());

        TypeInfo superBounds = wildcard.getSuperBound().get(0);
        assertEquals("java.lang.String", superBounds.getQualified());
        assertNull(superBounds.getDimension());
        assertEquals(0, superBounds.getGeneric().size());
        assertNull(superBounds.getWildcard());

        // methodNode9 - check that a two-level deep nested generic
        methodNode = findByMethodName("method9", testMethods);
        assertEquals(1, methodNode.getParameter().size());
        assertEquals("(java.util.ArrayList<java.util.ArrayList<java.lang.String>>)", methodNode.getSignature());

        methodParameterNode = methodNode.getParameter().get(0);
        assertEquals("arg1", methodParameterNode.getName());
        assertEquals("java.util.ArrayList", methodParameterNode.getType().getQualified());
        assertNull(methodParameterNode.getType().getDimension());
        assertEquals(1, methodParameterNode.getType().getGeneric().size());
        assertNull(methodParameterNode.getType().getWildcard());

        type = methodParameterNode.getType().getGeneric().get(0);
        assertEquals("java.util.ArrayList", type.getQualified());
        assertNull(type.getDimension());
        assertEquals(1, type.getGeneric().size());
        assertNull(type.getWildcard());

        type = type.getGeneric().get(0);
        assertEquals("java.lang.String", type.getQualified());
        assertNull(type.getDimension());
        assertEquals(0, type.getGeneric().size());
        assertNull(type.getWildcard());

        // methodNode10 - check var args
        methodNode = findByMethodName("method10", testMethods);
        assertEquals(1, methodNode.getParameter().size());
        assertEquals("(java.lang.Object...)", methodNode.getSignature());
        assertTrue(methodNode.isVarArgs());

        methodParameterNode = methodNode.getParameter().get(0);
        assertEquals("object", methodParameterNode.getName());
        assertEquals("java.lang.Object", methodParameterNode.getType().getQualified());
        assertEquals("[]", methodParameterNode.getType().getDimension());

        // methodNode9--check var args negative test
        assertFalse(findByMethodName("method9", testMethods).isVarArgs());
    }

    /**
     * testing methodNode properties
     */
    @Test
    public void testMethod3() {
        String[] sourceFiles = new String[] {getSourceFile(Method3.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        List<Method> testMethods = classNode.getMethod();

        // methodNode1 -- we check public scope
        Method methodNode = findByMethodName("method1", testMethods);
        assertEquals("public", methodNode.getScope());

        // methodNode2 -- we check package scope
        methodNode = findByMethodName("method2", testMethods);
        assertEquals("", methodNode.getScope());

        // methodNode3 -- we check private scope
        methodNode = findByMethodName("method3", testMethods);
        assertEquals("private", methodNode.getScope());

        // methodNode4 -- we check private scope
        methodNode = findByMethodName("method4", testMethods);
        assertEquals("protected", methodNode.getScope());

        // methodNode5 -- we check native
        methodNode = findByMethodName("method5", testMethods);
        assertTrue(methodNode.isNative());
        // and negative
        assertFalse(findByMethodName("method4", testMethods).isNative());

        // methodNode6 -- we check static
        methodNode = findByMethodName("method6", testMethods);
        assertTrue(methodNode.isStatic());
        // and negative
        assertFalse(findByMethodName("method4", testMethods).isStatic());

        // methodNode7 -- we check final
        methodNode = findByMethodName("method7", testMethods);
        assertTrue(methodNode.isFinal());
        // and negative
        assertFalse(findByMethodName("method4", testMethods).isFinal());

        // methodNode8 -- we check synchronized
        methodNode = findByMethodName("method8", testMethods);
        assertTrue(methodNode.isSynchronized());
        // and negative
        assertFalse(findByMethodName("method4", testMethods).isSynchronized());

        // methodNode9 -- we check one thrown exception
        methodNode = findByMethodName("method9", testMethods);
        assertEquals(1, methodNode.getException().size());

        TypeInfo exception = methodNode.getException().get(0);
        assertEquals("java.lang.Exception", exception.getQualified());
        assertNull(exception.getDimension());
        assertEquals(0, exception.getGeneric().size());
        assertNull(exception.getWildcard());

        // methodNode10 -- we check two thrown exceptions
        methodNode = findByMethodName("method10", testMethods);
        assertEquals(2, methodNode.getException().size());

        exception = methodNode.getException().get(0);
        assertEquals("java.lang.OutOfMemoryError", exception.getQualified());
        assertNull(exception.getDimension());
        assertEquals(0, exception.getGeneric().size());

        exception = methodNode.getException().get(1);
        assertEquals("java.lang.IllegalArgumentException", exception.getQualified());
        assertNull(exception.getDimension());
        assertEquals(0, exception.getGeneric().size());

        // negative--no exceptions
        assertEquals(0, findByMethodName("method4", testMethods).getException().size());

        // methodNode11 -- 1 annotation instance

        methodNode = findByMethodName("method11", testMethods);
        assertEquals(1, methodNode.getAnnotation().size());

        AnnotationInstance annotation = methodNode.getAnnotation().get(0);
        assertEquals("java.lang.Deprecated", annotation.getQualified());
        assertEquals(0, annotation.getArgument().size());

        // methodNode12 -- 2 annotation instances
        methodNode = findByMethodName("method12", testMethods);
        assertEquals(2, methodNode.getAnnotation().size());

        annotation = methodNode.getAnnotation().get(0);
        assertEquals("java.lang.Deprecated", annotation.getQualified());

        annotation = methodNode.getAnnotation().get(1);
        assertEquals(Annotation12.class.getName(), annotation.getQualified());
        assertEquals(1, annotation.getArgument().size());
        AnnotationArgument annotArgument = annotation.getArgument().get(0);
        assertEquals("value", annotArgument.getName());
        assertEquals("java.lang.Warning", annotArgument.getValue().get(0));

        // negative -- no annotations
        assertEquals(0, findByMethodName("method4", testMethods).getAnnotation().size());

    }

    /**
     * Short way of finding methodNodes. It's meant to only be used for
     * methodNodes that do not share the same name in the same class. In fact,
     * this class will junit assert that there is only 1 methodNode matching
     * this name in the supplied <code>list</code> methodNodes.
     *
     * @param methodNodeName
     *            the shortname of the methodNode
     * @param methodNodes
     *            the list of methodNodes to look through.
     * @return The matching methodNode
     */
    private Method findByMethodName(String methodNodeName, List<Method> methodNodes) {
        for (Method methodNode : methodNodes) {
            if (methodNode.getName().equals(methodNodeName)) {
                return methodNode;
            }
        }

        fail();
        return new Method();
    }
}
