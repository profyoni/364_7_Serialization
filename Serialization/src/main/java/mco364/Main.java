package mco364;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

class MyArrayList<T> implements Serializable {

    private T[] backingArray;
    private int ip = 0;

    public MyArrayList(){
        backingArray = (T[]) new Object[1000];
    }
    public void add(T t) {
        backingArray[ip++] = t;
    }

    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.writeInt(ip);
        for (int i = 0; i < ip; i++) {
            out.writeObject(backingArray[i]);
        }
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        ip = in.readInt();
        backingArray = (T[]) new Object[ip];
        for (int i = 0; i < ip; i++) {
            backingArray[i] = (T) in.readObject();
        }
    }
}

class Person implements Serializable {

    int age;
    String first, last;

    @Override
    public String toString() {
        return "Person{" + "first=" + first + ", last=" + last + '}';
    }

    public Person(String first, String last) {
        this.first = first;
        this.last = last;
    }

}

/**
 *
 * @author jrobinson
 */
public class Main {

    public static void main(String[] args) throws IOException {
        MyArrayList<Person> list = new MyArrayList();

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream("peopleObject.txt")))) {
            oos.writeObject(list);
        }

    }

    public static void main2(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Person p = new Person("Yaakov", "Liff");

        try (PrintStream ps = new PrintStream("people.txt")) {
            ps.print(p.first + "                        \n\n\n\n\t\t\t" + p.last);

        } // wiil autoclose ps even if exception thrown //ps.close();

        ///try with resources isa roughly equivalent to the following
        PrintStream ps = null;
        try {
            ps = new PrintStream("list.obj");
        } finally {
            if (ps != null) {
                ps.close();
            }
        }

        Scanner sc = new Scanner(new FileInputStream("people.txt"));

        String first = sc.next();
        String last = sc.next();
        Person p1 = new Person(first, last);
        System.out.println(p1);

        ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream("peopleObject.txt")));

        oos.writeObject(p);

        oos.close();

        ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream("peopleObject.txt")));

        Person p3 = (Person) ois.readObject();
        System.out.println(p3);
        ois.close();

    }
}
