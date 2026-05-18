import javax.print.Doc;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Doctor doc1 = new Doctor("Sultana Kamal", 38, "01855667788", 501, "Medicine");
        Doctor doc2 = new Doctor("Aminul Islam", 45, "01722334455", 502, "Orthopedics");
        Doctor doc3 = new Doctor( "Farhana Zaman", 42, "01999887766", 503, "Cardiology");
        Doctor doc4 = new Doctor("Farzana Alam", 38, "01777887765", 504, "Cardiology");
        Doctor doc5=new Doctor("Mahfuzur Rahman", 35,"01756987643",505,"Gynecologist");
        Doctor doc6=new Doctor("Shihab Islam",40,"01727546787",506,"Dentist");
        Doctor doc7=new Doctor("Sajid Al Amin",43,"01326548898",507,"Psychiatrist");
        Doctor doc8=new Doctor("Ruhit Al Pantha",46,"01721664598",508,"Surgeon");

        Patient patient1 = new Patient( "Rahim Uddin", 45, "01711223344", 1001, "O+", "Dengue Fever", doc1);
        Patient patient2 = new Patient("Karim Hasan", 32, "01822334455", 1002, "A-", "Fractured Arm", doc2);
        Patient patient3 = new Patient( "Fatema Begum", 60, "01933445566", 1003, "B+", "Heart Palpitations", doc3);
        Patient patient4 = new Patient( "Asma Pramanik", 60, "01955345566", 1004, "AB+", "Infection", doc4);
        Patient patient5 = new Patient( "Mujahidul Islam", 44, "01714523344", 1005, "A+", "Dengue Fever", doc5);
        Patient patient6 = new Patient( "RI Raju", 43, "01778223344", 1006, "A+", "Dengue Fever", doc6);
        Patient patient7 = new Patient( "Abu Bakar", 39, "01767854334", 1007, "AB+", "Dengue Fever", doc7);
        Patient patient8 = new Patient( "Emon Monti", 37, "01342233448", 1008, "O+", "Dengue Fever", doc8);

        Staff s1 = new Staff("Mitu Akter", 29, "01711112222",201, "Nurse", 25000);
        Staff s2 = new Staff("Rahman Hossain", 35, "01822223333", 202, "Receptionist", 20000);
        Staff s3 = new Staff("Sadia Islam", 32, "01933334444",203, "Lab Technician", 30000);
        Staff s4 = new Staff("Karim Uddin", 40, "01744445555",204, "Pharmacist", 28000);
        Staff s5 = new Staff("Meheraj Hossain", 29, "01655556666",205, "Ward Boy", 15000);
        Staff s6 = new Staff("Nusrat Jahan", 28, "01655556666",206, "Ward Boy", 15000);
        Staff s7 = new Staff("Nusrat Jahan", 28, "01655556666",207, "Ward Boy", 15000);
        Staff s8 = new Staff("Nusrat Jahan", 28, "01655556666",208, "Ward Boy", 15000);
        Staff s9 = new Staff("Nusrat Jahan", 28, "01655556666",209, "Ward Boy", 15000);

        System.out.println(" HOSPITAL DATABASE \n");
        Hospital hospital = new Hospital("Dhanmondi");

        HospitalService hospitalService = new HospitalService(hospital);

        hospitalService.readDataFromText("doctorInfo.txt");

//        for (Doctor doctor : doctors) {
//            try {
//                hospitalService.addDoctor(doctor);
//            } catch (DuplicateDoctorIdException e) {
//                System.out.println(e.getMessage());
//            }
//        }

        hospital.addPatientInfo(patient1);
        hospital.addPatientInfo(patient2);
        hospital.addPatientInfo(patient3);
        hospital.addPatientInfo(patient4);
        hospital.addPatientInfo(patient5);
        hospital.addPatientInfo(patient6);
        hospital.addPatientInfo(patient7);
        hospital.addPatientInfo(patient8);

        hospital.addStaffInfo(s1);
        hospital.addStaffInfo(s2);
        hospital.addStaffInfo(s3);
        hospital.addStaffInfo(s4);
        hospital.addStaffInfo(s5);
        hospital.addStaffInfo(s6);
        hospital.addStaffInfo(s7);
        hospital.addStaffInfo(s8);
        hospital.addStaffInfo(s9);


        while (true) {
            System.out.println("Press 0 => terminate the program" + "\n" +
                                "Press 1 => display all doctor" + "\n" +
                                "Press 2 => display all patient" + "\n" +
                                "Press 3 => display all staff" + "\n" +
                                "Press 4 => search doctor by ID" + "\n" +
                                "Press 5 => search patient by ID" + "\n" +
                                "Press 6 => search staff by ID" + "\n");
            int dice = sc.nextInt();
            if(dice ==1) {
                hospital.displayDoctor();
            } else if (dice == 2 ) {
                hospital.displayPatient();
            } else if (dice == 3) {
                hospital.displayStaff();
            } else if(dice == 4) {
                try{
                    System.out.println("Enter the doctor id: ");
                    int id = sc.nextInt();
                    Doctor searchedDoctor = hospital.searchDoctorById(id);
                    searchedDoctor.displayDetails();
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
            else if(dice == 5) {
                System.out.println("Enter the patient id: ");
                int id = sc.nextInt();
                hospital.searchPatientById(id);
            } else if(dice == 6) {
                System.out.println("Enter the staff id: ");
                int id = sc.nextInt();
                hospital.searchStaffById(id);
            } else if (dice == 0) {
                return;
            } else {
                System.out.println("Invalid input!!");
            }
        }
    }
}