public class Doctor extends Person {
    private int doctor_Id;
    private String specialization;
    private Patient [] patients;
    private int patientCount;

    public Doctor(String personName, int age, String contactNumber, int doctorId, String specialization) {
        super(personName, age, contactNumber);
        this.doctor_Id = doctorId;
        this.specialization = specialization;
        patients = new Patient [5];
    }

    public Patient [] getPatients() { return patients; }
    public int getPatientCount() { return patientCount; }
    public int getDoctorId() { return doctor_Id; }
    public String getSpecialization() { return specialization; }

    @Override
    public void displayDetails() {
        System.out.println(" Doctor Profile ");
        System.out.println("Doctor ID  : " + doctor_Id);
        System.out.println("Name       : Dr. " + getpersonName());
        System.out.println("Specialty  : " + specialization);
        System.out.println("Contact    : " + getContactNumber());
        System.out.println("Patient Count: " + patientCount);
        for (Patient patient : patients) {
            if (patient != null) {
                patient.displayDetails();
            }
        }
    }

    public void appointment(Patient patient) {
        if(patientCount < 6) {
            patients[patientCount] = patient;
            patientCount++;
        } else {
            System.out.println("Reached max patients");
        }
    }
}