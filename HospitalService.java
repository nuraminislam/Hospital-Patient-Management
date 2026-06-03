import javax.print.Doc;
import java.io.File;
import java.util.Scanner;

public class HospitalService {
    private Hospital hospital;
    public HospitalService(Hospital hospital) {
        this.hospital = hospital;
    }

    public Hospital getHospital() {this.hospital = hospital;return this.hospital;}

    public void addDoctor(Doctor doctor)
            throws DuplicateDoctorIdException {

        Doctor searchedDoctor = hospital.searchDoctorById(doctor.getDoctorId());

        if (searchedDoctor != null) {

            throw new DuplicateDoctorIdException(
                    "Doctor ID already exists."
            );
        }
        hospital.addDoctorInfo(doctor);
        System.out.println("Doctor added successfully.");
    }

    public void readDataFromText(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                String [] data = line.split(",");

                String name = data[0];
                int age = Integer.parseInt(data[1]);
                String number = data[2];
                int id = Integer.parseInt(data[3]);
                String specialization = data[4];
                try {
                    Doctor doctor = new Doctor(name, age, number, id, specialization);
                    hospital.addDoctorInfo(doctor);

                } catch (Exception e) {

                    System.out.println(
                            "Error adding doctor: "
                                    + e.getMessage()
                    );
                }

            }
            System.out.println("successfully read data from " + fileName);
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Error reading data from " + fileName);
            //e.printStackTrace();
        }
    }
    public void readPatientDataFromText(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                String [] data = line.split(",");

                String name = data[0];
                int age = Integer.parseInt(data[1]);
                String number = data[2];
                int id = Integer.parseInt(data[3]);
                String bloodGroup = data[4];
                String disease=data[5];
                int assignedDoctorId = Integer.parseInt(data[6]);
                Doctor assignedDoctor = hospital.searchDoctorById(assignedDoctorId);
                try {
                    Doctor doc = hospital.searchDoctorById(assignedDoctorId);
                    Patient patient = new Patient(name, age, number, id, bloodGroup, disease, doc);
                    hospital.addPatientInfo(patient);
                    assignedDoctor.appointment(patient);

                } catch (Exception e) {

                    System.out.println(
                            "Error adding patient info: "
                                    + e.getMessage()
                    );
                }

            }
            System.out.println("successfully read data from " + fileName);
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Error reading data from " + fileName);
            //e.printStackTrace();
        }
    }

    public void readStaffDataFromText(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();

                String [] data = line.split(",");

                String name = data[0];
                int age = Integer.parseInt(data[1]);
                String number = data[2];
                int staffid = Integer.parseInt(data[3]);
                String role= data[4];
                double Salary = Double.parseDouble(data[5]);
                try {
                    Staff staff = new Staff(name, age, number, staffid, role, Salary);
                    hospital.addStaffInfo(staff);

                } catch (Exception e) {

                    System.out.println(
                            "Error adding staff: "
                                    + e.getMessage()
                    );
                }

            }
            System.out.println("successfully read data from " + fileName);
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Error reading data from " + fileName);
            //e.printStackTrace();
        }
    }


}



