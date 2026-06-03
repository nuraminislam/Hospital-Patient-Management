public class Appointment {
    private int appointmentId;
    private Doctor doctor;
    private Patient patient;
    private String date;       // e.g. "2026-06-10"
    private String time;       // e.g. "10:30 AM"
    private String reason;
    private String status;     // Scheduled, Completed, Cancelled

    public Appointment(int appointmentId, Doctor doctor, Patient patient, String date, String time, String reason) {
        this.appointmentId = appointmentId;
        this.doctor  = doctor;
        this.patient = patient;
        this.date    = date;
        this.time    = time;
        this.reason  = reason;
        this.status  = "Scheduled";
    }

    public int    getAppointmentId() { return appointmentId; }
    public Doctor getDoctor()        { return doctor; }
    public Patient getPatient()      { return patient; }
    public String getDate()          { return date; }
    public String getTime()          { return time; }
    public String getReason()        { return reason; }
    public String getStatus()        { return status; }
    public void   setStatus(String status) { this.status = status; }

    public void displayDetails() {
        System.out.println("----- Appointment -----");
        System.out.println("ID      : " + appointmentId);
        System.out.println("Doctor  : Dr. " + doctor.getpersonName());
        System.out.println("Patient : " + patient.getpersonName());
        System.out.println("Date    : " + date);
        System.out.println("Time    : " + time);
        System.out.println("Reason  : " + reason);
        System.out.println("Status  : " + status);
    }
}