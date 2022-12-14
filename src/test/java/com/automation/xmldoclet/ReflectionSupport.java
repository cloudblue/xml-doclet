package com.automation.xmldoclet;

import com.automation.xmldoclet.xjc.Class;

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReflectionSupport {

    /**
     * Runs a shallow comparison a class node structure with the reflection of its type.
     * Note: Does not check doc comments, annotations or deep objects.
     */
    public static <T> void assertStructureEquals(java.lang.Class<T> clazz, Class xmlClazz) {
        final int mod = clazz.getModifiers();
        assertEquals(getScope(mod), xmlClazz.getScope(), "Scope");
        assertEquals(Modifier.isAbstract(mod), xmlClazz.isAbstract(), "Abstract");
        assertEquals(clazz.getSimpleName(), xmlClazz.getName(), "SimpleName");
        assertEquals(clazz.getName(), xmlClazz.getQualified(), "Qualified");
        assertEquals(clazz.getDeclaredConstructors().length, xmlClazz.getConstructor().size(), "Constructors");
        assertEquals(clazz.getSuperclass().getName(), xmlClazz.getClazz().getQualified(), "Superclass");
        assertEquals(clazz.getInterfaces().length, xmlClazz.getInterface().size(), "Interfaces");
        assertEquals(clazz.getTypeParameters().length, xmlClazz.getGeneric().size(), "Generics");
        assertEquals(countClassMethodsFilteringSynthetic(clazz), xmlClazz.getMethod().size(), "Methods");
        assertEquals(clazz.getDeclaredFields().length, xmlClazz.getField().size(), "Fields");
        assertEquals(Externalizable.class.isAssignableFrom(clazz), xmlClazz.isExternalizable(), "Externalizable");
        assertEquals(Serializable.class.isAssignableFrom(clazz), xmlClazz.isSerializable(), "Serializable");
        assertEquals(Exception.class.isAssignableFrom(clazz), xmlClazz.isException(), "Exception");
        assertEquals(Error.class.isAssignableFrom(clazz), xmlClazz.isError(), "Error");
        assertTrue(xmlClazz.isIncluded());
    }

    private static String getScope(int mod) {
        if (Modifier.isPublic(mod)) return "public";
        if (Modifier.isProtected(mod)) return "protected";
        if (Modifier.isPrivate(mod)) return "private";
        return "";
    }

    /**
     * Required due to Jacoco synthetic classes (<a href="https://www.jacoco.org/jacoco/trunk/doc/faq.html">...</a>)
     *
     * @param clazz
     * @return
     */
    private static long countClassMethodsFilteringSynthetic(final java.lang.Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods()).filter(Predicate.not(Method::isSynthetic)).count();
    }
}
