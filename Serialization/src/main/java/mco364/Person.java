package mco364;

import java.io.Serializable;

public class Person implements Serializable {

    int age;
    String first;
    String last;

    @Override
    public String toString() {
        return "Person{" + "first=" + first + ", last=" + last + '}';
    }

    public Person() {
    }

    public Person(String first, String last) {
        this.first = first;
        this.last = last;
    }

}
