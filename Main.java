public class Main {
    public static void main(String[] args)
    {
        Doctor doc1 = new Doctor("Sultana Kamal", 38, "01855667788", 501, "Medicine");
        Doctor doc2 = new Doctor("Aminul Islam", 45, "01722334455", 502, "Orthopedics");
        Doctor doc3 = new Doctor("Farhana Zaman", 42, "01999887766", 503, "Cardiology");
        Doctor doc4 = new Doctor("Farzana Alam", 38, "01777887765", 504, "Cardiology");

        Person[] hospitalRecords = new Person[50];

        hospitalRecords[0] = new Patient("Rahim Uddin", 45, "01711223344", 1001, "O+", "Dengue Fever", doc1);
        hospitalRecords[1] = new Patient("Karim Hasan", 32, "01822334455", 1002, "A-", "Fractured Arm", doc2);
        hospitalRecords[2] = new Patient("Fatema Begum", 60, "01933445566", 1003, "B+", "Heart Palpitations", doc3);
        hospitalRecords[3] = new Patient("Asma Pramanik", 60, "01955345566", 1004, "AB+", "Infection", doc4);

        System.out.println(" HOSPITAL DATABASE \n");

        for (int i = 0; i < hospitalRecords.length; i++) {
            if (hospitalRecords[i] != null) {
                hospitalRecords[i].displayDetails();
                System.out.println();
            }
        }
    }
}