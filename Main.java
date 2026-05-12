public class Main {
    public static void main(String[] args)
    {
        Doctor doc1 = new Doctor("Sultana Kamal", 38, "01855667788", 501, "Medicine");
        Doctor doc2 = new Doctor("Aminul Islam", 45, "01722334455", 502, "Orthopedics");
        Doctor doc3 = new Doctor( "Farhana Zaman", 42, "01999887766", 503, "Cardiology");
        Doctor doc4 = new Doctor("Farzana Alam", 38, "01777887765", 504, "Cardiology");

        Patient patient1 = new Patient( "Rahim Uddin", 45, "01711223344", 1001, "O+", "Dengue Fever", doc1);
        Patient patient2 = new Patient("Karim Hasan", 32, "01822334455", 1002, "A-", "Fractured Arm", doc2);
        Patient patient3 = new Patient( "Fatema Begum", 60, "01933445566", 1003, "B+", "Heart Palpitations", doc3);
        Patient patient4 = new Patient( "Asma Pramanik", 60, "01955345566", 1004, "AB+", "Infection", doc4);

        Staff s1 = new Staff("Mitu Akter", 29, "01711112222",201, "Nurse", 25000);
        Staff s2 = new Staff("Rahman Hossain", 35, "01822223333", 202, "Receptionist", 20000);
        Staff s3 = new Staff("Sadia Islam", 32, "01933334444",203, "Lab Technician", 30000);
        Staff s4 = new Staff("Karim Uddin", 40, "01744445555",204, "Pharmacist", 28000);
        Staff s5 = new Staff("Nusrat Jahan", 28, "01655556666",205, "Ward Boy", 15000);

        System.out.println(" HOSPITAL DATABASE \n");
        Hospital hospital = new Hospital("Dhanmondi");

        hospital.addDoctorInfo(doc1);
        hospital.addDoctorInfo(doc2);
        hospital.addDoctorInfo(doc3);
        hospital.addDoctorInfo(doc4);

        hospital.addPatientInfo(patient1);
        hospital.addPatientInfo(patient2);
        hospital.addPatientInfo(patient3);
        hospital.addPatientInfo(patient4);

        hospital.addStaffInfo(s1);
        hospital.addStaffInfo(s2);
        hospital.addStaffInfo(s3);
        hospital.addStaffInfo(s4);
        hospital.addStaffInfo(s5);

        hospital.displayDoctor();
        hospital.displayPatient();
        hospital.displayStaff();
    }
}