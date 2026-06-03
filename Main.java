import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        Hospital hospital = new Hospital("Dhanmondi");
        HospitalService hospitalService = new HospitalService(hospital);
        hospitalService.readDataFromText("doctorInfo.txt");
        hospitalService.readPatientDataFromText("patientInfo.txt");
        hospitalService.readStaffDataFromText("staffInfo.txt");
        hospitalService.readAppointmentDataFromText("appointmentInfo.txt");
        SwingUtilities.invokeLater(() -> new LoginPage(hospitalService));
    }
}