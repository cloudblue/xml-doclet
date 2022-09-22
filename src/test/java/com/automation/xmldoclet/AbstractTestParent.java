package com.automation.xmldoclet;

import com.automation.xmldoclet.simpledata.Annotation1;
import com.automation.xmldoclet.xjc.Root;

import javax.xml.bind.JAXB;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.spi.ToolProvider;

/**
 * Base class for all tests.
 */
public class AbstractTestParent {

    /**
     * Processes the source code using javadoc.
     *
     * @param extendedClassPath   Any classpath information required to help along javadoc.
     *                            Javadoc will actually compile the source code you specify; so
     *                            if there are any jars or classes that are referenced by the
     *                            source code to process, then including those compiled items in
     *                            the classpath will give you more complete data in the
     *                            resulting XML.
     * @param sourcePaths         Usually sourcePaths is specified in conjunction with
     *                            either/both packages & subpackages. The source-paths value
     *                            should be the path of the source files right before the
     *                            standard package-based folder layout of projects begins. For
     *                            example, if you have code that exists in package foo.bar, and
     *                            your code is physically in /MyFolder/foo/bar/ , then the
     *                            sourcePaths would be /MyFolder
     * @param packages            Use if you want to detail specific packages to process
     *                            (contrast with subpackages, which is probably the easiest/most
     *                            brute force way of using xml-doclet). If you have within your
     *                            code two packages, foo.bar and bar.foo, but only wanted
     *                            foo.bar processed, then specify just 'foo.bar' for this
     *                            argument.
     * @param sourceFiles         You can specify source files individually. This usually is
     *                            used instead of sourcePaths/subPackages/packages. If you use
     *                            this parameter, specify the full path of any java file you
     *                            want processed.
     * @param subPackages         You can specify 'subPackages', which simply gives one an easy
     *                            way to specify the root package, and have javadoc recursively
     *                            look through everything under that package. So for instance,
     *                            if you had foo.bar, foo.bar.bar, and bar.foo, specifying 'foo'
     *                            will process foo.bar and foo.bar.bar packages, but not bar.foo
     *                            (unless you specify 'bar' as a subpackage, too)
     * @param additionalArguments Additional Arguments.
     * @return XStream compatible data structure
     */
    public Root executeJavadoc(String extendedClassPath, String[] sourcePaths, String[] packages,
                               String[] sourceFiles,
                               String[] subPackages, String[] additionalArguments) {
        // aggregate arguments and packages
        List<String> argumentList = new ArrayList<>();

        // use XmlDoclet
        argumentList.add("-doclet");
        argumentList.add(XmlDoclet.class.getCanonicalName());

        // by setting this to 'private', nothing is omitted in the parsing
        argumentList.add("-private");

        String classPath = System.getProperty("java.class.path", ".");
        if (extendedClassPath != null) {
            classPath += File.pathSeparator + extendedClassPath;
        }
        argumentList.add("-classpath");
        argumentList.add(classPath);

        if (sourcePaths != null) {
            String concatenatedSourcePaths = String.join(File.pathSeparator, sourcePaths);
            if (concatenatedSourcePaths.length() > 0) {
                argumentList.add("-sourcepath");
                argumentList.add(concatenatedSourcePaths);
            }
        }

        if (subPackages != null) {
            String concatenatedSubPackages = String.join(";", subPackages);
            if (concatenatedSubPackages.length() > 0) {
                argumentList.add("-subpackages");
                argumentList.add(concatenatedSubPackages);
            }
        }

        if (packages != null) {
            argumentList.addAll(Arrays.asList(packages));
        }

        if (sourceFiles != null) {
            argumentList.addAll(Arrays.asList(sourceFiles));
        }

        if (additionalArguments != null) {
            argumentList.addAll(Arrays.asList(additionalArguments));
        }

        System.out.println("Executing doclet with arguments: " + String.join(" ", argumentList));

        String[] arguments = argumentList.toArray(new String[] {});
        ToolProvider javadoc = ToolProvider.findFirst("javadoc").orElseThrow();
        int res = javadoc.run(System.out, System.err, arguments);
        System.out.println("Done with doclet processing");
        if (res != 0) {
            throw new RuntimeException("Doclet failed");
        }

        return XmlDoclet.root;
    }

    /**
     * Use for debugging tests.
     */
    static String getAsXml(Object jaxbObject) {
        final StringWriter writer = new StringWriter();
        JAXB.marshal(jaxbObject, writer);
        return writer.toString();
    }

    static <T> String getSourceFile(java.lang.Class<T> clazz) {
        return "./src/test/java/"
            + clazz.getCanonicalName().replaceAll("\\.", "/")
            + ".java";
    }

    static String getSourceFile(String clazz) {
        return "./src/test/java/"
            + getSimpledataPackage().replaceAll("\\.", "/")
            + "/" + clazz + ".java";
    }

    static String getSimpledataPackage() {
        return Annotation1.class.getPackageName();
    }
}
