package view;

import UiUtils.UIUtils;
import db.UserDAO;
import model.User;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SignupForm extends WindowAdapter implements ActionListener {

    private JFrame signupFrame;
    private JPanel mainPanel;
    private JTextField fullNameField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signupButton;
    private JButton loginButton;
    // Declare other fields as needed

    public SignupForm() {
        setUpFrame();
        createComponents();
        addComponents();
        signupFrame.setSize(400, 300);
        signupFrame.setVisible(true);
    }

    private void setUpFrame() {
        signupFrame = new JFrame();
        signupFrame.setTitle("Signup Form");
        UIUtils.setLookAndFeel();
        UIUtils.setFrameIcon(signupFrame, "img/fLogo.png");
    }

    private void createComponents() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        mainPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx++;
        fullNameField = new JTextField(20);
        mainPanel.add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx++;
        phoneNumberField = new JTextField(20);
        mainPanel.add(phoneNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx++;
        addressField = new JTextField(20);
        mainPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx++;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx++;
        confirmPasswordField = new JPasswordField(20);
        mainPanel.add(confirmPasswordField, gbc);

        // Add more fields as needed (e.g., date of birth, gender, etc.)

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        signupButton = new JButton("Signup ");
        signupButton.addActionListener(this);
        mainPanel.add(signupButton, gbc);

        gbc.gridx++;
        gbc.insets = new Insets(5, 30, 5, 5);
        loginButton = new JButton("Login ");
        loginButton.addActionListener(this); // Register ActionListener
        mainPanel.add(loginButton, gbc);
    }

    private void addComponents() {
        signupFrame.getContentPane().add(mainPanel);
        signupFrame.setLocationRelativeTo(null);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        signupFrame.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == signupButton) {
            // Perform actions when "Add New " button is clicked
            String fullName = fullNameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String address = addressField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Validation
            if (fullName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty() ||
                    password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(signupFrame, "Please fill in all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(signupFrame, "Passwords do not match.");
                return;
            }

            // Create a User object
            User newUser = new User(fullName, phoneNumber, address, password);

            // Call DAO to add user to database
            UserDAO userDAO = new UserDAO();
            userDAO.addUser(newUser);

            // Optionally, show success message or clear fields
            JOptionPane.showMessageDialog(signupFrame, "User added successfully.");
            fullNameField.setText("");
            phoneNumberField.setText("");
            addressField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");

        } else if (e.getSource() == loginButton) {
            signupFrame.dispose();
            new LoginForm();
        }
    }
}
