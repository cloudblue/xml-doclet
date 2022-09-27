xml-doclet
=================================

This library provides a doclet to output the javadoc comments from Java source code to an XML document.  
Works with Java 11 and above, supporting up to Java 17 (handling Records and Sealed classes as normal classes).  

Usage
-----

If you are using Maven you can use this doclet by adding the following configuration to your pom.xml:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-javadoc-plugin</artifactId>
  <configuration>
    <doclet>com.automation.xmldoclet.XmlDoclet</doclet>
    <additionalparam>
        -d ${project.build.directory}
        -filename ${project.artifactId}-${project.version}-javadoc.xml
    </additionalparam>
    <useStandardDocletOptions>false</useStandardDocletOptions>
    <docletArtifact>
      <groupId>com.automation</groupId>
      <artifactId>xml-doclet</artifactId>
      <version>2.0.0</version>
    </docletArtifact>
  </configuration>
</plugin>
```

If you are not using maven, you can use the [jar-with-dependencies](), which contains all required libraries.

```shell
javadoc -doclet com.automation.xmldoclet.XmlDoclet \
  -docletpath xml-doclet-2.0.0-jar-with-dependencies.jar \
  [Javadoc- and XmlDoclet-Options]
```

Options
-------

```
-d <directory>            Destination directory for output file.
                          Default: .

-docencoding <encoding>   Encoding of the output file.
                          Default: UTF8

-dryrun                   Parse javadoc, but don't write output file.
                          Default: false

-filename <filename>      Name of the output file.
                          Default: javadoc.xml
```

Output
------

Gives a Java class like this:

```java
package com.example;

/**
 * Worlds greeter.
 */
public class HelloWorld {

    /**
     * Greets all worlds.
     *
     * @param worlds list of worlds to greet
     * @return       a greeting to everyone!
     */
    public String hello(List<String> worlds) {
        return "Hello " + worlds.toString();
    }
}
```

The doclet will generate the following XML document:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<root>
  <package name="com.example">
    <class name="AnnotatedParameter" qualified="com.example.HelloWorld" scope="public" abstract="false" error="false" exception="false" externalizable="false" included="true" serializable="false">
      <comment>Worlds greeter.</comment>
      <class qualified="java.lang.Object"/>
      <constructor name="AnnotatedParameter" signature="()" qualified="com.example.HelloWorld" scope="public" final="false" included="true" native="false" synchronized="false" static="false" varArgs="false"/>
      <method name="hello" signature="(java.util.List&lt;java.lang.String&gt;)" qualified="com.example.HelloWorld.hello" scope="public" abstract="false" final="false" included="true" native="false" synchronized="false" static="false" varArgs="false">
        <comment>Greets all worlds.</comment>
        <tag name="@param" text="worlds list of worlds to greet"/>
        <tag name="@return" text="a greeting to everyone!"/>
        <parameter name="worlds">
          <type qualified="java.util.List">
            <generic qualified="java.lang.String"/>
          </type>
        </parameter>
        <return qualified="java.lang.String"/>
      </method>
    </class>
  </package>
</root>
```

Credits
-------

Some ideas and name came from Seth Call: ([xml-doclet](http://code.google.com/p/xml-doclet)).  
Reimplemented by MarkusBernhardt: ([xml-doclet](https://github.com/MarkusBernhardt/xml-doclet)).  
Ported to the new Javadoc API for Java 11+ by vojtechhabarta: ([xml-doclet](https://github.com/vojtechhabarta/xml-doclet/tree/rewrite-using-new-javadoc-api)).  
And now maintained by CloudBlue.

License
-------

```
Copyright 2018, MarkusBernhardt
Copyright 2020, vojtechhabarta
Copyright 2022, CloudBlue

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```