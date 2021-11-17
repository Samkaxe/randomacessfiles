import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String fileStringPath = "C:\\Users\\charl\\Desktop\\MyFile.myBinary";
    private static Path  thepath = Path.of(fileStringPath);
    public static void main(String[] args) {
        PersonFileManeger fileManeger = new PersonFileManeger(fileStringPath);
      List<Person> persons = fileManeger.getAllPersons();
        System.out.println(persons);
        Person p2 = new Person(2 , "peter");

        fileManeger.deletePerson(p2);
        System.out.println(fileManeger.getAllPersons());
       /*
        System.out.println(fileManeger.getPerosn(1));
        Person p = new Person(1 , "Jeppe");
        Person p2 = new Person(2 , " peter");
        Person p3 = new Person(3 , "trine");
        fileManeger.addPerson(p3);

        Person p = new Person(1 , "Jeppe");
        Person p2 = new Person(2 , " peter");
        Person p3 = new Person(3 , "trine");

        List<Person> persons= new ArrayList<>();
        persons.add(p);
        persons.add(p2);
        persons.add(p3);


       */
      //  List<Person> personFromFile = loadPresons();
      //  System.out.println(personFromFile);
        //fileManeger.updatePerson(new Person(2 , " peter"));


    }

    private static List<Person> loadPresons() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileStringPath))){
            return (List<Person>) ois.readObject();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null ;

    }

    private static void savePersons(List<Person> persons) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileStringPath));
            oos.writeObject(persons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
