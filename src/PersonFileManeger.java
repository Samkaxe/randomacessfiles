import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class PersonFileManeger {
    private static final int ID_SIZE = Integer.BYTES; // 4
    private static final int NAME_SIZE = 50;
    private static final int RECORD_SIZE = ID_SIZE + NAME_SIZE;
    private static final int  EMPTY_ID = -1 ;

    private File file;

    public PersonFileManeger(String filename) {
        this.file = new File(filename);
    }

    // define the interface
    public List<Person> getAllPersons() {
        // we allready have the reandom file

        // create the array list
        List<Person> persons = new ArrayList<>();

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            // get file pointer point  at  the first byte in the file  < the file.length to not reach out of the file
            while (raf.getFilePointer() < raf.length()) {
                // check on friday with jeppe line 29 to 35
                int id = raf.readInt();
                byte[] bytes = new byte[NAME_SIZE];
                raf.read(bytes);

                String name = new String(bytes).trim();
                Person p = new Person(id, name);
                persons.add(readPersonFromRaf(raf));

            }

            return persons;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addPerson(Person p) {

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.seek(raf.length()); // go to end of file
            raf.writeInt(p.getId());
            raf.writeBytes(String.format("%-" + NAME_SIZE + "s", p.getName()).substring(0, NAME_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(Person p){
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                if (id == p.getId()) {
                        raf.seek(raf.getFilePointer() - ID_SIZE);
                        raf.writeInt(EMPTY_ID);
                    return;
                }
                else

                    raf.skipBytes(NAME_SIZE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    private Person readPersonFromRaf(RandomAccessFile raf) throws IOException {
        int id = raf.readInt();
        byte[] bytes = new byte[NAME_SIZE];
        raf.read(bytes);
        String name = new String(bytes).trim();
        Person p = new Person(id, name);

        return p;
    }
    public Person getPerosn(int id){
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            while (raf.getFilePointer() < raf.length()) {
                int currentid = raf.readInt();
                if (currentid == id) {

                    byte[] bytes = new byte[NAME_SIZE];
                    raf.read(bytes);
                    String name = new String(bytes).trim();
                    Person p = new Person(id, name);
                    return p ;
                }
                else
                    raf.skipBytes(NAME_SIZE);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updatePerson(Person p) {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                if (id == p.getId()) {

                    raf.writeBytes(String.format("%-" + NAME_SIZE + "s", p.getName()).substring(0, NAME_SIZE));
                    return;
                }
                else

                  raf.skipBytes(NAME_SIZE);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
