import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class DoctorPage extends JPanel {

    static final Color BG_COLOR   = new Color(245, 245, 252);
    static final Color CARD_COLOR = Color.WHITE;
    static final Color TEXT_DARK  = new Color(40, 30, 80);
    static final Color TEXT_MUTED = new Color(120, 110, 160);
    static final Color ACCENT     = new Color(99, 102, 241);

    private HospitalService hospitalService;
    private JPanel listPanel;
    private JTextField searchField;

    public DoctorPage(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        // ── Top bar: title + search ──────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout(16, 0));
        topBar.setBackground(BG_COLOR);
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        JLabel title = new JLabel("👨‍⚕️  Doctors");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_DARK);
        topBar.add(title, BorderLayout.WEST);

        // Search box
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
                            "Search by name or specialization...",
                            x,
                            y
                    );

                    g2.dispose();
                }
            }
        };
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 198, 230), 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        searchField.putClientProperty("JTextField.placeholderText", "Search by name or specialization...");

        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setBackground(BG_COLOR);
        searchWrap.setMaximumSize(new Dimension(260, 36));
        searchWrap.add(searchField);

        topBar.add(searchWrap, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // ── Doctor list panel (scrollable) ───────────────────────
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_COLOR);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG_COLOR);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Load doctors
        loadDoctors("");

        // Search listener
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                loadDoctors(searchField.getText().trim().toLowerCase());
            }
        });
    }

    private void loadDoctors(String filter) {
        listPanel.removeAll();

        Doctor[] doctors = hospitalService.getHospital().getDoctor();
        int count = hospitalService.getHospital().getDoctorCount();
        boolean any = false;

        for (int i = 0; i < count; i++) {
            Doctor doc = doctors[i];
            if (doc == null) continue;

            String name = doc.getpersonName().toLowerCase();
            String spec = doc.getSpecialization().toLowerCase();

            if (!filter.isEmpty() && !name.contains(filter) && !spec.contains(filter)) continue;

            listPanel.add(buildDoctorCard(doc));
            listPanel.add(Box.createVerticalStrut(12));
            any = true;
        }

        if (!any) {
            JLabel none = new JLabel("No doctors found.", SwingConstants.CENTER);
            none.setFont(new Font("Segoe UI", Font.ITALIC, 15));
            none.setForeground(TEXT_MUTED);
            none.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(Box.createVerticalStrut(40));
            listPanel.add(none);
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel buildDoctorCard(Doctor doc) {
        JPanel card = new JPanel(new BorderLayout(16, 0));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 218, 235), 1, true),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Avatar circle with initials
        JPanel avatar = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                String initials = getInitials(doc.getpersonName());
                int x = (getWidth()  - fm.stringWidth(initials)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(initials, x, y);
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(56, 56));
        avatar.setOpaque(false);

        JPanel avatarWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        avatarWrap.setBackground(CARD_COLOR);
        avatarWrap.add(avatar);
        card.add(avatarWrap, BorderLayout.WEST);

        // Info
        JPanel info = new JPanel(new GridLayout(3, 1, 0, 2));
        info.setBackground(CARD_COLOR);

        JLabel nameLabel = new JLabel("Dr. " + doc.getpersonName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(TEXT_DARK);

        JLabel specLabel = new JLabel("🩺  " + doc.getSpecialization());
        specLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        specLabel.setForeground(TEXT_MUTED);

        JLabel contactLabel = new JLabel("📞  " + doc.getContactNumber()
                + "     🆔  ID: " + doc.getDoctorId());
        contactLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        contactLabel.setForeground(TEXT_MUTED);

        info.add(nameLabel);
        info.add(specLabel);
        info.add(contactLabel);
        card.add(info, BorderLayout.CENTER);

        // Right side: patient count + status badge
        JPanel right = new JPanel(new GridLayout(2, 1, 0, 6));
        right.setBackground(CARD_COLOR);

        int pc = doc.getPatientCount();
        JLabel patLabel = new JLabel("Patients: " + pc + " / 5", SwingConstants.CENTER);
        patLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        patLabel.setForeground(TEXT_MUTED);

        boolean available = pc < 5;
        JLabel badge = new JLabel(available ? "✅ Available" : "🔴 Full", SwingConstants.CENTER);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 13));
        badge.setForeground(available ? new Color(22, 163, 74) : new Color(220, 38, 38));

        // View patients button
        JButton viewBtn = new JButton("View Patients");
        viewBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        viewBtn.setForeground(ACCENT);
        viewBtn.setBackground(CARD_COLOR);
        viewBtn.setBorder(BorderFactory.createLineBorder(ACCENT, 1, true));
        viewBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewBtn.setFocusPainted(false);
        viewBtn.addActionListener(e -> showPatientPopup(doc));

        right.add(patLabel);
        right.add(badge);

        JPanel rightWrap = new JPanel(new BorderLayout(0, 6));
        rightWrap.setBackground(CARD_COLOR);
        rightWrap.add(right, BorderLayout.CENTER);
        rightWrap.add(viewBtn, BorderLayout.SOUTH);

        card.add(rightWrap, BorderLayout.EAST);

        return card;
    }

    private void showPatientPopup(Doctor doc) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Patients of Dr. " + doc.getpersonName(), true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel header = new JLabel("  Patients attending Dr. " + doc.getpersonName(), SwingConstants.LEFT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setForeground(TEXT_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(14, 14, 10, 14));
        dialog.add(header, BorderLayout.NORTH);

        Patient[] patients = doc.getPatients();
        int pc = doc.getPatientCount();

        if (pc == 0) {
            JLabel none = new JLabel("No patients assigned.", SwingConstants.CENTER);
            none.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            none.setForeground(TEXT_MUTED);
            dialog.add(none, BorderLayout.CENTER);
        } else {
            String[] cols = {"Patient ID", "Name", "Age", "Contact"};
            Object[][] data = new Object[pc][4];
            for (int i = 0; i < pc; i++) {
                Patient p = patients[i];
                if (p != null) {
                    data[i][0] = p.getPatientId();
                    data[i][1] = p.getpersonName();
                    data[i][2] = p.getAge();
                    data[i][3] = p.getContactNumber();
                }
            }
            JTable table = new JTable(data, cols) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            table.setRowHeight(32);
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            table.getTableHeader().setBackground(new Color(240, 238, 252));
            table.setGridColor(new Color(230, 228, 245));
            dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        }

        JButton close = new JButton("Close");
        close.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        close.addActionListener(e -> dialog.dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 10, 0));
        btnPanel.add(close);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private String getInitials(String name) {
        String[] parts = name.trim().split(" ");
        if (parts.length >= 2)
            return ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
        return name.substring(0, Math.min(2, name.length())).toUpperCase();
    }
}