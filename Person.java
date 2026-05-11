public abstract class Person {
    private String name;
    private int age;
    private String contactNumber;

    public Person(String name, int age, String contactNumber)
    {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age)
    {
        if (age > 0) {
            this.age = age;
        } else {
            System.out.println("Error: Age must be positive.");
        }
    }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    public abstract void displayDetails();
}