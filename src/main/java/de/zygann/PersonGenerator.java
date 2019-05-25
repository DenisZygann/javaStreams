package de.zygann;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class PersonGenerator {

    private final static List<String> firstName = new LinkedList<>();
    private final static List<String> lastName = new LinkedList<>();
    private static List<Person> personList = new LinkedList<>();

    private static long COUNTER = 0;


    static {
        firstName.add("Egon");
        firstName.add("Alfred");
        firstName.add("Peter");
        firstName.add("Bernd");
        firstName.add("Gerhard");
        firstName.add("Linus");
        firstName.add("Michael");
        firstName.add("Christopher");
        firstName.add("Magnus");
        firstName.add("Frederik");
        firstName.add("Otto");
        firstName.add("Patrick");
        firstName.add("Stefan");
        firstName.add("Torge");
        firstName.add("Victor");

        firstName.add("Angelika");
        firstName.add("Britta");
        firstName.add("Cornelia");
        firstName.add("Daniela");
        firstName.add("Elizabeth");
        firstName.add("Finja");
        firstName.add("Claudia");
        firstName.add("Hannah");
        firstName.add("Julia");
        firstName.add("Xenia");
        firstName.add("Olga");
        firstName.add("Penny");
        firstName.add("Stefanie");
        firstName.add("Tanja");
        firstName.add("Veronica");
    }

    static {
        lastName.add("Schulz");
        lastName.add("Meier");
        lastName.add("Landrut");
        lastName.add("Meyer");
        lastName.add("Kirchhoff");
        lastName.add("Stein");
        lastName.add("Gerlach");
        lastName.add("Schulze");
        lastName.add("Adamczyk");
    }

    public static Person createPerson() {
        Random random = new Random();
        return new Person(UUID.randomUUID().toString(), firstName.get(random.nextInt(firstName.size())),
                lastName.get(random.nextInt(lastName.size())), getAge(random), random.nextInt(220),
                (double) random.nextInt(180));
    }

    /**
     * Returns a value which is between 0 and 99.
     * Every fourth value is under 19.
     *
     * @param random {@link Random}
     * @return an int which is between 0 and 99.
     */
    private static int getAge(Random random) {
        int age = random.nextInt(100);
        COUNTER++;

        if (COUNTER % 4 == 0 && age > 18) {
            age = random.nextInt(18);
        } else if (age < 19 && COUNTER % 4 != 0) {
            while (age < 19) {
                age = random.nextInt(100);
            }
        }
        return age;
    }

    public static List<Person> getPersonList(int size){

        if (personList.size() < 1) {
            for (int i = 0; i < size; i++) {
                personList.add(PersonGenerator.createPerson());
            }
        }
        return personList;
    }

    public static List<Person> getNewPersonList(int size){
        COUNTER = 0;
        personList = null;
        personList = new LinkedList<>();
        return getPersonList(size);
    }
}
