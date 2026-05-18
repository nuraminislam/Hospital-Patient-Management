import javax.print.Doc;
import java.io.File;
import java.util.Scanner;

public class HospitalService {
    private Hospital hospital;
    public HospitalService(Hospital hospital) {
        this.hospital = hospital;
    }

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
                    addDoctor(doctor);

                } catch (Exception e) {

                    System.out.println(
                            "Error adding doctor: "
                                    + e.getMessage()
                    );
                }

            }
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Error reading data from " + fileName);
            e.printStackTrace();
        }
    }
}
