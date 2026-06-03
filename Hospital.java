import javax.print.Doc;

public class Hospital {
    private String hospitalName = "SouthEast Medical Hospital";
    private String hospitalBranch;

    private Doctor [] doctors;
    private Patient [] patients;
    private Staff [] staffs;

    private int doctorCount;
    private int patientCount;
    private int staffCount;

    int maxSize = 100;

    public Doctor[] getDoctor() {
        return doctors;
    }
    public int getDoctorCount()
    {
        return this.doctorCount;
    }

    public int getPatientCount()
    {
        return this.patientCount;
    }

    public Patient [] getPatient()
    {
        return this.patients;
    }

    public int getStaffCount()
    {
        return this.staffCount;
    }

    public Hospital(String branch) {
        this.hospitalBranch = branch;

        doctors = new Doctor[maxSize];
        patients = new Patient[maxSize];
        staffs = new Staff[maxSize];
    }

    public String getHospitalName() {
        return this.hospitalName;
    }

    public void addDoctorInfo(Doctor doc) {
        if(doctorCount < maxSize) {
            doctors[doctorCount] = doc;
            doctorCount++;
        } else {
            System.out.println("Reached max doctor");
        }
    }

    public void addPatientInfo(Patient patient) throws InvalidAgeException {
        // Business Rule: Checking if the age is valid
        if(patient.getAge() <= 0 || patient.getAge() > 120) {
            throw new InvalidAgeException("Validation Error: Invalid age (" + patient.getAge() + ") for patient " + patient.getpersonName());
        }

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
        System.out.println("Showing doctors info of " + this.hospitalName + " of " +this.hospitalBranch + " branch :"+ '\n');
        for(Doctor doc : doctors) {
            if(doc != null) {
                doc.displayDetails();
                System.out.println();
            }
        }
    }

    public void displayIndividualDcotor(Doctor doc) {
        doc.displayDetails();
        System.out.println();
    }

    public void displayPatient() {
        System.out.println("Showing patients info of "+ this.hospitalName + " of " +this.hospitalBranch + " branch :"+ '\n');
        for(Patient patient : patients) {
            if(patient != null) {
                patient.displayDetails();
                System.out.println();
            }
        }
    }

    public void displayStaff() {
        System.out.println("Showing staffs info of " + this.hospitalName + " of " +this.hospitalBranch + " branch :"+ '\n');
        for(Staff staff : staffs) {
            if(staff != null) {
                staff.displayDetails();
                System.out.println();
            }
        }
    }

    public Doctor searchDoctorById(int id) {

        for (int i = 0; i < doctorCount; i++) {

            if (doctors[i].getDoctorId() == id) {
                return  doctors[i];
            }
        }
        return null;
    }

    public Patient searchPatientById(int id) {

        for (int i = 0; i < patientCount; i++) {
            if (patients[i].getPatientId() == id) {
                return patients[i];
            }
        }

        System.out.println("Patient not found.");
        return null;
    }

    public void searchStaffById(int id) {

        for (int i = 0; i < staffCount; i++) {

            if (staffs[i].getStaffId() == id) {

                System.out.println("Staff Found:\n");
                staffs[i].displayDetails();
                return;
            }
        }

        System.out.println("Staff not found.");
    }

    public int availableDoctors() {
        int available = 0;

        for (Doctor doc : doctors) {

            if (doc != null) {

                if (doc.getPatientCount() < 5) {
                    available++;
                }
            }
        }

        return available;
    }
}
