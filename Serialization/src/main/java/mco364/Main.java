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

class Person implements Serializable{

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

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Person p = new Person("Yaakov", "Liff");

        PrintStream ps = new PrintStream("people.txt");

        ps.print(p.first + "                        \n\n\n\n\t\t\t" + p.last);

        ps.close();

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
