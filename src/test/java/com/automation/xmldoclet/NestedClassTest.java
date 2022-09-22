package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.ParentClass;
import com.automation.xmldoclet.xjc.Class;
import com.automation.xmldoclet.xjc.Root;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Nested classes caused a duplication of the generated output.
 */
public class NestedClassTest extends AbstractTestParent {

    @Test
    void testDuplicate() {
        String[] sourceFiles = new String[] {getSourceFile(ParentClass.class)};
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});

        List<Class> classes = rootNode.getPackage().get(0).getClazz();
        long classesCount = classes.stream()
            .filter(aClass -> aClass.getName().contains(ParentClass.NestedClass.class.getSimpleName()))
            .count();
        assertEquals(1, classesCount);
    }
}
