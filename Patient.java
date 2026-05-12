public class Patient extends Person {
    private int patientId;
    private String bloodGroup;
    private String disease;
    private Doctor assignedDoctor;

    public Patient(String personName, int age, String contactNumber, int patientId, String bloodGroup, String disease, Doctor assignedDoctor)
    {
        super(personName, age, contactNumber);
        this.patientId = patientId;
        this.bloodGroup = bloodGroup;
        this.disease = disease;
        this.assignedDoctor = assignedDoctor;
    }

    public int getPatientId() { return patientId; }
    public String getBloodGroup() { return bloodGroup; }
    public String getDisease() { return disease; }
    public Doctor getAssignedDoctor() { return assignedDoctor; }

    @Override
    public void displayDetails() {
        System.out.println("----- Patient Record -----");
        System.out.println("Patient ID : " + patientId);
        System.out.println("Name       : " + getpersonName());
        System.out.println("Age        : " + getAge());
        System.out.println("Blood Group: " + bloodGroup);
        System.out.println("Disease    : " + disease);

        if (assignedDoctor != null) {
            System.out.println("Assigned Dr: " + assignedDoctor.getpersonName() + " (" + assignedDoctor.getSpecialization() + ")");
        } else {
            System.out.println("Assigned Dr: None");
        }
        System.out.println("Contact    : " + getContactNumber());
    }
}