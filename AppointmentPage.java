import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class AppointmentPage extends JPanel {

    static final Color BG_COLOR   = new Color(245, 245, 252);
    static final Color CARD_COLOR = Color.WHITE;
    static final Color TEXT_DARK  = new Color(40, 30, 80);
    static final Color TEXT_MUTED = new Color(120, 110, 160);
    static final Color ACCENT     = new Color(99, 102, 241);

    static final String APPOINTMENT_FILE = "appointmentInfo.txt";

    private HospitalService hospitalService;
    private JComboBox<String> doctorCombo, patientCombo;
    private JTextField dateField, timeField, reasonField;
    private JLabel msgLabel;
    private DefaultTableModel tableModel;

    public AppointmentPage(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setLayout(new BorderLayout(0, 20));
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("📅  Appointments");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.add(buildForm(), BorderLayout.NORTH);
        centerPanel.add(buildTable(), BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(centerPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_COLOR);
        add(scroll, BorderLayout.CENTER);

        // Load existing appointments from hospitalService into table
        loadExistingAppointments();
    }

    private JPanel buildForm() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 218, 235), 1, true),
                BorderFactory.createEmptyBorder(24, 32, 24, 32)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        int row = 0;

        // Doctor dropdown
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        card.add(makeLabel("Doctor"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        doctorCombo = new JComboBox<>();
        styleCombo(doctorCombo);
        loadDoctors();
        card.add(doctorCombo, gbc);
        row++;

        // Patient dropdown
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        card.add(makeLabel("Patient"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        patientCombo = new JComboBox<>();
        styleCombo(patientCombo);
        loadPatients();
        card.add(patientCombo, gbc);
        row++;

        // Date
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        card.add(makeLabel("Date"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        dateField = makeTextField("e.g. 2026-06-15");
        card.add(dateField, gbc);
        row++;

        // Time
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        card.add(makeLabel("Time"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        timeField = makeTextField("e.g. 10:30 AM");
        card.add(timeField, gbc);
        row++;

        // Reason
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        card.add(makeLabel("Reason"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        reasonField = makeTextField("e.g. Routine checkup");
        card.add(reasonField, gbc);
        row++;

        // Message
        msgLabel = new JLabel("", SwingConstants.CENTER);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 8, 4, 8);
        card.add(msgLabel, gbc);
        row++;

        // Submit
        JButton submitBtn = new JButton("Book Appointment");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitBtn.setBackground(ACCENT);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setBorderPainted(false);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(200, 44));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 8, 8, 8);
        card.add(submitBtn, gbc);
        submitBtn.addActionListener(e -> submitAppointment());

        return card;
    }

    private JPanel buildTable() {
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_COLOR);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 218, 235), 1, true),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel tableTitle = new JLabel("All Appointments");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tableTitle.setForeground(TEXT_DARK);
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        tableCard.add(tableTitle, BorderLayout.NORTH);

        String[] cols = {"ID", "Doctor", "Patient", "Date", "Time", "Reason", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(240, 238, 252));
        table.getTableHeader().setForeground(TEXT_DARK);
        table.setGridColor(new Color(230, 228, 245));
        table.setShowGrid(true);

        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);
        return tableCard;
    }

    private void loadExistingAppointments() {
        for (Appointment appt : hospitalService.getAppointments()) {
            tableModel.addRow(new Object[]{
                    appt.getAppointmentId(),
                    "Dr. " + appt.getDoctor().getpersonName(),
                    appt.getPatient().getpersonName(),
                    appt.getDate(),
                    appt.getTime(),
                    appt.getReason(),
                    appt.getStatus()
            });
        }
    }

    private void loadDoctors() {
        doctorCombo.removeAllItems();
        doctorCombo.addItem("-- Select Doctor --");
        Doctor[] doctors = hospitalService.getHospital().getDoctor();
        int count = hospitalService.getHospital().getDoctorCount();
        for (int i = 0; i < count; i++) {
            if (doctors[i] != null)
                doctorCombo.addItem("ID: " + doctors[i].getDoctorId() + " — Dr. " + doctors[i].getpersonName());
        }
    }

    private void loadPatients() {
        patientCombo.removeAllItems();
        patientCombo.addItem("-- Select Patient --");
        Patient[] patients = hospitalService.getHospital().getPatient();
        int count = hospitalService.getHospital().getPatientCount();
        for (int i = 0; i < count; i++) {
            if (patients[i] != null)
                patientCombo.addItem("ID: " + patients[i].getPatientId() + " — " + patients[i].getpersonName());
        }
    }

    private void submitAppointment() {
        int docIdx = doctorCombo.getSelectedIndex();
        int patIdx = patientCombo.getSelectedIndex();
        String date   = dateField.getText().trim();
        String time   = timeField.getText().trim();
        String reason = reasonField.getText().trim();

        if (docIdx == 0 || patIdx == 0 || date.isEmpty() || time.isEmpty() || reason.isEmpty()) {
            showMsg("Please fill in all fields.", false);
            return;
        }

        Doctor  doc = hospitalService.getHospital().getDoctor()[docIdx - 1];
        Patient pat = hospitalService.getHospital().getPatient()[patIdx - 1];

        int id = hospitalService.getNextAppointmentId();
        Appointment appt = new Appointment(id, doc, pat, date, time, reason);

        // Save to hospitalService list
        hospitalService.addAppointment(appt);
        hospitalService.incrementAppointmentId();

        // Save to file
        hospitalService.saveAppointmentToFile(appt, APPOINTMENT_FILE);

        // Add to table
        tableModel.addRow(new Object[]{
                appt.getAppointmentId(),
                "Dr. " + doc.getpersonName(),
                pat.getpersonName(),
                date, time, reason,
                appt.getStatus()
        });

        showMsg("✅ Appointment booked and saved!", true);
        dateField.setText(""); timeField.setText(""); reasonField.setText("");
        doctorCombo.setSelectedIndex(0); patientCombo.setSelectedIndex(0);
    }

    private void showMsg(String msg, boolean success) {
        msgLabel.setText(msg);
        msgLabel.setForeground(success ? new Color(22, 163, 74) : new Color(200, 50, 50));
    }

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(TEXT_DARK);
        return lbl;
    }

    private JTextField makeTextField(String tooltip) {
        JTextField f = new JTextField();
        f.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        f.setToolTipText(tooltip);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 198, 230), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return f;
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBorder(BorderFactory.createLineBorder(new Color(200, 198, 230), 1, true));
    }
}