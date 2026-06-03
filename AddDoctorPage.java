import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class AddDoctorPage extends JPanel {

    static final Color BG_COLOR   = new Color(245, 245, 252);
    static final Color CARD_COLOR = Color.WHITE;
    static final Color TEXT_DARK  = new Color(40, 30, 80);
    static final Color TEXT_MUTED = new Color(120, 110, 160);
    static final Color ACCENT     = new Color(99, 102, 241);

    private HospitalService hospitalService;
    static final String DOCTOR_FILE = "doctorInfo.txt";

    private JTextField nameField, ageField, contactField, idField, specField;
    private JLabel msgLabel;

    public AddDoctorPage(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("👨‍⚕️  Add New Doctor");
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

        nameField    = addRow(card, gbc, row++, "Full Name",       "e.g. John Smith");
        idField      = addRow(card, gbc, row++, "Doctor ID",       "e.g. 1001");
        ageField     = addRow(card, gbc, row++, "Age",             "e.g. 35");
        contactField = addRow(card, gbc, row++, "Contact Number",  "e.g. 01700000000");
        specField    = addRow(card, gbc, row++, "Specialization",  "e.g. Cardiology");

        msgLabel = new JLabel("", SwingConstants.CENTER);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.insets = new Insets(4, 8, 4, 8);
        card.add(msgLabel, gbc);

        row++;
        JButton submitBtn = new JButton("Add Doctor");
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

        submitBtn.addActionListener(e -> submitDoctor());

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

    private void submitDoctor() {
        String name    = nameField.getText().trim();
        String ageStr  = ageField.getText().trim();
        String contact = contactField.getText().trim();
        String idStr   = idField.getText().trim();
        String spec    = specField.getText().trim();

        if (name.isEmpty() || ageStr.isEmpty() || contact.isEmpty() || idStr.isEmpty() || spec.isEmpty()) {
            showMsg("Please fill in all fields.", false);
            return;
        }

        int age, id;
        try { age = Integer.parseInt(ageStr); } catch (NumberFormatException e) { showMsg("Age must be a number.", false); return; }
        try { id  = Integer.parseInt(idStr);  } catch (NumberFormatException e) { showMsg("Doctor ID must be a number.", false); return; }

        Doctor doc = new Doctor(name, age, contact, id, spec);
        hospitalService.getHospital().addDoctorInfo(doc);
        hospitalService.saveDoctorToFile(doc, DOCTOR_FILE);

        showMsg("✅ Doctor added and saved to file!", true);
        clearFields();
    }

    private void showMsg(String msg, boolean success) {
        msgLabel.setText(msg);
        msgLabel.setForeground(success ? new Color(22, 163, 74) : new Color(200, 50, 50));
    }

    private void clearFields() {
        nameField.setText(""); ageField.setText(""); contactField.setText("");
        idField.setText(""); specField.setText("");
    }
}