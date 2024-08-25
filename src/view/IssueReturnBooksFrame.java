package view;

import UiUtils.UIUtils;
import db.BookDAO;
import model.Book;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class IssueReturnBooksFrame {

    private JFrame managementFrame;
    private JTable table;
    private JTextField bookIdField;
    private JTextField memberIdField;
    private JTextField issueDateField;
    private JTextField returnDateField;
    private BookDAO bookDAO;

    public IssueReturnBooksFrame() {
        bookDAO = new BookDAO();
        initialize();
        table.clearSelection();
        managementFrame.setVisible(true);
        loadIssuedBooks(); // Load issued books into the table
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
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Ensure single row selection
        table.setRowSelectionAllowed(true); // Allow row selection
        table.setColumnSelectionAllowed(false); // Disallow column selection
        tableScrollPane.setViewportView(table);

        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Book ID", "Member ID", "Issue Date", "Return Date"}
        ) {
            boolean[] columnEditables = new boolean[]{false, true, true, true, true};

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });

        TableRowSorter<DefaultTableModel> tableSorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
        table.setRowSorter(tableSorter);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new LineBorder(new Color(0, 120, 215), 5));
        buttonsPanel.setBackground(UIManager.getColor("Button.background"));
        buttonsPanel.setBounds(10, 415, 825, 80);
        managementFrame.getContentPane().add(buttonsPanel);
        buttonsPanel.setLayout(new GridLayout(0, 5, 0, 0));

        addIssueButton(buttonsPanel);
        addReturnButton(buttonsPanel);
        addViewButton(buttonsPanel);
        addHomeButton(buttonsPanel);
        addExitButton(buttonsPanel);

        JPanel memberPanel = new JPanel();
        memberPanel.setBorder(new LineBorder(SystemColor.textHighlight, 5));
        memberPanel.setBounds(10, 10, 240, 395);
        managementFrame.getContentPane().add(memberPanel);
        memberPanel.setLayout(null);

        addMemberFields(memberPanel);
    }

    private void addIssueButton(JPanel buttonsPanel) {
        JButton issueButton = new JButton("Issue Book");
        issueButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        issueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                issueBook();
            }
        });
        buttonsPanel.add(issueButton);
    }

    private void addReturnButton(JPanel buttonsPanel) {
        JButton returnButton = new JButton("Return Book");
        returnButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
        buttonsPanel.add(returnButton);
    }

    private void addViewButton(JPanel buttonsPanel) {
        JButton viewButton = new JButton("View Issued Books");
        viewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadIssuedBooks();
            }
        });
        buttonsPanel.add(viewButton);
    }

    private void addHomeButton(JPanel buttonsPanel) {
        JButton homeButton = new JButton("Home Page");
        homeButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                managementFrame.dispose();
                new MainFrame();
            }
        });
        buttonsPanel.add(homeButton);
    }

    private void addExitButton(JPanel buttonsPanel) {
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
        JLabel bookIdText = new JLabel("Book ID:");
        bookIdText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        bookIdText.setBounds(10, 22, 100, 25);
        memberPanel.add(bookIdText);

        bookIdField = new JTextField();
        bookIdField.setBounds(120, 23, 110, 25);
        memberPanel.add(bookIdField);
        bookIdField.setColumns(10);

        JLabel memberIdText = new JLabel("Member ID:");
        memberIdText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        memberIdText.setBounds(10, 62, 100, 25);
        memberPanel.add(memberIdText);

        memberIdField = new JTextField();
        memberIdField.setColumns(10);
        memberIdField.setBounds(120, 63, 110, 25);
        memberPanel.add(memberIdField);

        JLabel issueDateText = new JLabel("Issue Date:");
        issueDateText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        issueDateText.setBounds(10, 102, 100, 25);
        memberPanel.add(issueDateText);

        issueDateField = new JTextField();
        issueDateField.setColumns(10);
        issueDateField.setBounds(120, 103, 110, 25);
        memberPanel.add(issueDateField);

        JLabel returnDateText = new JLabel("Return Date:");
        returnDateText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        returnDateText.setBounds(10, 142, 100, 25);
        memberPanel.add(returnDateText);

        returnDateField = new JTextField();
        returnDateField.setColumns(10);
        returnDateField.setBounds(120, 143, 110, 25);
        memberPanel.add(returnDateField);
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

    private void issueBook() {
        String bookIdStr = bookIdField.getText();
        String memberIdStr = memberIdField.getText();
        String issueDate = issueDateField.getText();
        String returnDate = returnDateField.getText();

        if (bookIdStr.isEmpty() || memberIdStr.isEmpty() || issueDate.isEmpty() || returnDate.isEmpty()) {
            JOptionPane.showMessageDialog(managementFrame, "Please fill all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookId;
        int memberId;
        try {
            bookId = Integer.parseInt(bookIdStr);
            memberId = Integer.parseInt(memberIdStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(managementFrame, "Please enter valid Book ID and Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidDate(issueDate) || !isValidDate(returnDate)) {
            JOptionPane.showMessageDialog(managementFrame, "Please enter valid dates (yyyy-MM-dd).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        bookDAO.issueBook(bookId, memberId, issueDate, returnDate);
        JOptionPane.showMessageDialog(managementFrame, "Book successfully issued.", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void returnBook() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(managementFrame, "Please select a book to return.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookIdColumnIndex = 1;

        Object bookIdValue = table.getValueAt(selectedRow, bookIdColumnIndex);

        if (bookIdValue instanceof Integer) {
            int bookId = (Integer) bookIdValue;
            bookDAO.returnBook(bookId);
            loadIssuedBooks();
            JOptionPane.showMessageDialog(managementFrame, "Book successfully returned.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(managementFrame, "Invalid Book ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadIssuedBooks() {
        List<Book> issuedBooks = bookDAO.getAllIssuedBooks();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing data

        for (Book book : issuedBooks) {
            model.addRow(new Object[]{
                    book.getId(),
                    book.getBookId(),
                    book.getMemberId(),
                    book.getIssueDate(),
                    book.getReturnDate()
            });
        }
    }

    private void clearFields() {
        bookIdField.setText("");
        memberIdField.setText("");
        issueDateField.setText("");
        returnDateField.setText("");
    }
}
