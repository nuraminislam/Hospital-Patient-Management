public abstract class Person extends Hospital {
    private String personName;
    private int age;
    private String contactNumber;

    public Person(String name, String branch, String personName, int age, String contactNumber)
    {
        super(name, branch);
        this.personName = personName;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public String getpersonName() { return personName; }
    public void setpersonName(String personName) { this.personName = personName; }
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