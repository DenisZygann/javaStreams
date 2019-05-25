package de.zygann;

public class Person {


    private String id;
    private String firstName;
    private String lastName;
    private int age;
    private int height;
    private double weight;

    public Person(String id, String firstName, String lastName, int age, int height, double weight) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + "\n" + "First Name: " + getFirstName() +
                "\nLast Name: " + getLastName() + "\nAge: " +  getAge() +
                "\nWeight: " + getWeight() + "\nHeight: " + getHeight();
    }

}
