import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class AddPatientPage extends JPanel {

    static final Color BG_COLOR   = new Color(245, 245, 252);
    static final Color CARD_COLOR = Color.WHITE;
    static final Color TEXT_DARK  = new Color(40, 30, 80);
    static final Color TEXT_MUTED = new Color(120, 110, 160);
    static final Color ACCENT     = new Color(99, 102, 241);

    private HospitalService hospitalService;
    static final String PATIENT_FILE = "C:\\Users\\pc\\Downloads\\Hospital-Patient-Management\\patientInfo.txt";

    private JTextField nameField, ageField, contactField, idField, bloodField, diseaseField;
    private JComboBox<String> doctorCombo;
    private JLabel msgLabel;

    public AddPatientPage(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("🧑‍⚕️  Add New Patient");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 218, 235), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1;

        int row = 0;
        nameField    = addRow(card, gbc, row++, "Full Name",      "e.g. Alice Johnson");
        idField      = addRow(card, gbc, row++, "Patient ID",     "e.g. 2001");
        ageField     = addRow(card, gbc, row++, "Age",            "e.g. 28");
        contactField = addRow(card, gbc, row++, "Contact Number", "e.g. 01800000000");
        bloodField   = addRow(card, gbc, row++, "Blood Group",    "e.g. A+, B-, O+");
        diseaseField = addRow(card, gbc, row++, "Disease",        "e.g. Hypertension");

        // Doctor dropdown
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.3;
        JLabel docLbl = new JLabel("Assign Doctor");
        docLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        docLbl.setForeground(TEXT_DARK);
        card.add(docLbl, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        doctorCombo = new JComboBox<>();
        doctorCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        doctorCombo.setBorder(BorderFactory.createLineBorder(new Color(200, 198, 230), 1, true));
        loadDoctorsIntoCombo();
        card.add(doctorCombo, gbc);
        row++;

        // Message
        msgLabel = new JLabel("", SwingConstants.CENTER);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 8, 4, 8);
        card.add(msgLabel, gbc);
        row++;

        // Submit button
        JButton submitBtn = new JButton("Add Patient");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        submitBtn.setBackground(ACCENT);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setBorderPainted(false);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(200, 44));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 8, 8, 8);
        card.add(submitBtn, gbc);

        submitBtn.addActionListener(e -> submitPatient());

        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_COLOR);
        add(scroll, BorderLayout.CENTER);
    }

    private JTextField addRow(JPanel card, GridBagConstraints gbc, int row, String labelText, String placeholder) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0.3;
        gbc.insets = new Insets(8, 8, 8, 8);
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(TEXT_DARK);
        card.add(lbl, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setToolTipText(placeholder);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 198, 230), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        card.add(field, gbc);
        return field;
    }

    private void loadDoctorsIntoCombo() {
        doctorCombo.removeAllItems();
        doctorCombo.addItem("-- Select Doctor --");
        Doctor[] doctors = hospitalService.getHospital().getDoctor();
        int count = hospitalService.getHospital().getDoctorCount();
        for (int i = 0; i < count; i++) {
            if (doctors[i] != null)
                doctorCombo.addItem("ID: " + doctors[i].getDoctorId() + " — Dr. " + doctors[i].getpersonName());
        }
    }

    private void submitPatient() {
        String name    = nameField.getText().trim();
        String ageStr  = ageField.getText().trim();
        String contact = contactField.getText().trim();
        String idStr   = idField.getText().trim();
        String blood   = bloodField.getText().trim();
        String disease = diseaseField.getText().trim();
        int selectedDoc = doctorCombo.getSelectedIndex();

        if (name.isEmpty() || ageStr.isEmpty() || contact.isEmpty() || idStr.isEmpty()
                || blood.isEmpty() || disease.isEmpty()) {
            showMsg("Please fill in all fields.", false);
            return;
        }

        int age, id;
        try { age = Integer.parseInt(ageStr); } catch (NumberFormatException e) { showMsg("Age must be a number.", false); return; }
        try { id  = Integer.parseInt(idStr);  } catch (NumberFormatException e) { showMsg("Patient ID must be a number.", false); return; }

        Doctor assignedDoc = null;
        if (selectedDoc > 0) {
            assignedDoc = hospitalService.getHospital().getDoctor()[selectedDoc - 1];
        }

        Patient patient = new Patient(name, age, contact, id, blood, disease, assignedDoc);
        hospitalService.getHospital().addPatientInfo(patient);
        hospitalService.savePatientToFile(patient, PATIENT_FILE);

        if (assignedDoc != null) {
            assignedDoc.appointment(patient);
        }

        showMsg("✅ Patient added and saved to file!", true);
        clearFields();
        loadDoctorsIntoCombo();
    }

    private void showMsg(String msg, boolean success) {
        msgLabel.setText(msg);
        msgLabel.setForeground(success ? new Color(22, 163, 74) : new Color(200, 50, 50));
    }

    private void clearFields() {
        nameField.setText(""); ageField.setText(""); contactField.setText("");
        idField.setText(""); bloodField.setText(""); diseaseField.setText("");
        doctorCombo.setSelectedIndex(0);
    }
}