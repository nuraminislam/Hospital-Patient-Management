import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class HospitalLoginPage extends JFrame {

    private static final Color BG         = new Color(250, 250, 250);
    private static final Color CARD_BG    = Color.WHITE;
    private static final Color BORDER_CLR = new Color(220, 220, 220);
    private static final Color TEXT       = new Color(30, 30, 30);
    private static final Color MUTED      = new Color(140, 140, 140);
    private static final Color ACCENT     = new Color(37, 99, 235);   // blue
    private static final Color ACCENT_HOV = new Color(29, 78, 216);
    private static final Color ERROR      = new Color(220, 38, 38);
    private static final Color SUCCESS    = new Color(22, 163, 74);

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JLabel         statusLabel;
    private JButton        loginBtn;

    public HospitalLoginPage() {
        setTitle("Hospital Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_CLR, 1),
                new EmptyBorder(40, 40, 40, 40)
        ));
        card.setMaximumSize(new Dimension(340, 999));
        card.setPreferredSize(new Dimension(340, 440));

        // ── Header ──────────────────────────────────────
        JLabel cross = new JLabel("+");
        cross.setFont(new Font("Arial", Font.BOLD, 28));
        cross.setForeground(ACCENT);
        cross.setAlignmentX(CENTER_ALIGNMENT);
        card.add(cross);

        card.add(Box.createVerticalStrut(4));

        JLabel title = new JLabel("Hospital Management");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(TEXT);
        title.setAlignmentX(CENTER_ALIGNMENT);
        card.add(title);

        JLabel sub = new JLabel("Sign in to continue");
        sub.setFont(new Font("Arial", Font.PLAIN, 13));
        sub.setForeground(MUTED);
        sub.setAlignmentX(CENTER_ALIGNMENT);
        card.add(sub);

        card.add(Box.createVerticalStrut(28));

        // ── Role ────────────────────────────────────────
        card.add(label("Role"));
        card.add(Box.createVerticalStrut(5));
        roleBox = new JComboBox<>(new String[]{
                "Select role…", "Doctor", "Nurse", "Receptionist",
                "Pharmacist", "Administrator", "Lab Technician"
        });
        styleCombo(roleBox);
        card.add(roleBox);

        card.add(Box.createVerticalStrut(14));

        // ── Username ────────────────────────────────────
        card.add(label("Username"));
        card.add(Box.createVerticalStrut(5));
        usernameField = new JTextField();
        styleField(usernameField);
        card.add(usernameField);

        card.add(Box.createVerticalStrut(14));

        // ── Password ────────────────────────────────────
        card.add(label("Password"));
        card.add(Box.createVerticalStrut(5));
        passwordField = new JPasswordField();
        styleField(passwordField);
        card.add(passwordField);

        card.add(Box.createVerticalStrut(6));

        // ── Show password ───────────────────────────────
        JCheckBox showPass = new JCheckBox("Show password");
        showPass.setFont(new Font("Arial", Font.PLAIN, 12));
        showPass.setForeground(MUTED);
        showPass.setBackground(CARD_BG);
        showPass.setFocusPainted(false);
        showPass.addActionListener(e ->
                passwordField.setEchoChar(showPass.isSelected() ? '\0' : '•')
        );
        card.add(showPass);

        card.add(Box.createVerticalStrut(20));

        // ── Status ──────────────────────────────────────
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(ERROR);
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        card.add(statusLabel);

        card.add(Box.createVerticalStrut(8));

        // ── Login button ────────────────────────────────
        loginBtn = new JButton("Sign In") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? ACCENT_HOV : ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setOpaque(false);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        loginBtn.setAlignmentX(CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> handleLogin());
        getRootPane().setDefaultButton(loginBtn);
        card.add(loginBtn);

        root.add(card);
        setContentPane(root);
        setVisible(true);
    }

    // ── Helpers ──────────────────────────────────────────────────────────

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.PLAIN, 12));
        l.setForeground(TEXT);
        return l;
    }

    private void styleField(JTextField f) {
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setForeground(TEXT);
        f.setBackground(Color.WHITE);
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_CLR, 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
        if (f instanceof JPasswordField) ((JPasswordField) f).setEchoChar('•');
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT, 1),
                        new EmptyBorder(6, 10, 6, 10)
                ));
            }
            public void focusLost(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_CLR, 1),
                        new EmptyBorder(6, 10, 6, 10)
                ));
            }
        });
    }

    private void styleCombo(JComboBox<String> c) {
        c.setFont(new Font("Arial", Font.PLAIN, 13));
        c.setBackground(Color.WHITE);
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        c.setBorder(BorderFactory.createLineBorder(BORDER_CLR, 1));
    }

    // ── Login logic ──────────────────────────────────────────────────────

    private void handleLogin() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        String role = (String) roleBox.getSelectedItem();

        if (role == null || role.startsWith("Select")) {
            status("Please select a role.", true); return;
        }
        if (user.isEmpty()) {
            status("Username is required.", true); usernameField.requestFocus(); return;
        }
        if (pass.isEmpty()) {
            status("Password is required.", true); passwordField.requestFocus(); return;
        }

        loginBtn.setEnabled(false);
        loginBtn.setText("Signing in…");
        status("", false);

        // Simulate auth delay
        Timer t = new Timer(1000, e -> {
            // Demo credentials: admin / admin123
            if (user.equals("admin") && pass.equals("admin123")) {
                status("Login successful!", false);
                statusLabel.setForeground(SUCCESS);
                JOptionPane.showMessageDialog(this,
                        "Welcome, " + role + "!\nRedirecting to dashboard…",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                status("Invalid username or password.", true);
            }
            loginBtn.setEnabled(true);
            loginBtn.setText("Sign In");
        });
        t.setRepeats(false);
        t.start();
    }

    private void status(String msg, boolean err) {
        statusLabel.setText(msg.isEmpty() ? " " : msg);
        statusLabel.setForeground(err ? ERROR : SUCCESS);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(HospitalLoginPage::new);
    }
}