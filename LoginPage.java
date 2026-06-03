import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class LoginPage extends JFrame {

    static final String IMAGE_PATH1 = "C:\\Users\\pc\\Downloads\\Hospital-Patient-Management\\logo.jpeg";
    static final String IMAGE_PATH2 = "C:\\Users\\pc\\Downloads\\Hospital-Patient-Management\\background.jpeg";

    HospitalService hospitalService;


    static class RoundedField extends JTextField {
        public RoundedField(int cols) { super(cols); setOpaque(false); }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
            g2.dispose();
        }
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(180, 180, 210));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
            g2.dispose();
        }
    }

    static class RoundedPassField extends JPasswordField {
        public RoundedPassField(int cols) { super(cols); setOpaque(false); }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
            g2.dispose();
        }
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(180, 180, 210));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
            g2.dispose();
        }
    }

    static class RoundedButton extends JButton {
        private Color bg;
        public RoundedButton(String text, Color bg) {
            super(text);
            this.bg = bg;
            setOpaque(false); setFocusPainted(false);
            setBorderPainted(false); setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getModel().isPressed() ? bg.darker() : getModel().isRollover() ? bg.brighter() : bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public LoginPage(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
        setTitle("Hospital Management — Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        BufferedImage logo = null, background = null;
        try { logo       = ImageIO.read(new File(IMAGE_PATH1)); } catch (IOException e) { System.out.println("Logo not found."); }
        try { background = ImageIO.read(new File(IMAGE_PATH2)); } catch (IOException e) { System.out.println("Background not found."); }

        final BufferedImage sharedLogo = logo;
        final BufferedImage sharedBg   = background;

        if (sharedLogo != null) setIconImage(sharedLogo);

        // Background panel
        JPanel bg = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (sharedBg != null) {
                    g2.drawImage(sharedBg, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2.setPaint(new GradientPaint(0, 0, new Color(99, 102, 241), getWidth(), getHeight(), new Color(168, 85, 247)));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                g2.setColor(new Color(0, 0, 0, 140));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bg.setLayout(new GridBagLayout());
        setContentPane(bg);

        // Card
        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 215));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(480, 610));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(36, 40, 6, 40);
        if (sharedLogo != null) {
            Image scaled = sharedLogo.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaled), SwingConstants.CENTER) {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setClip(new java.awt.geom.Ellipse2D.Float((getWidth()-80)/2f, (getHeight()-80)/2f, 80, 80));
                    super.paintComponent(g);
                    g2.dispose();
                    Graphics2D g3 = (Graphics2D) g.create();
                    g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g3.setColor(new Color(99, 102, 241));
                    g3.setStroke(new BasicStroke(3f));
                    g3.drawOval((getWidth()-80)/2, (getHeight()-80)/2, 80, 80);
                    g3.dispose();
                }
            };
            logoLabel.setPreferredSize(new Dimension(400, 90));
            card.add(logoLabel, gbc);
        }

        JLabel title = new JLabel("Welcome Back", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(60, 40, 120));
        gbc.gridy = 1; gbc.insets = new Insets(4, 40, 2, 40);
        card.add(title, gbc);

        JLabel sub = new JLabel("Sign in to your account", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(new Color(130, 110, 180));
        gbc.gridy = 2; gbc.insets = new Insets(0, 40, 20, 40);
        card.add(sub, gbc);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(new Color(80, 60, 140));
        gbc.gridy = 3; gbc.insets = new Insets(6, 40, 2, 40);
        card.add(userLabel, gbc);

        RoundedField userField = new RoundedField(20);
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        userField.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        userField.setPreferredSize(new Dimension(400, 48));
        gbc.gridy = 4; gbc.insets = new Insets(2, 40, 10, 40);
        card.add(userField, gbc);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(80, 60, 140));
        gbc.gridy = 5; gbc.insets = new Insets(6, 40, 2, 40);
        card.add(passLabel, gbc);

        RoundedPassField passField = new RoundedPassField(20);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        passField.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        passField.setPreferredSize(new Dimension(400, 48));
        gbc.gridy = 6; gbc.insets = new Insets(2, 40, 6, 40);
        card.add(passField, gbc);

        JCheckBox showPass = new JCheckBox("Show Password");
        showPass.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        showPass.setForeground(new Color(80, 60, 140));
        showPass.setOpaque(false); showPass.setFocusPainted(false);
        showPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 7; gbc.insets = new Insets(0, 42, 10, 40);
        card.add(showPass, gbc);
        showPass.addActionListener(e -> passField.setEchoChar(showPass.isSelected() ? (char) 0 : '•'));

        JLabel msgLabel = new JLabel("", SwingConstants.CENTER);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        gbc.gridy = 8; gbc.insets = new Insets(0, 40, 4, 40);
        card.add(msgLabel, gbc);

        RoundedButton loginBtn = new RoundedButton("Login", new Color(99, 102, 241));
        loginBtn.setPreferredSize(new Dimension(400, 50));
        gbc.gridy = 9; gbc.insets = new Insets(8, 40, 36, 40);
        card.add(loginBtn, gbc);

        ActionListener loginAction = e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                msgLabel.setForeground(new Color(200, 50, 50));
                msgLabel.setText("Please fill in all fields.");
            } else if (user.equals("admin") && pass.equals("1234")) {
                // Open Dashboard and close Login
                dispose();
                new Dashboard(user, sharedLogo, hospitalService);
            } else {
                msgLabel.setForeground(new Color(200, 50, 50));
                msgLabel.setText("Invalid username or password.");
            }
        };

        loginBtn.addActionListener(loginAction);
        passField.addActionListener(loginAction);

        bg.add(card);
        setVisible(true);
    }
}