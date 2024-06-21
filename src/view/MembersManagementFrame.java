package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import db.MemberDAO;
import model.Member;
import UiUtils.UIUtils;

public class MembersManagementFrame {

    private JFrame managementFrame;
    private JTable table;
    private JTextField firstNameField;
    private JTextField idField;
    private JTextField lastNameField;
    private JTextField ageField;
    private JTextField membershipDateField;
    private JComboBox<String> genderSelectionBox;
    private MemberDAO memberDAO;

    public MembersManagementFrame() {
        memberDAO = new MemberDAO();
        initialize();
        table.clearSelection();
        managementFrame.setVisible(true);
        loadMembers(); // Load members into the table
    }

    private void initialize() {
        managementFrame = new JFrame();
        managementFrame.setBounds(100, 100, 860, 540);
        managementFrame.setResizable(false);
        managementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managementFrame.setTitle("Library Management System");
        managementFrame.getContentPane().setLayout(null);

        UIUtils.setLookAndFeel();
        UIUtils.setFrameIcon(managementFrame, "img/fLogo.png");

        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new LineBorder(SystemColor.textHighlight, 5));
        tablePanel.setBounds(260, 10, 575, 395);
        managementFrame.getContentPane().add(tablePanel);
        tablePanel.setLayout(null);

        JScrollPane tableScrollPane = new JScrollPane();
        tableScrollPane.setBounds(10, 10, 555, 375);
        tablePanel.add(tableScrollPane);

        table = new JTable();
        tableScrollPane.setViewportView(table);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        table.setModel(new DefaultTableModel(new Object[][] {},
                new String[] { "ID", "First Name", "Last Name", "Age", "Gender", "Membership Date" }) {
            boolean[] columnEditables = new boolean[] { false, true, true, true, true, true };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });

        TableRowSorter<DefaultTableModel> tableSorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(tableSorter);

        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                // Handle table changes if necessary
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new LineBorder(new Color(0, 120, 215), 5));
        buttonsPanel.setBackground(UIManager.getColor("Button.background"));
        buttonsPanel.setBounds(10, 415, 825, 80);
        managementFrame.getContentPane().add(buttonsPanel);
        buttonsPanel.setLayout(new GridLayout(0, 6, 0, 0));

        addButton(buttonsPanel);
        updateButton(buttonsPanel);
        deleteButton(buttonsPanel);
        viewButton(buttonsPanel);
        homeButton(buttonsPanel);
        exitButton(buttonsPanel);

        JPanel memberPanel = new JPanel();
        memberPanel.setBorder(new LineBorder(SystemColor.textHighlight, 5));
        memberPanel.setBounds(10, 10, 240, 395);
        managementFrame.getContentPane().add(memberPanel);
        memberPanel.setLayout(null);

        addMemberFields(memberPanel);
    }

    private void addButton(JPanel buttonsPanel) {
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMember();
            }
        });
        buttonsPanel.add(addButton);
    }

    private void updateButton(JPanel buttonsPanel) {
        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateMember();
            }
        });
        buttonsPanel.add(updateButton);
    }

    private void deleteButton(JPanel buttonsPanel) {
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteMember();
            }
        });
        buttonsPanel.add(deleteButton);
    }

    private void viewButton(JPanel buttonsPanel) {
        JButton viewButton = new JButton("View");
        viewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadMembers();
            }
        });
        buttonsPanel.add(viewButton);
    }

    private void homeButton(JPanel buttonsPanel) {
        JButton homeButton = new JButton("Home Page");
        homeButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                managementFrame.dispose();
                new MainFrame("Admin Name");
            }
        });
        buttonsPanel.add(homeButton);
    }

    private void exitButton(JPanel buttonsPanel) {
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonsPanel.add(exitButton);
    }

    private void addMemberFields(JPanel memberPanel) {

        JLabel idText = new JLabel("ID:");
        idText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        idText.setBounds(10, 20, 100, 25);
        memberPanel.add(idText);

        idField = new JTextField();
        idField.setBounds(120, 20, 110, 25);
        memberPanel.add(idField);
        idField.setColumns(10);

        JLabel firstNameText = new JLabel("First Name:");
        firstNameText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        firstNameText.setBounds(10, 60, 100, 25);
        memberPanel.add(firstNameText);

        firstNameField = new JTextField();
        firstNameField.setBounds(120, 60, 110, 25);
        memberPanel.add(firstNameField);
        firstNameField.setColumns(10);

        JLabel lastNameText = new JLabel("Last Name:");
        lastNameText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lastNameText.setBounds(10, 100, 100, 25);
        memberPanel.add(lastNameText);

        lastNameField = new JTextField();
        lastNameField.setBounds(120, 100, 110, 25);
        memberPanel.add(lastNameField);
        lastNameField.setColumns(10);

        JLabel ageText = new JLabel("Age:");
        ageText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ageText.setBounds(10, 140, 100, 25);
        memberPanel.add(ageText);

        ageField = new JTextField();
        ageField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        ageField.setBounds(120, 140, 110, 25);
        memberPanel.add(ageField);
        ageField.setColumns(10);

        JLabel genderText = new JLabel("Gender:");
        genderText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        genderText.setBounds(10, 180, 100, 25);
        memberPanel.add(genderText);

        genderSelectionBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });
        genderSelectionBox.setBounds(120, 180, 110, 25);
        memberPanel.add(genderSelectionBox);

        JLabel membershipDateText = new JLabel("Membership Date:");
        membershipDateText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        membershipDateText.setBounds(10, 220, 150, 25);
        memberPanel.add(membershipDateText);

        membershipDateField = new JTextField();
        membershipDateField.setBounds(140, 220, 90, 25);
        memberPanel.add(membershipDateField);
        membershipDateField.setColumns(10);

    }

    private boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private void addMember() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String ageStr = ageField.getText();
        String gender = (String) genderSelectionBox.getSelectedItem();
        String membershipDate = membershipDateField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || ageStr.isEmpty() || gender.isEmpty() || membershipDate.isEmpty()) {
            JOptionPane.showMessageDialog(managementFrame, "Please fill all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(managementFrame, "Please enter a valid age.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidDate(membershipDate)) {
            JOptionPane.showMessageDialog(managementFrame, "Please enter a valid date (yyyy-MM-dd).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Member member = new Member(0, firstName, lastName, age, gender, membershipDate);
        memberDAO.addMember(member);
        loadMembers();
        clearFields();
        JOptionPane.showMessageDialog(managementFrame, "Member successfully added.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateMember() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(managementFrame, "Please select a member to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = String.valueOf(genderSelectionBox.getSelectedItem());
            String membershipDate = membershipDateField.getText();

            Member updatedMember = new Member(id, firstName, lastName, age, gender, membershipDate);
            memberDAO.updateMember(updatedMember);
            table.setValueAt(id, selectedRow, 0);
            table.setValueAt(firstName, selectedRow, 1);
            table.setValueAt(lastName, selectedRow, 2);
            table.setValueAt(age, selectedRow, 3);
            table.setValueAt(gender, selectedRow, 4);
            table.setValueAt(membershipDate, selectedRow, 5);
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(managementFrame, "Invalid input format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        private void deleteMember() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(managementFrame, "Please select a member to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int id = (int) model.getValueAt(selectedRow, 0);

        memberDAO.deleteMember(id);
        loadMembers();
        JOptionPane.showMessageDialog(managementFrame, "Member successfully deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadMembers() {
        List<Member> members = memberDAO.getAllMembers();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Member member : members) {
            model.addRow(new Object[] { member.getId(), member.getFirstName(), member.getLastName(), member.getAge(), member.getGender(), member.getMembershipDate() });
        }
    }

    private void clearFields() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        genderSelectionBox.setSelectedIndex(0);
        membershipDateField.setText("");
    }
}
