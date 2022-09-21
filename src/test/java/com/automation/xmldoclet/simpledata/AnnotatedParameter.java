package com.automation.xmldoclet.simpledata;

import java.util.List;
import java.util.Map;

/**
 * AnnotatedParameter
 */
public class AnnotatedParameter {

    /**
     * method1
     *
     * @param paramStr
     * @param paramsList
     * @param paramsArray
     * @param paramPrim
     * @param paramsArgs
     */
    void method1(@ParamAnnotation String paramStr, @ParamAnnotation List<String> paramsList,
                 @ParamAnnotation String[] paramsArray, @ParamAnnotation char paramPrim,
                 @ParamAnnotation Map<String, Integer> paramsMap, @ParamAnnotation String... paramsArgs) {

    }

}
