package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.Annotation12;
import com.automation.xmldoclet.simpledata.Field1;
import com.automation.xmldoclet.xjc.AnnotationArgument;
import com.automation.xmldoclet.xjc.AnnotationInstance;
import com.automation.xmldoclet.xjc.Class;
import com.automation.xmldoclet.xjc.Field;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test group for Fields
 */
public class FieldTest extends AbstractTestParent {

    /**
     * Rigorous Parser :-)
     */
    @Test
    public void testSampledoc() {
        executeJavadoc(".", new String[] {"./src/test/java"}, null, null, new String[] {"com"},
            new String[] {"-dryrun"});
    }

    /**
     * testing a returns of fields
     */
    @Test
    public void testMethod1() {
        String[] sourceFiles = new String[] {getSourceFile(Field1.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        Package packageNode = rootNode.getPackage().get(0);
        Class classNode = packageNode.getClazz().get(0);
        List<Field> fields = classNode.getField();

        assertEquals(1, rootNode.getPackage().size());
        assertNull(packageNode.getComment());
        assertEquals(getSimpledataPackage(), packageNode.getName());
        assertEquals(0, packageNode.getAnnotation().size());
        assertEquals(0, packageNode.getEnum().size());
        assertEquals(0, packageNode.getInterface().size());
        assertEquals(1, packageNode.getClazz().size());

        // field0 -- test name
        Field field = findByFieldName("field0", fields);
        assertEquals("field0", field.getName());

        // field1 -- test public field
        field = findByFieldName("field1", fields);
        assertEquals("public", field.getScope());

        // field2 -- test private field
        field = findByFieldName("field2", fields);
        assertEquals("private", field.getScope());

        // field3 -- default scope field (non defined)
        field = findByFieldName("field3", fields);
        assertEquals("", field.getScope());

        // field4 -- protected scope field
        field = findByFieldName("field4", fields);
        assertEquals("protected", field.getScope());

        // field5 -- volatile field
        field = findByFieldName("field5", fields);
        assertTrue(field.isVolatile());

        // negative test of volatile
        assertFalse(findByFieldName("field4", fields).isVolatile());

        // field6 -- static field
        field = findByFieldName("field6", fields);
        assertTrue(field.isStatic());

        // negative test of static
        assertFalse(findByFieldName("field4", fields).isStatic());

        // field7 -- transient field
        field = findByFieldName("field7", fields);
        assertTrue(field.isTransient());

        // negative test of transient
        assertFalse(findByFieldName("field4", fields).isTransient());

        // field8 -- final field
        field = findByFieldName("field8", fields);
        assertTrue(field.isFinal());

        // negative test of final
        assertFalse(findByFieldName("field4", fields).isFinal());

        // field9 -- string final expression
        field = findByFieldName("field9", fields);
        assertEquals("\"testy\"", field.getConstant());

        // field10 -- int final expression
        field = findByFieldName("field10", fields);
        assertEquals("10", field.getConstant());

        // field11 -- annotation
        field = findByFieldName("field11", fields);
        assertEquals(1, field.getAnnotation().size());

        AnnotationInstance annotation = field.getAnnotation().get(0);
        assertEquals("java.lang.Deprecated", annotation.getQualified());
        assertEquals("Deprecated", annotation.getName());
        assertEquals(0, annotation.getArgument().size());

        // field12 -- two annotations
        field = findByFieldName("field12", fields);
        assertEquals(2, field.getAnnotation().size());

        annotation = field.getAnnotation().get(0);
        assertEquals("java.lang.Deprecated", annotation.getQualified());
        assertEquals("Deprecated", annotation.getName());
        assertEquals(0, annotation.getArgument().size());

        annotation = field.getAnnotation().get(1);
        assertEquals(Annotation12.class.getName(), annotation.getQualified());
        assertEquals(Annotation12.class.getSimpleName(), annotation.getName());
        assertEquals(1, annotation.getArgument().size());

        AnnotationArgument argument = annotation.getArgument().get(0);
        assertEquals("value", argument.getName());
        assertEquals("mister", argument.getValue().get(0));

        // field13 - type testing
        field = findByFieldName("field13", fields);
        assertNotNull(field.getType());
        assertEquals("java.lang.String", field.getType().getQualified());
        assertNull(field.getType().getDimension());
        assertNull(field.getType().getWildcard());
        assertEquals(0, field.getType().getGeneric().size());

        // field14 - wild card
        field = findByFieldName("field14", fields);
        assertNotNull(field.getType());
        assertEquals("java.util.ArrayList", field.getType().getQualified());
        assertNotNull(field.getType().getGeneric());
        assertEquals(1, field.getType().getGeneric().size());
        assertEquals("?", field.getType().getGeneric().get(0).getQualified());
        assertNotNull(field.getType().getGeneric().get(0).getWildcard());

        // field15 - typed generic
        field = findByFieldName("field15", fields);
        assertNotNull(field.getType());
        assertEquals("java.util.HashMap", field.getType().getQualified());
        assertEquals(2, field.getType().getGeneric().size());
        assertEquals("java.lang.String", field.getType().getGeneric().get(0).getQualified());
        assertNull(field.getType().getGeneric().get(0).getWildcard());
        assertEquals("java.lang.Integer", field.getType().getGeneric().get(1).getQualified());
        assertNull(field.getType().getGeneric().get(1).getWildcard());

        // field16 - array
        field = findByFieldName("field16", fields);
        assertNotNull(field.getType());
        assertEquals("java.lang.String", field.getType().getQualified());
        assertEquals("[]", field.getType().getDimension());
    }

    /**
     * Short way of finding fields.
     *
     * @param fieldName the shortname of the method
     * @param fields the list of methods to look through.
     * @return The matching field
     */
    private Field findByFieldName(String fieldName, List<Field> fields) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        fail();
        return new Field();
    }
}
