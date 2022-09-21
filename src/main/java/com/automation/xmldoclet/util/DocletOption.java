package com.automation.xmldoclet.util;

import jdk.javadoc.doclet.Doclet;

import java.util.List;
import java.util.function.Consumer;

/**
 * Utility class for declaring Doclet options.
 */

public class DocletOption implements Doclet.Option {

    private final List<String> names;
    private final String parameters;
    private final String description;
    private final int argumentCount;
    private final Consumer<List<String>> processor;

    public static DocletOption ofParameter(List<String> names, String parameter, String description, Consumer<String> processor) {
        return new DocletOption(names, parameter, description, 1, arguments -> processor.accept(arguments.get(0)));
    }

    public static DocletOption ofFlag(List<String> names, String description, Runnable processor) {
        return new DocletOption(names, null, description, 0, arguments -> processor.run());
    }

    private DocletOption(List<String> names, String parameters, String description, int argumentCount, Consumer<List<String>> processor) {
        this.names = names;
        this.parameters = parameters;
        this.description = description;
        this.argumentCount = argumentCount;
        this.processor = processor;
    }

    @Override
    public int getArgumentCount() {
        return argumentCount;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Kind getKind() {
        return Kind.STANDARD;
    }

    @Override
    public List<String> getNames() {
        return names;
    }

    @Override
    public String getParameters() {
        return parameters;
    }

    @Override
    public boolean process(String option, List<String> arguments) {
        processor.accept(arguments);
        return true;
    }
}