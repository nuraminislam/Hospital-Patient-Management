import javax.print.Doc;
import java.io.*;
import java.util.Scanner;

public class HospitalService {
    private Hospital hospital;
    public HospitalService(Hospital hospital) {
        this.hospital = hospital;
    }

    public Hospital getHospital() {this.hospital = hospital;return this.hospital;}

    public void addDoctor(Doctor doctor) throws DuplicateDoctorIdException {
        Doctor searchedDoctor = hospital.searchDoctorById(doctor.getDoctorId());
        if (searchedDoctor != null) {
            throw new DuplicateDoctorIdException("Doctor ID already exists.");
        }
        hospital.addDoctorInfo(doctor);
        System.out.println("Doctor added successfully.");
    }

    // ── Helper: write a line safely, ensuring it starts on a new line ──
    private void appendLine(String fileName, String content) throws IOException {
        File file = new File(fileName);

        // If file exists and doesn't end with newline, add one first
        if (file.exists() && file.length() > 0) {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            raf.seek(file.length() - 1);
            int lastChar = raf.read();
            raf.close();
            if (lastChar != '\n') {
                FileWriter fw = new FileWriter(file, true);
                fw.write(System.lineSeparator());
                fw.close();
            }
        }

        // Now append the new line
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.write(System.lineSeparator());
        bw.close();
    }

    // ── Save doctor to file ──────────────────────────────────────
    // Format: name,age,contact,id,specialization
    public void saveDoctorToFile(Doctor doc, String fileName) {
        try {
            String line = doc.getpersonName() + "," +
                    doc.getAge() + "," +
                    doc.getContactNumber() + "," +
                    doc.getDoctorId() + "," +
                    doc.getSpecialization();
            appendLine(fileName, line);
            System.out.println("Doctor saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving doctor: " + e.getMessage());
        }
    }

    // ── Save patient to file ─────────────────────────────────────
    // Format: name,age,contact,id,bloodGroup,disease,assignedDoctorId
    public void savePatientToFile(Patient patient, String fileName) {
        try {
            int doctorId = (patient.getAssignedDoctor() != null)
                    ? patient.getAssignedDoctor().getDoctorId()
                    : -1;
            String line = patient.getpersonName() + "," +
                    patient.getAge() + "," +
                    patient.getContactNumber() + "," +
                    patient.getPatientId() + "," +
                    patient.getBloodGroup() + "," +
                    patient.getDisease() + "," +
                    doctorId;
            appendLine(fileName, line);
            System.out.println("Patient saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving patient: " + e.getMessage());
        }
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



