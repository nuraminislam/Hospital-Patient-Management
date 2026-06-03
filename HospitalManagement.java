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
//        hospitalService.readDataFromText("patientInfo.txt");
//        hospitalService.readDataFromText("staffInfo.txt");

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

    public void Home () {
        System.out.println("View Person -> 1");
        System.out.println("Search Person -> 2");
        System.out.println("Add new Person -> 3");

        int selector = sc.nextInt();
        if (selector == 1) {
            selectPersonForVeiw();
        } else if (selector == 2) {
            searchPersonForVeiw();
        } else if (selector == 3) {
            System.out.println("Add Person");
        } else if (selector == 0) {
            return;
        }
    }

    public void selectPersonForVeiw() {
        System.out.println("Doctor List -> 1");
        System.out.println("Staff List -> 2");
        System.out.println("Patient List -> 3");
        System.out.println("Go back -> 0");


        int selector = sc.nextInt();
        switch (selector) {
            case 1:
                viewDoctor();
                selectPersonForVeiw();
            case 2:
                System.out.println("Staff list");
            case 3:
                System.out.println("Staff List");
            case 4:
                Home();
        }
    }

    public void searchPersonForVeiw() {
        System.out.println("Search Doctor -> 1");
        System.out.println("Search Staff -> 2");
        System.out.println("Search Patient -> 1");
        System.out.println("Go back -> 0");


        int selector = sc.nextInt();
        switch (selector) {
            case 1:
                searchDoctor();
                searchPersonForVeiw();
            case 0:
                Home();
        }

    }

    public void searchDoctor() {
        System.out.println("Enter doctor id: ");
        int id = sc.nextInt();
        hospital.displayIndividualDcotor(hospital.searchDoctorById(id));
        searchPersonForVeiw();
    }

    public void viewDoctor() {
        hospital.displayDoctor();
    }
}
