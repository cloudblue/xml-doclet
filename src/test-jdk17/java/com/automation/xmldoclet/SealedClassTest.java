package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.NonSealedSubClass;
import com.automation.xmldoclet.simpledata.SealedClass;
import com.automation.xmldoclet.simpledata.SealedInterface;
import com.automation.xmldoclet.simpledata.SealedSubClass;
import com.automation.xmldoclet.xjc.Class;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import org.junit.jupiter.api.Test;

import static com.automation.xmldoclet.ReflectionSupport.assertStructureEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SealedClassTest extends AbstractTestParent {

    @Test
    public void testSealed() {
        String[] sourceFiles = new String[] {
            SimpledataSupport.getSourceFile(SealedInterface.class),
            SimpledataSupport.getSourceFile(SealedClass.class),
            SimpledataSupport.getSourceFile(SealedSubClass.class),
            SimpledataSupport.getSourceFile(NonSealedSubClass.class)
        };
        Root rootNode = executeJavadoc(null, null, null, sourceFiles, null, new String[] {"-dryrun"});
        Package packageNode = rootNode.getPackage().get(0);
        assertEquals(1, rootNode.getPackage().size());
        assertEquals(1, packageNode.getInterface().size());
        Class sealedClass = packageNode.getClazz().get(0);
        Class sealedSubClass = packageNode.getClazz().get(1);
        Class nonSealedSubClass = packageNode.getClazz().get(2);
        assertStructureEquals(SealedClass.class, sealedClass);
        assertStructureEquals(SealedSubClass.class, sealedSubClass);
        assertStructureEquals(NonSealedSubClass.class, nonSealedSubClass);
    }
}
