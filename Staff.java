public class Staff extends Person {

    private int staffId;
    private String role;
    private double salary;

    public Staff(String personName, int age, String contactNumber, int staffId, String role, double salary) {

        super(personName, age, contactNumber);
        this.staffId = staffId;
        this.role = role;
        this.salary = salary;
    }


    public int getStaffId() { return staffId; }

    public String getRole() {
        return role;
    }

    public double getSalary() {
        return salary;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSalary(double salary) {
        if (salary > 0) {
            this.salary = salary;
        } else {
            System.out.println("Invalid salary");
        }
    }

    @Override
    public void displayDetails() {
        System.out.println("----- Staff Profile -----");
        System.out.println("Staff ID   : " + staffId);
        System.out.println("Name       : " + getpersonName());
        System.out.println("Age        : " + getAge());
        System.out.println("Role       : " + role);
        System.out.println("Salary     : " + salary);
        System.out.println("Contact    : " + getContactNumber());
    }
}