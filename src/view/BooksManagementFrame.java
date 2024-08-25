package view;

import UiUtils.UIUtils;
import db.BookDAO;
import model.Book;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BooksManagementFrame {

    private JFrame managementFrame;
    private JTable table;
    private JTextField idField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField yearField;
    private JTextField statusField;
    private DefaultTableModel tableModel;
    private BookDAO bookDAO;


    public BooksManagementFrame() {
        bookDAO = new BookDAO();
        initialize();
        managementFrame.setVisible(true);
        loadAllBooks();
    }

    private void initialize() {
        managementFrame = new JFrame();
        managementFrame.setBounds(100, 100, 860, 540);
        managementFrame.setResizable(false);
        managementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        managementFrame.setTitle("Library Management System - Books Management");
        managementFrame.getContentPane().setLayout(null);

        UIUtils.setLookAndFeel();
        UIUtils.setFrameIcon(managementFrame, "img/fLogo.png");

        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new LineBorder(SystemColor.textHighlight, 5));
        tablePanel.setBounds(260, 10, 575, 395);
        managementFrame.getContentPane().add(tablePanel);
        tablePanel.setLayout(new BorderLayout());

        JScrollPane tableScrollPane = new JScrollPane();
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[][]{},
                new String[]{"ID", "Title", "Author", "Publisher", "Year", "Status"}) {
            boolean[] columnEditables = new boolean[]{false, true, true, true, true, true};

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        };
        table = new JTable();
        table.setModel(tableModel);
        tableScrollPane.setViewportView(table);

        table.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                // Handle updates if needed
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

        JPanel bookPanel = new JPanel();
        bookPanel.setBorder(new LineBorder(SystemColor.textHighlight, 5));
        bookPanel.setBounds(10, 10, 240, 395);
        managementFrame.getContentPane().add(bookPanel);
        bookPanel.setLayout(null);

        addBookFields(bookPanel);
    }

    private void addButton(JPanel buttonsPanel) {
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idField.getText().isEmpty() || titleField.getText().isEmpty() || authorField.getText().isEmpty() ||
                        publisherField.getText().isEmpty() || yearField.getText().isEmpty() || statusField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(managementFrame, "Please fill all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(idField.getText());
                    int year = Integer.parseInt(yearField.getText());
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String publisher = publisherField.getText();
                    String status = statusField.getText();

                    Book newBook = new Book(id, title, author, publisher, year, status);
                    bookDAO.addBook(newBook);
                    tableModel.addRow(new Object[]{id, title, author, publisher, year, status});
                    clearFields();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(managementFrame, "Invalid input format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonsPanel.add(addButton);
    }

    private void updateButton(JPanel buttonsPanel) {
        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(managementFrame, "Please select a book to update.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int id = Integer.parseInt(idField.getText());
                    int year = Integer.parseInt(yearField.getText());
                    String title = titleField.getText();
                    String author = authorField.getText();
                    String publisher = publisherField.getText();
                    String status = statusField.getText();

                    Book updatedBook = new Book(id, title, author, publisher, year, status);
                    bookDAO.updateBook(updatedBook);
                    tableModel.setValueAt(id, selectedRow, 0);
                    tableModel.setValueAt(title, selectedRow, 1);
                    tableModel.setValueAt(author, selectedRow, 2);
                    tableModel.setValueAt(publisher, selectedRow, 3);
                    tableModel.setValueAt(year, selectedRow, 4);
                    tableModel.setValueAt(status, selectedRow, 5);
                    clearFields();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(managementFrame, "Invalid input format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonsPanel.add(updateButton);
    }

    private void deleteButton(JPanel buttonsPanel) {
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(managementFrame, "Please select a book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id = (int) tableModel.getValueAt(selectedRow, 0);
                bookDAO.deleteBook(id);
                tableModel.removeRow(selectedRow);
            }
        });
        buttonsPanel.add(deleteButton);
    }

    private void viewButton(JPanel buttonsPanel) {
        JButton viewButton = new JButton("View");
        viewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadAllBooks();
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
                new MainFrame();
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

    private void addBookFields(JPanel bookPanel) {
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        idLabel.setBounds(10, 20, 200, 30);
        bookPanel.add(idLabel);

        idField = new JTextField();
        idField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        idField.setBounds(50, 20, 180, 30);
        bookPanel.add(idField);
        idField.setColumns(10);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        titleLabel.setBounds(10, 70, 200, 30);
        bookPanel.add(titleLabel);

        titleField = new JTextField();
        titleField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        titleField.setBounds(50, 70, 180, 30);
        bookPanel.add(titleField);
        titleField.setColumns(10);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        authorLabel.setBounds(10, 120, 200, 30);
        bookPanel.add(authorLabel);

        authorField = new JTextField();
        authorField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        authorField.setBounds(70, 120, 150, 30);
        bookPanel.add(authorField);
        authorField.setColumns(10);

        JLabel publisherLabel = new JLabel("Publisher:");
        publisherLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        publisherLabel.setBounds(10, 170, 200, 30);
        bookPanel.add(publisherLabel);

        publisherField = new JTextField();
        publisherField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        publisherField.setBounds(85, 170, 140, 30);
        bookPanel.add(publisherField);
        publisherField.setColumns(10);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        yearLabel.setBounds(10, 220, 200, 30);
        bookPanel.add(yearLabel);

        yearField = new JTextField();
        yearField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        yearField.setBounds(70, 220, 150, 30);
        bookPanel.add(yearField);
        yearField.setColumns(10);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        statusLabel.setBounds(10, 270, 200, 30);
        bookPanel.add(statusLabel);

        statusField = new JTextField();
        statusField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        statusField.setBounds(70, 270, 150, 30);
        bookPanel.add(statusField);
        statusField.setColumns(10);
    }

    private void loadAllBooks() {
        List<Book> books = bookDAO.getAllBooks();
        tableModel.setRowCount(0); // Clear the table
        for (Book book : books) {
            tableModel.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYear(), book.getStatus()});
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        publisherField.setText("");
        yearField.setText("");
        statusField.setText("");
    }
}
