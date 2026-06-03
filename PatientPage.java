import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class PatientPage extends JPanel {

    static final Color BG_COLOR   = new Color(245, 245, 252);
    static final Color CARD_COLOR = Color.WHITE;
    static final Color TEXT_DARK  = new Color(40, 30, 80);
    static final Color TEXT_MUTED = new Color(120, 110, 160);
    static final Color ACCENT     = new Color(99, 102, 241);

    private HospitalService hospitalService;
    private JPanel listPanel;
    private JTextField searchField;

    public PatientPage(HospitalService hospitalService) {

        this.hospitalService = hospitalService;

        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_COLOR);

        JLabel title = new JLabel("🧑‍⚕️ Patients");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_DARK);

        searchField = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(new Color(150, 150, 150));

                    g2.setFont(getFont());
                    FontMetrics fm = g2.getFontMetrics();

                    int x = getInsets().left + 5;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

                    g2.drawString(
                            "Search by name...",
                            x,
                            y
                    );

                    g2.dispose();
                }
            }
        };

        topBar.add(title, BorderLayout.WEST);
        topBar.add(searchField, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_COLOR);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        loadPatients("");

        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                loadPatients(searchField.getText().trim().toLowerCase());
            }
        });
    }

    private void loadPatients(String filter) {

        listPanel.removeAll();

        Patient[] patients =
                hospitalService.getHospital().getPatient();

        int count =
                hospitalService.getHospital().getPatientCount();

        for (int i = 0; i < count; i++) {

            Patient patient = patients[i];

            if (patient == null)
                continue;

            String name =
                    patient.getpersonName().toLowerCase();

            if (!filter.isEmpty()
                    && !name.contains(filter))
                continue;

            listPanel.add(buildPatientCard(patient));
            listPanel.add(Box.createVerticalStrut(12));
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel buildPatientCard(Patient patient) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(220,218,235),1,true),
                BorderFactory.createEmptyBorder(
                        16,20,16,20)
        ));

        JLabel name = new JLabel(
                patient.getpersonName());

        name.setFont(
                new Font("Segoe UI",
                        Font.BOLD,16));

        JLabel details = new JLabel(
                "🆔 ID: " + patient.getPatientId()
                        + "    Blood group: "
                        + patient.getBloodGroup()
                        + "    Diesease: "
                        + patient.getDisease()
                        + "    Doctor: "
                        + patient.getAssignedDoctor().getpersonName()
        );

        JLabel contact = new JLabel(
                "📞 " + patient.getContactNumber()
        );

        JPanel info = new JPanel(
                new GridLayout(3,1));

        info.setBackground(CARD_COLOR);

        info.add(name);
        info.add(details);
        info.add(contact);


        card.add(info, BorderLayout.CENTER);

        return card;
    }
}