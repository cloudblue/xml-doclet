package com.automation.xmldoclet.simpledata;

/**
 * Java 16 record class.
 *
 * @param x point in the X axis
 * @param y point in the Y axis
 */
public record RecordClass(int x, int y) {
}

//public abstract sealed class Person permits Employee, Manager {
//
//}
//
//public final class Employee extends Person {
//}
//
//public non-sealed class Manager extends Person {
//}