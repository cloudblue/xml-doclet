package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.RecordClass;

public class SimpledataSupport {

    static <T> String getSourceFile(java.lang.Class<T> clazz) {
        return "./src/test-jdk17/java/"
            + clazz.getCanonicalName().replaceAll("\\.", "/")
            + ".java";
    }

    static String getSourceFile(String clazz) {
        return "./src/test-jdk17/java/"
            + getSimpledataPackage().replaceAll("\\.", "/")
            + "/" + clazz + ".java";
    }

    static String getSimpledataPackage() {
        return RecordClass.class.getPackageName();
    }
}
