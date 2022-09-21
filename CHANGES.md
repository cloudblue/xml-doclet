CHANGES
=======

2.0.0
----------
* Reimplemented to use the new Javadoc API (Java 11+ support). Thanks to vojtechhabarta!
* Known side effect:  
  Parameterized types does not include the space between type arguments anymore.  
  This means Map<String, String> becomes Map<String,String>.  

1.0.5
-----
* Added the support for reading javadoc of fields in an Interface. See #6. Thanks to sandeshh!

1.0.4
-----
* Make xml-doclet java 1.6 compatible. See #5. Thanks to Jussi Malinen!

1.0.2
-----
* Changed the scope of the jar-with-dependencies to 'test'.

1.0.1
-----
* Added javadoc block tags, like @param or @see, to the generated XML output. See #2
* Switched from using XML elements to XML attributes wherever possible to reduce the output size.

1.0.0
-----
* Initial release