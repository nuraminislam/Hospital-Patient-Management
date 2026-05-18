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
}
