public class HospitalManagement {
    private Hospital hospital;
    private HospitalService hospitalService;

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
        System.out.println("Welocme to " + hospital.getHospitalName() + " Management System.....");
    }

}
