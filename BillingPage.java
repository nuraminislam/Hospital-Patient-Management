import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BillingPage extends JPanel {
    static final Color BG_COLOR   = new Color(245, 245, 252);
    static final Color CARD_COLOR = Color.WHITE;
    static final Color TEXT_DARK  = new Color(40, 30, 80);
    static final Color ACCENT     = new Color(99, 102, 241);

    private HospitalService hospitalService;
    private JTextField searchField;
    private JTextArea receiptArea;

    public BillingPage(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setLayout(new BorderLayout(20, 20));
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(CARD_COLOR);
        topPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 218, 235), 1, true));

        JLabel lbl = new JLabel("🔍 Enter Patient ID for Billing:");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(TEXT_DARK);

        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton generateBtn = new JButton("Generate Bill");
        generateBtn.setBackground(ACCENT);
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        generateBtn.setFocusPainted(false);
        generateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Mac UI Fix: ব্যাকগ্রাউন্ড কালার ফিক্স করার জন্য
        generateBtn.setOpaque(true);
        generateBtn.setBorderPainted(false);

        // Custom Hover Effect: মাউস নিলে কালার একটু ডার্ক হবে
        generateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                generateBtn.setBackground(ACCENT.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                generateBtn.setBackground(ACCENT);
            }
        });

        topPanel.add(lbl);
        topPanel.add(searchField);
        topPanel.add(generateBtn);

        add(topPanel, BorderLayout.NORTH);

        receiptArea = new JTextArea();
        receiptArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        receiptArea.setForeground(new Color(30, 30, 30));
        receiptArea.setEditable(false);
        receiptArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(receiptArea);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 218, 235), 1, true));

        add(scroll, BorderLayout.CENTER);

        generateBtn.addActionListener(e -> generateBill());
    }

    private void generateBill() {
        String idText = searchField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Patient ID.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int pid = Integer.parseInt(idText);
            Patient patient = findPatient(pid);

            if (patient == null) {
                receiptArea.setText("\n\n   ❌ Patient not found with ID: " + pid + "\n   Please check the ID and try again.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("                 HOSPITAL BILLING INVOICE               \n");
            sb.append("\n");
            sb.append(String.format(" Patient ID     : %d\n", patient.getPatientId()));
            sb.append(String.format(" Patient Name   : %s\n", patient.getpersonName()));
            sb.append(String.format(" Age / Blood    : %d / %s\n", patient.getAge(), patient.getBloodGroup()));
            sb.append(String.format(" Disease        : %s\n\n", patient.getDisease()));

            String docName = "Not Assigned";
            int docFee = 0;
            if(patient.getAssignedDoctor() != null) {
                docName = "Dr. " + patient.getAssignedDoctor().getpersonName();
                docFee = 1000; // Standard Doctor Fee
            }
            sb.append(String.format(" Assigned Doctor: %s\n\n", docName));

            sb.append("\n");
            sb.append(" CHARGES BREAKDOWN:\n");
            sb.append("\n");
            sb.append(String.format(" Consultation Fee              : BDT %d\n", docFee));
            sb.append(" Room Charge (Standard)        : BDT 1500\n");
            sb.append(" Medical Tests & Medicines     : BDT 2500\n");
            sb.append(" Hospital Service Tax (10%)    : BDT 400\n");
            sb.append("\n");

            int total = docFee + 1500 + 2500 + 400;
            sb.append(String.format(" TOTAL PAYABLE AMOUNT          : BDT %d\n", total));
            sb.append("\n");
            sb.append("         Thank you for choosing our hospital.           \n");
            sb.append("               Wishing you good health!                 \n");
            sb.append("\n");

            receiptArea.setText(sb.toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Patient ID must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Patient findPatient(int id) {
        Patient[] patients = hospitalService.getHospital().getPatient();
        int count = hospitalService.getHospital().getPatientCount();
        for (int i = 0; i < count; i++) {
            if (patients[i] != null && patients[i].getPatientId() == id) {
                return patients[i];
            }
        }
        return null;
    }
}