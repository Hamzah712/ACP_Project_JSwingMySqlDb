import java.awt.*;
import javax.swing.*;

public class SignupForm extends JFrame {

    public SignupForm() {
        setTitle("Signup Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the main panel
        mainPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx++;
        mainPanel.add(new JTextField(20), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Email Address:"), gbc);
        gbc.gridx++;
        mainPanel.add(new JTextField(20), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx++;
        mainPanel.add(new JPasswordField(20), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx++;
        mainPanel.add(new JPasswordField(20), gbc);

        // Add more fields as needed (e.g., date of birth, gender, etc.)

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton signupButton = new JButton("Sign Up");
        mainPanel.add(signupButton, gbc);

        // Add the main panel to the frame's content pane
        getContentPane().add(mainPanel);

        // Center the frame on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
