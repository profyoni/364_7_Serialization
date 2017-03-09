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

    transient  T[] backingArray; // transient only affects default Serialiaztion
    transient int ip = 0;

    public MyArrayList(){
        backingArray = (T[]) new Object[1000];
    }
    public void add(T t) {
        backingArray[ip++] = t;
    }
    // custom serilaiztion
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.defaultWriteObject(); // Bloch, Item 75, pg 299, beyond scope of lecture
        
        out.writeInt(ip);
        for (int i = 0; i < ip; i++) {
            out.writeObject(backingArray[i]);
        }
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {

        in.defaultReadObject();
        
        ip = in.readInt();
        backingArray = (T[]) new Object[ip];
        for (int i = 0; i < ip; i++) {
            backingArray[i] = (T) in.readObject();
        }
    }
}



/**
 *
 * @author jrobinson
 */
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MyArrayList<Person> list = new MyArrayList();
        list.add(new Person("Shmuel","Morris"));

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream("peopleObject.txt")))) {
            oos.writeObject(list);
        }
        
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream("peopleObject.txt")))) {
            list = (MyArrayList<Person>) ois.readObject();
        }
        
        for (int i=0;i<list.ip;i++)
        {
            System.out.println( list.backingArray[i].first);
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
