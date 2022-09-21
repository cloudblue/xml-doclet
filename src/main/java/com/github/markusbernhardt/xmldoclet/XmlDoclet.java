package com.github.markusbernhardt.xmldoclet;

import com.github.markusbernhardt.xmldoclet.util.DocletOption;
import com.github.markusbernhardt.xmldoclet.xjc.Root;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class XmlDoclet implements Doclet {

    /**
     * For tests.
     */
    public static Root root;

    private String directory;
    private String encoding;
    private boolean dryrun;
    private String filename;

    @Override
    public void init(Locale locale, Reporter reporter) {
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Set<? extends Option> getSupportedOptions() {
        return new LinkedHashSet<>(List.of(
            DocletOption.ofParameter(
                List.of("-d"),
                "directory",
                "Destination directory for output file.\nDefault: .",
                argument -> this.directory = argument
            ),
            DocletOption.ofParameter(
                List.of("-docencoding"),
                "encoding",
                "Encoding of the output file.\nDefault: UTF8",
                argument -> this.encoding = argument
            ),
            DocletOption.ofFlag(
                List.of("-dryrun"),
                "Parse javadoc, but don't write output file.\nDefault: false",
                () -> this.dryrun = true
            ),
            DocletOption.ofParameter(
                List.of("-filename"),
                "filename",
                "Name of the output file.\nDefault: javadoc.xml",
                argument -> this.filename = argument
            )
        ));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean run(DocletEnvironment environment) {
        final Root xmlRoot = new Parser(environment).transform();
        root = xmlRoot;
        save(xmlRoot);
        return true;
    }

    private void save(Root xmlRoot) {
        ClassLoader originalClassLoader = null;
        try {
            originalClassLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            if (dryrun) {
                return;
            }
            final JAXBContext context = JAXBContext.newInstance(Root.class);
            final Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            if (encoding != null) {
                marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            }
            final String name = (directory != null ? directory + File.separator : "") + (filename != null ? filename : "javadoc.xml");
            try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(name))) {
                marshaller.marshal(xmlRoot, outputStream);
            }
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (originalClassLoader != null) {
                Thread.currentThread().setContextClassLoader(originalClassLoader);
            }
        }
    }

}
