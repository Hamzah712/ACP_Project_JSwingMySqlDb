import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends WindowAdapter implements ActionListener{

    //declaring objects

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton resetButton;
    private JButton signUpButton;

    private JLabel welcomeLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel signUpLabel;

    private GridBagLayout gbl = new GridBagLayout();
    private GridBagConstraints gbc;

    private ImageIcon imageIcon;

    //constructor
    public LoginForm()
    {
        setUpFrame();
        createComp();
        addComp();
        addActionListener();

        frame.setSize(350,350);
        frame.setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if(e.getSource() == resetButton) {
            usernameField.setText("");
            passwordField.setText("");
        } else if (e.getSource() == signUpButton) {
            new SignupForm();
            frame.dispose();
        } else if (e.getSource() == loginButton) {
            if (username.isBlank() && password.isBlank()) {
                JOptionPane.showMessageDialog(frame, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }else if (username.isBlank()) {
                JOptionPane.showMessageDialog(frame, "Username cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (password.isBlank()) {
                JOptionPane.showMessageDialog(frame, "Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Additional validation can be added here (e.g., check credentials against a database)
                JOptionPane.showMessageDialog(frame, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
            }


        }
    }

    private void setUpFrame(){
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e){
            e.printStackTrace();
        }

        frame = new JFrame("Login Form");
        frame.setLocationRelativeTo(null);
        gbl = new GridBagLayout();
        frame.setLayout(gbl);
        gbc = new GridBagConstraints();

        imageIcon = new ImageIcon("C:/Users/pc/Desktop/225.jpg");
        Image img = imageIcon.getImage();
        frame.setIconImage(img);
    }

    private void createComp (){
        // create components
        welcomeLabel = new JLabel("Welcome :) ");
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        signUpLabel = new JLabel("not registered? ");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        loginButton = new JButton("login ");
        resetButton = new JButton("reset ");
        signUpButton = new JButton("signUp ");

    }

    private void addComp(){
        // adding components
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across 2 columns
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10,5,10,5);
        Font welcomeLabelFont = new Font("Italic", Font.PLAIN, 25);
        welcomeLabel.setFont(welcomeLabelFont);
        frame.add(welcomeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(usernameLabel, gbc);

        gbc.gridy = 2;
        frame.add(passwordLabel, gbc);

        gbc.gridy = 4;
        frame.add(signUpLabel,gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(resetButton, gbc);

        gbc.gridx = 2;
        frame.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(signUpButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(usernameField, gbc);

        gbc.gridy = 2;
        frame.add(passwordField, gbc);
    }

    private void addActionListener(){
        // add action listener
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        signUpButton.addActionListener(this);
    }
}

