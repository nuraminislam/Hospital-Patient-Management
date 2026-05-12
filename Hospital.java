public class Hospital {
    private String hospitalName = "SouthEast Medical Hospital";
    private String hospitalBranch;

    private Doctor [] doctors;
    private Patient [] patients;
    private Staff [] staffs;

    private int doctorCount;
    private int patientCount;
    private int staffCount;

    int maxSize = 50;

    public Hospital(String branch) {
        this.hospitalBranch = branch;

        doctors = new Doctor[maxSize];
        patients = new Patient[maxSize];
        staffs = new Staff[maxSize];
    }

    public void addDoctorInfo(Doctor doc) {
        if(doctorCount < maxSize) {
            doctors[doctorCount] = doc;
            doctorCount++;
        } else {
            System.out.println("Reached max doctor");
        }
    }

    public void addPatientInfo(Patient patient) {
        if(patientCount < maxSize) {
            patients[patientCount] = patient;
            patientCount++;
        } else {
            System.out.println("Reached max patient");
        }
    }

    public void addStaffInfo(Staff staff) {
        if(staffCount < maxSize) {
            staffs[staffCount] = staff;
            staffCount++;
        } else {
            System.out.println("Reached max staff");
        }
    }

    public void displayDoctor() {
        System.out.println("Showing doctors info of " + this.hospitalBranch + " :"+ '\n');
        for(Doctor doc : doctors) {
            if(doc != null) {
                doc.displayDetails();
                System.out.println();
            }
        }
    }

    public void displayPatient() {
        System.out.println("Showing patients info of " + this.hospitalBranch + " :"+ '\n');
        for(Patient patient : patients) {
            if(patient != null) {
                patient.displayDetails();
                System.out.println();
            }
        }
    }

    public void displayStaff() {
        System.out.println("Showing staffs info of " + this.hospitalBranch + " :"+ '\n');
        for(Staff staff : staffs) {
            if(staff != null) {
                staff.displayDetails();
                System.out.println();
            }
        }
    }

}
