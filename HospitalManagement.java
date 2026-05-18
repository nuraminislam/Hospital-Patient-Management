import java.util.Scanner;

public class HospitalManagement {
    private Hospital hospital;
    private HospitalService hospitalService;
    Scanner sc = new Scanner(System.in);

    public  HospitalManagement(String hospitalBranchName) {
        hospital = new Hospital(hospitalBranchName);
        hospitalService = new HospitalService(hospital);
    }

    public HospitalService getHospitalService() {
        return hospitalService;
    }
    public void setHospitalService(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    public Hospital getHospital() {
        return hospital;
    }
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void startProgram() {
        hospitalService.readDataFromText("doctorInfo.txt");
        hospitalService.readDataFromText("patientInfo.txt");
        hospitalService.readDataFromText("staffInfo.txt");

        System.out.println("Welocme to " + hospital.getHospitalName() + " Management System.....");
    }

    public boolean logInAdmin() {
        System.out.println("Login as admin....");
        System.out.println("Please enter your username:");
        String username = sc.nextLine();
        System.out.println("Please enter your password:");
        String password = sc.nextLine();

        Login login = new Login(username, password);
        return login.authenticate();
    }


}
