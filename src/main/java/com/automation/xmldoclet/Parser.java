package com.automation.xmldoclet;

import com.automation.xmldoclet.xjc.Annotation;
import com.automation.xmldoclet.xjc.AnnotationArgument;
import com.automation.xmldoclet.xjc.AnnotationElement;
import com.automation.xmldoclet.xjc.AnnotationInstance;
import com.automation.xmldoclet.xjc.Class;
import com.automation.xmldoclet.xjc.Constructor;
import com.automation.xmldoclet.xjc.Enum;
import com.automation.xmldoclet.xjc.EnumConstant;
import com.automation.xmldoclet.xjc.Field;
import com.automation.xmldoclet.xjc.Interface;
import com.automation.xmldoclet.xjc.Method;
import com.automation.xmldoclet.xjc.MethodParameter;
import com.automation.xmldoclet.xjc.Package;
import com.automation.xmldoclet.xjc.Root;
import com.automation.xmldoclet.xjc.TagInfo;
import com.automation.xmldoclet.xjc.TypeInfo;
import com.automation.xmldoclet.xjc.TypeParameter;
import com.automation.xmldoclet.xjc.Wildcard;
import com.sun.source.doctree.BlockTagTree;
import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import com.sun.source.util.DocTrees;
import jdk.javadoc.doclet.DocletEnvironment;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import java.io.Externalizable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Parser {

    private Root xmlRoot;

    private final DocletEnvironment environment;
    private final DocTrees docTreesUtils;
    private final Elements elementUtils;
    private final Types typeUtils;

    // Utilities for comparison
    private final TypeMirror objectType;
    private final TypeMirror errorType;
    private final TypeMirror exceptionType;
    private final TypeMirror externalizableType;
    private final TypeMirror serializableType;

    public Parser(DocletEnvironment environment) {
        this.environment = environment;
        this.docTreesUtils = environment.getDocTrees();
        this.elementUtils = environment.getElementUtils();
        this.typeUtils = environment.getTypeUtils();

        this.objectType = elementUtils.getTypeElement(Object.class.getCanonicalName()).asType();
        this.errorType = elementUtils.getTypeElement(Error.class.getCanonicalName()).asType();
        this.exceptionType = elementUtils.getTypeElement(Exception.class.getCanonicalName()).asType();
        this.externalizableType = elementUtils.getTypeElement(Externalizable.class.getCanonicalName()).asType();
        this.serializableType = elementUtils.getTypeElement(Serializable.class.getCanonicalName()).asType();
    }

    public Root transform() {
        setXmlRoot(new Root());
        transformElements(environment.getSpecifiedElements());
        return getXmlRoot();
    }

    private void transformElements(Collection<? extends Element> elements) {
        for (Element element : elements) {
            transformElement(element);
        }
    }

    private void transformElement(Element element) {
        if (element.getKind() == ElementKind.PACKAGE) {
            transformPackageElement((PackageElement) element);
        }
        if (element.getKind().isClass() || element.getKind().isInterface()) {
            transformTypeElement((TypeElement) element);
        }
    }

    private void transformPackageElement(PackageElement packageElement) {
        final Package xmlPackage = new Package();
        xmlPackage.setName(packageElement.getQualifiedName().toString());
        transformJavadoc(packageElement, xmlPackage::setComment, xmlPackage.getTag());
        getXmlRoot().getPackage().add(xmlPackage);
        transformElements(packageElement.getEnclosedElements());
    }

    private void transformTypeElement(TypeElement typeElement) {
        if (environment.getFileKind(typeElement) != JavaFileObject.Kind.SOURCE) {
            return;
        }
        if (!environment.isIncluded(typeElement)) {
            return;
        }
        if (typeElement.getKind() == ElementKind.ANNOTATION_TYPE) {
            transformAnnotationType(typeElement);

        } else if (typeElement.getKind() == ElementKind.ENUM) {
            transformEnumType(typeElement);

        } else if (typeElement.getKind() == ElementKind.INTERFACE) {
            transformInterfaceType(typeElement);

        } else if (typeElement.getKind() == ElementKind.CLASS || typeElement.getKind().toString().equals("RECORD")) {
            transformClassType(typeElement);

        } else {
            transformElements(typeElement.getEnclosedElements());
        }
    }

    private void transformClassType(TypeElement typeElement) {
        final Class xmlClass = new Class();
        setNames(typeElement, xmlClass::setName, xmlClass::setQualified);
        xmlClass.setAbstract(hasModifier(typeElement, Modifier.ABSTRACT));
        xmlClass.setScope(getScope(typeElement));
        xmlClass.setError(typeUtils.isAssignable(typeElement.asType(), errorType));
        xmlClass.setException(typeUtils.isAssignable(typeElement.asType(), exceptionType));
        xmlClass.setSerializable(typeUtils.isAssignable(typeElement.asType(), serializableType));
        xmlClass.setExternalizable(typeUtils.isAssignable(typeElement.asType(), externalizableType));
        xmlClass.setIncluded(environment.isIncluded(typeElement));
        transformJavadoc(typeElement, xmlClass::setComment, xmlClass.getTag());
        xmlClass.getGeneric().addAll(transformTypeParameters(typeElement.getTypeParameters()));
        xmlClass.setClazz(transformTypeMirror(typeElement.getSuperclass()));
        xmlClass.getInterface().addAll(transformTypeMirrors(typeElement.getInterfaces()));
        xmlClass.getAnnotation().addAll(transformAnnotationMirrors(typeElement.getAnnotationMirrors()));
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            if (!environment.isIncluded(enclosedElement)) {
                continue;
            }
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                final VariableElement fieldElement = (VariableElement) enclosedElement;
                xmlClass.getField().add(transformFieldElement(fieldElement));

            } else if (enclosedElement.getKind() == ElementKind.CONSTRUCTOR) {
                final ExecutableElement constructorElement = (ExecutableElement) enclosedElement;
                xmlClass.getConstructor().add(transformConstructorElement(constructorElement));

            } else if (enclosedElement.getKind() == ElementKind.METHOD) {
                final ExecutableElement methodElement = (ExecutableElement) enclosedElement;
                xmlClass.getMethod().add(transformMethodElement(methodElement));
            }
        }

        getXmlPackage(typeElement).getClazz().add(xmlClass);

        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            if (enclosedElement.getKind().isClass() || enclosedElement.getKind().isInterface() ) {
                final TypeElement enclosedTypeElement = (TypeElement) enclosedElement;
                transformTypeElement(enclosedTypeElement);
            }
        }
    }

    private void transformInterfaceType(TypeElement typeElement) {
        final Interface xmlInterface = new Interface();
        setNames(typeElement, xmlInterface::setName, xmlInterface::setQualified);
        xmlInterface.setScope(getScope(typeElement));
        xmlInterface.setIncluded(environment.isIncluded(typeElement));
        transformJavadoc(typeElement, xmlInterface::setComment, xmlInterface.getTag());
        xmlInterface.getGeneric().addAll(transformTypeParameters(typeElement.getTypeParameters()));
        xmlInterface.getInterface().addAll(transformTypeMirrors(typeElement.getInterfaces()));
        xmlInterface.getAnnotation().addAll(transformAnnotationMirrors(typeElement.getAnnotationMirrors()));
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            if (!environment.isIncluded(enclosedElement)) {
                continue;
            }
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                final VariableElement fieldElement = (VariableElement) enclosedElement;
                xmlInterface.getField().add(transformFieldElement(fieldElement));
            }
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                final ExecutableElement methodElement = (ExecutableElement) enclosedElement;
                xmlInterface.getMethod().add(transformMethodElement(methodElement));
            } else if (enclosedElement.getKind() == ElementKind.CLASS ||
                    enclosedElement.getKind() == ElementKind.ANNOTATION_TYPE ||
                    enclosedElement.getKind() == ElementKind.INTERFACE ) {
                final TypeElement enclosedTypeElement = (TypeElement) enclosedElement;
                transformTypeElement(enclosedTypeElement);
            }
        }
        getXmlPackage(typeElement).getInterface().add(xmlInterface);
    }

    private void transformEnumType(TypeElement typeElement) {
        final Enum xmlEnum = new Enum();
        setNames(typeElement, xmlEnum::setName, xmlEnum::setQualified);
        xmlEnum.setScope(getScope(typeElement));
        xmlEnum.setIncluded(environment.isIncluded(typeElement));
        transformJavadoc(typeElement, xmlEnum::setComment, xmlEnum.getTag());
        xmlEnum.setClazz(transformTypeMirror(typeElement.getSuperclass()));
        xmlEnum.getInterface().addAll(transformTypeMirrors(typeElement.getInterfaces()));
        xmlEnum.getAnnotation().addAll(transformAnnotationMirrors(typeElement.getAnnotationMirrors()));
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            if (!environment.isIncluded(enclosedElement)) {
                continue;
            }
            if (enclosedElement.getKind() == ElementKind.ENUM_CONSTANT) {
                final VariableElement constantElement = (VariableElement) enclosedElement;
                xmlEnum.getConstant().add(transformEnumConstant(constantElement));
            }
        }
        getXmlPackage(typeElement).getEnum().add(xmlEnum);
    }

    private void transformAnnotationType(TypeElement typeElement) {
        final Annotation xmlAnnotation = new Annotation();
        setNames(typeElement, xmlAnnotation::setName, xmlAnnotation::setQualified);
        xmlAnnotation.setScope(getScope(typeElement));
        xmlAnnotation.setIncluded(environment.isIncluded(typeElement));
        transformJavadoc(typeElement, xmlAnnotation::setComment, xmlAnnotation.getTag());
        xmlAnnotation.getAnnotation().addAll(transformAnnotationMirrors(typeElement.getAnnotationMirrors()));
        for (Element enclosedElement : typeElement.getEnclosedElements()) {
            if (!environment.isIncluded(enclosedElement)) {
                continue;
            }
            if (enclosedElement.getKind() == ElementKind.METHOD) {
                final ExecutableElement methodElement = (ExecutableElement) enclosedElement;
                xmlAnnotation.getElement().add(transformAnnotationElement(methodElement));
            }
        }
        getXmlPackage(typeElement).getAnnotation().add(xmlAnnotation);
    }

    private AnnotationElement transformAnnotationElement(ExecutableElement annotationElement) {
        final AnnotationElement xmlElement = new AnnotationElement();
        xmlElement.setName(annotationElement.getSimpleName().toString());
        xmlElement.setQualified(getEnclosingQualified(annotationElement) + "." + xmlElement.getName());
        if (annotationElement.getDefaultValue() != null) {
            xmlElement.setDefault(String.valueOf(annotationElement.getDefaultValue().getValue()));
        }
        xmlElement.setType(transformTypeMirror(annotationElement.getReturnType()));
        return xmlElement;
    }

    private EnumConstant transformEnumConstant(VariableElement constantElement) {
        final EnumConstant xmlConstant = new EnumConstant();
        xmlConstant.setName(constantElement.getSimpleName().toString());
        transformJavadoc(constantElement, xmlConstant::setComment, xmlConstant.getTag());
        xmlConstant.getAnnotation().addAll(transformAnnotationMirrors(constantElement.getAnnotationMirrors()));
        return xmlConstant;
    }

    private Field transformFieldElement(VariableElement variableElement) {
        final Field xmlField = new Field();
        xmlField.setName(variableElement.getSimpleName().toString());
        xmlField.setQualified(getEnclosingQualified(variableElement) + "." + xmlField.getName());
        xmlField.setScope(getScope(variableElement));
        xmlField.setVolatile(hasModifier(variableElement, Modifier.VOLATILE));
        xmlField.setTransient(hasModifier(variableElement, Modifier.TRANSIENT));
        xmlField.setStatic(hasModifier(variableElement, Modifier.STATIC));
        xmlField.setFinal(hasModifier(variableElement, Modifier.FINAL));
        xmlField.setType(transformTypeMirror(variableElement.asType()));
        transformJavadoc(variableElement, xmlField::setComment, xmlField.getTag());
        final Object constantValue = variableElement.getConstantValue();
        if (constantValue != null) {
            xmlField.setConstant(elementUtils.getConstantExpression(constantValue));
        }
        xmlField.getAnnotation().addAll(transformAnnotationMirrors(variableElement.getAnnotationMirrors()));
        return xmlField;
    }

    private Constructor transformConstructorElement(ExecutableElement constructorElement) {
        final Constructor xmlConstructor = new Constructor();
        xmlConstructor.setName(constructorElement.getEnclosingElement().getSimpleName().toString());
        xmlConstructor.setSignature(getSignature(constructorElement));
        xmlConstructor.setQualified(getEnclosingQualified(constructorElement));
        xmlConstructor.setScope(getScope(constructorElement));
        xmlConstructor.setFinal(hasModifier(constructorElement, Modifier.FINAL));
        xmlConstructor.setIncluded(environment.isIncluded(constructorElement));
        xmlConstructor.setNative(hasModifier(constructorElement, Modifier.NATIVE));
        xmlConstructor.setSynchronized(hasModifier(constructorElement, Modifier.SYNCHRONIZED));
        xmlConstructor.setStatic(hasModifier(constructorElement, Modifier.STATIC));
        xmlConstructor.setVarArgs(constructorElement.isVarArgs());
        transformJavadoc(constructorElement, xmlConstructor::setComment, xmlConstructor.getTag());
        xmlConstructor.getParameter().addAll(transformParameters(constructorElement));
        xmlConstructor.getException().addAll(transformExceptions(constructorElement));
        xmlConstructor.getAnnotation().addAll(transformAnnotationMirrors(constructorElement.getAnnotationMirrors()));
        return xmlConstructor;
    }

    private Method transformMethodElement(ExecutableElement methodElement) {
        final Method xmlMethod = new Method();
        xmlMethod.setName(methodElement.getSimpleName().toString());
        xmlMethod.setSignature(getSignature(methodElement));
        xmlMethod.setQualified(((QualifiedNameable) methodElement.getEnclosingElement()).getQualifiedName() + "." + xmlMethod.getName());
        xmlMethod.setScope(getScope(methodElement));
        xmlMethod.setAbstract(hasModifier(methodElement, Modifier.ABSTRACT));
        xmlMethod.setFinal(hasModifier(methodElement, Modifier.FINAL));
        xmlMethod.setIncluded(environment.isIncluded(methodElement));
        xmlMethod.setNative(hasModifier(methodElement, Modifier.NATIVE));
        xmlMethod.setSynchronized(hasModifier(methodElement, Modifier.SYNCHRONIZED));
        xmlMethod.setStatic(hasModifier(methodElement, Modifier.STATIC));
        xmlMethod.setVarArgs(methodElement.isVarArgs());
        transformJavadoc(methodElement, xmlMethod::setComment, xmlMethod.getTag());
        xmlMethod.getParameter().addAll(transformParameters(methodElement));
        xmlMethod.setReturn(transformTypeMirror(methodElement.getReturnType()));
        xmlMethod.getException().addAll(transformExceptions(methodElement));
        xmlMethod.getAnnotation().addAll(transformAnnotationMirrors(methodElement.getAnnotationMirrors()));
        return xmlMethod;
    }

    private List<TypeInfo> transformExceptions(ExecutableElement executableElement) {
        return transformTypeMirrors(executableElement.getThrownTypes());
    }

    private List<MethodParameter> transformParameters(ExecutableElement executableElement) {
        return executableElement.getParameters().stream()
            .map(this::transformParameter)
            .collect(Collectors.toList());
    }

    private MethodParameter transformParameter(VariableElement methodParameter) {
        final MethodParameter xmlParameter = new MethodParameter();
        xmlParameter.setName(methodParameter.getSimpleName().toString());
        xmlParameter.setType(transformTypeMirror(methodParameter.asType()));
        xmlParameter.getAnnotation().addAll(transformAnnotationMirrors(methodParameter.getAnnotationMirrors()));
        return xmlParameter;
    }

    private List<AnnotationInstance> transformAnnotationMirrors(List<? extends AnnotationMirror> annotationMirrors) {
        return annotationMirrors.stream()
            .map(this::transformAnnotationMirror)
            .collect(Collectors.toList());
    }

    private AnnotationInstance transformAnnotationMirror(AnnotationMirror annotationMirror) {
        final AnnotationInstance xmlAnnotation = new AnnotationInstance();
        setNames(annotationMirror.getAnnotationType().asElement(), xmlAnnotation::setName, xmlAnnotation::setQualified);
        xmlAnnotation.getArgument().addAll(transformAnnotationValues(annotationMirror.getElementValues()));
        return xmlAnnotation;
    }

    private List<AnnotationArgument> transformAnnotationValues(Map<? extends ExecutableElement, ? extends AnnotationValue> annotationValues) {
        return annotationValues.entrySet().stream()
            .map(entry -> transformAnnotationValue(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    private AnnotationArgument transformAnnotationValue(ExecutableElement element, AnnotationValue annotationValue) {
        final AnnotationArgument xmlArgument = new AnnotationArgument();
        xmlArgument.setName(element.getSimpleName().toString());
        final TypeMirror type = element.getReturnType();
        xmlArgument.setType(transformTypeMirror(type));
        final Object value = annotationValue.getValue();
        if (type.getKind() == TypeKind.ARRAY) {
            final ArrayType arrayType = (ArrayType) type;
            final TypeMirror componentType = arrayType.getComponentType();
            xmlArgument.setPrimitive(componentType.getKind().isPrimitive());
            xmlArgument.setArray(true);
            if (value instanceof List<?>) {
                final List<?> list = (List<?>) value;
                for (Object item : list) {
                    if (item instanceof AnnotationValue) {
                        final AnnotationValue annotationValueItem = (AnnotationValue) item;
                        transformAnnotationSingleValue(xmlArgument, annotationValueItem.getValue(), componentType);
                    }
                }
            }
        } else {
            xmlArgument.setPrimitive(type.getKind().isPrimitive());
            xmlArgument.setArray(false);
            transformAnnotationSingleValue(xmlArgument, value, type);
        }
        return xmlArgument;
    }

    private void transformAnnotationSingleValue(AnnotationArgument xmlArgument, Object value, TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            if (value instanceof Character) {
                final Character character = (Character) value;
                xmlArgument.getValue().add(String.valueOf((long) character));
            } else {
                xmlArgument.getValue().add(String.valueOf(value));
            }
        } else if (value instanceof String) {
            xmlArgument.getValue().add(String.valueOf(value));
        } else if (value instanceof TypeMirror) {
            final TypeMirror typeMirror = (TypeMirror) value;
            xmlArgument.getValue().add(typeMirror.toString());
        } else if (value instanceof VariableElement) {
            final VariableElement variableElement = (VariableElement) value;
            xmlArgument.getValue().add(variableElement.getSimpleName().toString());
        } else if (value instanceof AnnotationMirror) {
            final AnnotationMirror annotationMirror = (AnnotationMirror) value;
            xmlArgument.getAnnotation().add(transformAnnotationMirror(annotationMirror));
        } else if (value instanceof List<?>) {
            // already handled by calling method
        }
    }

    private List<TypeParameter> transformTypeParameters(List<? extends TypeParameterElement> typeParameterElements) {
        return typeParameterElements.stream()
            .map(this::transformTypeParameter)
            .collect(Collectors.toList());
    }

    private TypeParameter transformTypeParameter(TypeParameterElement typeParameterElement) {
        final TypeParameter xmlTypeParameter = new TypeParameter();
        xmlTypeParameter.setName(typeParameterElement.getSimpleName().toString());
        final List<String> bounds = typeParameterElement.getBounds().stream()
            .filter(bound -> !typeUtils.isSameType(bound, objectType))
            .map(TypeMirror::toString)
            .collect(Collectors.toList());
        xmlTypeParameter.getBound().addAll(bounds);
        return xmlTypeParameter;
    }

    private void transformJavadoc(Element element, Consumer<String> commentSetter, List<TagInfo> tags) {
        final DocCommentTree docCommentTree = docTreesUtils.getDocCommentTree(element);
        if (docCommentTree != null) {
            final String fullBody = docCommentTree.getFullBody().stream()
                .map(Object::toString)
                .collect(Collectors.joining());
            if (!fullBody.isEmpty()) {
                commentSetter.accept(fullBody);
            }
            for (DocTree blockTag : docCommentTree.getBlockTags()) {
                if (blockTag instanceof BlockTagTree) {
                    final BlockTagTree blockTagTree = (BlockTagTree) blockTag;
                    final String[] parts = blockTagTree.toString().split(" ", 2);
                    if (parts.length == 2) {
                        final TagInfo tag = new TagInfo();
                        tag.setName(parts[0]);
                        tag.setText(parts[1]);
                        tags.add(tag);
                    }
                }
            }
        }
    }

    private List<TypeInfo> transformTypeMirrorIfNonNull(TypeMirror typeMirror) {
        return typeMirror != null
            ? Collections.singletonList(transformTypeMirror(typeMirror))
            : Collections.emptyList();
    }

    private List<TypeInfo> transformTypeMirrors(List<? extends TypeMirror> typeMirrors) {
        return typeMirrors.stream()
            .map(this::transformTypeMirror)
            .collect(Collectors.toList());
    }

    private TypeInfo transformTypeMirror(TypeMirror typeMirror) {
        TypeInfo xmlTypeInfo = new TypeInfo();
        if (typeMirror.getKind() == TypeKind.DECLARED) {
            xmlTypeInfo.setQualified(stripType(typeUtils.erasure(typeMirror).toString()));
            final DeclaredType declaredType = (DeclaredType) typeMirror;
            final List<TypeInfo> generic = declaredType.getTypeArguments().stream()
                .map(this::transformTypeMirror)
                .collect(Collectors.toList());
            xmlTypeInfo.getGeneric().addAll(generic);
        } else if (typeMirror.getKind() == TypeKind.ARRAY) {
            final ArrayType arrayType = (ArrayType) typeMirror;
            xmlTypeInfo = transformTypeMirror(arrayType.getComponentType());
            xmlTypeInfo.setDimension((xmlTypeInfo.getDimension() != null ? xmlTypeInfo.getDimension() : "") + "[]");
        } else if (typeMirror.getKind() == TypeKind.WILDCARD) {
            xmlTypeInfo.setQualified("?");
            final WildcardType wildcardType = (WildcardType) typeMirror;
            final Wildcard wildcard = new Wildcard();
            wildcard.getExtendsBound().addAll(transformTypeMirrorIfNonNull(wildcardType.getExtendsBound()));
            wildcard.getSuperBound().addAll(transformTypeMirrorIfNonNull(wildcardType.getSuperBound()));
            xmlTypeInfo.setWildcard(wildcard);
        } else {
            xmlTypeInfo.setQualified(stripType(typeMirror.toString()));
        }
        return xmlTypeInfo;
    }


    private PackageElement getEnclosingPackage(Element element) {
        Objects.requireNonNull(element);
        return element.getKind() == ElementKind.PACKAGE
            ? (PackageElement) element
            : getEnclosingPackage(element.getEnclosingElement());
    }

    private String getScope(Element element) {
        final Set<Modifier> modifiers = element.getModifiers();
        if (modifiers.contains(Modifier.PUBLIC)) return "public";
        if (modifiers.contains(Modifier.PROTECTED)) return "protected";
        if (modifiers.contains(Modifier.PRIVATE)) return "private";
        return "";
    }

    private String getSignature(ExecutableElement executableElement) {
        final StringBuilder sb = new StringBuilder();
        sb.append('(');
        final List<? extends VariableElement> parameters = executableElement.getParameters();
        final Iterator<? extends VariableElement> iterator = parameters.iterator();
        while (iterator.hasNext()) {
            final VariableElement variableElement = iterator.next();
            if (variableElement.asType().getKind() == TypeKind.ARRAY && executableElement.isVarArgs()) {
                final ArrayType arrayType = (ArrayType) variableElement.asType();
                sb.append(stripType(arrayType.getComponentType().toString()));
                sb.append("...");
            } else {
                sb.append(stripType(variableElement.asType().toString()));
            }
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }

    private boolean hasModifier(Element element, Modifier modifier) {
        return element.getModifiers().contains(modifier);
    }

    private Package getXmlPackage(Element element) {
        return getXmlPackage(getEnclosingPackage(element));
    }

    private Package getXmlPackage(PackageElement packageElement) {
        return getXmlRoot().getPackage().stream()
            .filter(p -> Objects.equals(p.getName(), packageElement.getQualifiedName().toString()))
            .findFirst()
            .orElseGet(() -> {
                final Package xmlPackage = new Package();
                xmlPackage.setName(packageElement.getQualifiedName().toString());
                getXmlRoot().getPackage().add(xmlPackage);
                return xmlPackage;
            });
    }

    private void setNames(Element element, Consumer<String> nameSetter, Consumer<String> qualifiedSetter) {
        final String packageName = getEnclosingPackage(element).getQualifiedName().toString();
        final String packagePrefix = packageName.isEmpty() ? "" : packageName + ".";
        final String qualifiedName = ((QualifiedNameable) element).getQualifiedName().toString();
        final String name = qualifiedName.substring(packagePrefix.length());
        nameSetter.accept(name);
        qualifiedSetter.accept(qualifiedName);
    }

    /**
     * Strips annotations from type.
     */
    private String stripType(String type) {
        return Arrays.stream(type.split(" "))
            .dropWhile(s -> s.charAt(0) == '@')
            .collect(Collectors.joining(" "));
    }

    private String getEnclosingQualified(Element element) {
        return ((QualifiedNameable) element.getEnclosingElement()).getQualifiedName().toString();
    }

    private Root getXmlRoot() {
        return xmlRoot;
    }

    private void setXmlRoot(Root xmlRoot) {
        this.xmlRoot = xmlRoot;
    }
}
