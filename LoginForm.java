import javax.swing.*;
import java.awt.*;

public  class LoginForm extends JFrame {
    private JTextField username;
    private JPasswordField password;
    private JCheckBox showPasswrodCheckBox;
    private JComboBox<String> comboBox;
    private JLabel statusLabel;
    private JButton loginButton;

    public LoginForm() {
        setTitle("SouthEast Hospital Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1260, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(1, 2)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };

        //panel.add(buildLeftFane());
    }
}