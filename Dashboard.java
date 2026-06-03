import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Dashboard extends JFrame {

    HospitalService hospitalService;
    static final Color SIDEBAR_COLOR  = new Color(45, 35, 100);
    static final Color ACCENT         = new Color(99, 102, 241);
    static final Color BG_COLOR       = new Color(245, 245, 252);
    static final Color CARD_COLOR     = Color.WHITE;
    static final Color TEXT_DARK      = new Color(40, 30, 80);
    static final Color TEXT_MUTED     = new Color(120, 110, 160);

    private JPanel contentArea;

    public Dashboard(String username, BufferedImage logoImage, HospitalService hospitalService) {
        setTitle("Hospital Management — Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        this.hospitalService = hospitalService;
        if (logoImage != null) setIconImage(logoImage);

        // ── Sidebar ──────────────────────────────────────────────
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Logo + app name
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        logoPanel.setBackground(SIDEBAR_COLOR);
        logoPanel.setMaximumSize(new Dimension(220, 100));

        if (logoImage != null) {
            Image scaled = logoImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel logoIcon = new JLabel(new ImageIcon(scaled));
            logoPanel.add(logoIcon);
        }
        JLabel appName = new JLabel("<html><center>SouthEast Medical<br>College</center></html>");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        appName.setForeground(Color.WHITE);
        logoPanel.add(appName);
        sidebar.add(logoPanel);

        // Divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 40));
        sep.setMaximumSize(new Dimension(180, 1));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(10));

        // Nav items
        String[][] navItems = {
                {"Dashboard", "🏠"},
                {"Patients",  "🧑‍⚕️"},
                {"Doctors",   "👨‍⚕️"},
                {"Appointments", "📅"},
                {"Reports",   "📊"},
                {"Settings",  "⚙️"}
        };

        JPanel[] navBtns = new JPanel[navItems.length];
        for (int i = 0; i < navItems.length; i++) {
            final String label = navItems[i][0];
            final String icon  = navItems[i][1];
            final int idx = i;

            JPanel navItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
            navItem.setBackground(i == 0 ? ACCENT : SIDEBAR_COLOR);
            navItem.setMaximumSize(new Dimension(220, 48));
            navItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel navLabel = new JLabel(icon + "  " + label);
            navLabel.setForeground(Color.WHITE);
            navLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            navItem.add(navLabel);
            navBtns[i] = navItem;

            navItem.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    for (JPanel btn : navBtns) btn.setBackground(SIDEBAR_COLOR);
                    navItem.setBackground(ACCENT);
                    showPage(label);
                }
                public void mouseEntered(MouseEvent e) {
                    if (navItem.getBackground() != ACCENT)
                        navItem.setBackground(new Color(70, 55, 130));
                }
                public void mouseExited(MouseEvent e) {
                    if (navItem.getBackground() != ACCENT)
                        navItem.setBackground(SIDEBAR_COLOR);
                }
            });

            sidebar.add(navItem);
        }

        sidebar.add(Box.createVerticalGlue());

        // Logout button
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        logoutPanel.setBackground(SIDEBAR_COLOR);
        logoutPanel.setMaximumSize(new Dimension(220, 48));
        logoutPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JLabel logoutLabel = new JLabel("🚪  Logout");
        logoutLabel.setForeground(new Color(255, 100, 100));
        logoutLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutPanel.add(logoutLabel);
        logoutPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginPage(hospitalService);
            }
            public void mouseEntered(MouseEvent e) { logoutPanel.setBackground(new Color(70, 55, 130)); }
            public void mouseExited(MouseEvent e)  { logoutPanel.setBackground(SIDEBAR_COLOR); }
        });
        sidebar.add(logoutPanel);
        sidebar.add(Box.createVerticalStrut(20));

        // ── Top Bar ───────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(CARD_COLOR);
        topBar.setPreferredSize(new Dimension(0, 64));
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 218, 235)));

        JLabel pageTitle = new JLabel("  Dashboard");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pageTitle.setForeground(TEXT_DARK);
        topBar.add(pageTitle, BorderLayout.WEST);

        JLabel userInfo = new JLabel("👤  " + username + "   ");
        userInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userInfo.setForeground(TEXT_MUTED);
        topBar.add(userInfo, BorderLayout.EAST);

        // ── Content Area ──────────────────────────────────────────
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(BG_COLOR);

        showPage("Dashboard");

        // ── Assemble ──────────────────────────────────────────────
        add(sidebar, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);
        add(contentArea, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showPage(String page) {
        contentArea.removeAll();
        switch (page) {
            case "Dashboard":   contentArea.add(buildDashboardHome()); break;
            case "Doctors":     contentArea.add(new DoctorPage(hospitalService)); break;
            case "Appointments":contentArea.add(buildSimplePage("📅 Appointments", "View and schedule appointments here.")); break;
            case "Reports":     contentArea.add(buildSimplePage("📊 Reports", "View hospital reports and analytics here.")); break;
            case "Settings":    contentArea.add(buildSimplePage("⚙️ Settings", "Configure system settings here.")); break;
            case "Patients":    contentArea.add(new PatientPage(hospitalService)); break;
        }
        contentArea.revalidate();
        contentArea.repaint();
    }

    private JPanel buildDashboardHome() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Stat cards row
        JPanel cards = new JPanel(new GridLayout(1, 4, 20, 0));
        cards.setBackground(BG_COLOR);
        cards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        String[][] stats = {
                {"Total Patients", String.valueOf(hospitalService.getHospital().getPatientCount()), "🧑‍⚕️", "#6366f1"},
                {"Doctors",        String.valueOf(hospitalService.getHospital().getDoctorCount()),    "👨‍⚕️", "#10b981"},
                {"Appointments",   "320",   "📅",   "#f59e0b"},
                {"Available Doctors",String.valueOf(hospitalService.getHospital().availableDoctors()),   "🛏️",  "#ef4444"}
        };

        for (String[] s : stats) cards.add(buildStatCard(s[0], s[1], s[2], Color.decode(s[3])));

        panel.add(cards);
        panel.add(Box.createVerticalStrut(30));

        // Recent activity table placeholder
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_COLOR);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 218, 235), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel tableTitle = new JLabel("Recent Admissions");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(TEXT_DARK);
        tableCard.add(tableTitle, BorderLayout.NORTH);

        String[] cols = {"Patient Name", "Doctor", "Date", "Status"};
        Object[][] data = {
                {"Alice Johnson",  "Dr. Smith",   "2026-06-01", "Admitted"},
                {"Bob Williams",   "Dr. Khan",    "2026-06-02", "Discharged"},
                {"Carol Martinez", "Dr. Patel",   "2026-06-02", "Under Treatment"},
                {"David Lee",      "Dr. Ahmed",   "2026-06-03", "Admitted"},
                {"Emma Brown",     "Dr. Nguyen",  "2026-06-03", "Discharged"},
        };

        JTable table = new JTable(data, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(36);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(240, 238, 252));
        table.getTableHeader().setForeground(TEXT_DARK);
        table.setGridColor(new Color(230, 228, 245));
        table.setShowGrid(true);

        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(tableCard);

        return panel;
    }

    private JPanel buildStatCard(String label, String value, String icon, Color accent) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 218, 235), 1, true),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));

        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.WEST; g.gridx = 0; g.gridy = 0;

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        card.add(iconLabel, g);

        g.gridy = 1;
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(accent);
        card.add(valueLabel, g);

        g.gridy = 2;
        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelText.setForeground(TEXT_MUTED);
        card.add(labelText, g);

        return card;
    }

    private JPanel buildSimplePage(String heading, String description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel h = new JLabel(heading);
        h.setFont(new Font("Segoe UI", Font.BOLD, 24));
        h.setForeground(TEXT_DARK);
        h.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel d = new JLabel(description);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        d.setForeground(TEXT_MUTED);
        d.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(h);
        panel.add(Box.createVerticalStrut(10));
        panel.add(d);
        return panel;
    }
}