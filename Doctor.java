public class Doctor extends Person {
    private int doctor_Id;
    private String specialization;

    public Doctor(String hospitalName,String branch, String personName, int age, String contactNumber, int doctorId, String specialization) {
        super(hospitalName, branch, personName, age, contactNumber);
        this.doctor_Id = doctorId;
        this.specialization = specialization;
    }

    public int getDoctorId() { return doctor_Id; }
    public String getSpecialization() { return specialization; }

    @Override
    public void displayDetails() {
        System.out.println(" Doctor Profile ");
        System.out.println("Doctor ID  : " + doctor_Id);
        System.out.println("Name       : Dr. " + getpersonName());
        System.out.println("Specialty  : " + specialization);
        System.out.println("Contact    : " + getContactNumber());
    }
}