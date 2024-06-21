package view;

import UiUtils.UIUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends WindowAdapter implements ActionListener {

    private JFrame frame;
    private JButton btnManageBooks;
    private JButton btnManageMembers;
    private JButton btnIssueBook;
    private JButton btnReturnBook;
    private JButton btnLogout;
    private JLabel lblTotalBooks;
    private JLabel lblTotalMembers;
    private JLabel lblBooksIssuedToday;
    private JLabel lblBooksReturnedToday;

    public MainFrame(String adminName) {
        initializeFrame();
        UIUtils.setLookAndFeel();
        UIUtils.setFrameIcon(frame, "img/fLogo.png");

        addHeaderPanel(adminName);
        addNavigationPanel();
        addMainContentPanel();
        addFooterPanel();

        frame.addWindowListener(this);
        frame.setVisible(true);

        // Update the dashboard statistics
        updateDashboardStatistics();
    }

    private void initializeFrame() {
        frame = new JFrame("Library Management System - Dashboard");
        frame.setSize(620, 410);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    private void addHeaderPanel(String adminName) {
        JPanel headerPanel = new JPanel(null);
        headerPanel.setBounds(10, 10, 580, 30);
        JLabel lblWelcome = new JLabel("Welcome, " + adminName);
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblWelcome.setBounds(0, 0, 580, 30);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblWelcome);
        headerPanel.setBorder(new LineBorder(new Color(0, 120, 215), 2));
        frame.add(headerPanel);
    }

    private void addNavigationPanel() {
        JPanel navPanel = new JPanel(new GridLayout(1, 5));
        navPanel.setBounds(10, 50, 580, 50);
        btnManageBooks = new JButton("Manage Books");
        btnManageMembers = new JButton("Manage Members");
        btnIssueBook = new JButton("Issue Books");
        btnReturnBook = new JButton("Return Books");
        btnLogout = new JButton("Logout");

        // Add action listeners
        btnManageBooks.addActionListener(this);
        btnManageMembers.addActionListener(this);
        btnIssueBook.addActionListener(this);
        btnReturnBook.addActionListener(this);
        btnLogout.addActionListener(this);

        navPanel.add(btnManageBooks);
        navPanel.add(btnManageMembers);
        navPanel.add(btnIssueBook);
        navPanel.add(btnReturnBook);
        navPanel.add(btnLogout);

        navPanel.setBorder(new LineBorder(new Color(0, 120, 215), 2));
        frame.add(navPanel);
    }

    private void addMainContentPanel() {
        JPanel mainContentPanel = new JPanel(null);
        mainContentPanel.setBounds(10, 110, 580, 205);
        mainContentPanel.setBorder(new LineBorder(new Color(0, 120, 215), 2));

        lblTotalBooks = new JLabel("Total Books: ");
        lblTotalBooks.setBounds(5, 10, 580, 30);
        lblTotalBooks.setFont(new Font("Italic", Font.BOLD, 12));

        lblTotalMembers = new JLabel("Total Members: ");
        lblTotalMembers.setBounds(5, 50, 580, 30);
        lblTotalMembers.setFont(new Font("Italic", Font.BOLD, 12));

        lblBooksIssuedToday = new JLabel("Books Issued Today: ");
        lblBooksIssuedToday.setBounds(5, 90, 580, 30);
        lblBooksIssuedToday.setFont(new Font("Italic", Font.BOLD, 12));

        lblBooksReturnedToday = new JLabel("Books Returned Today: ");
        lblBooksReturnedToday.setBounds(5, 130, 580, 30);
        lblBooksReturnedToday.setFont(new Font("Italic", Font.BOLD, 12));

        mainContentPanel.add(lblTotalBooks);
        mainContentPanel.add(lblTotalMembers);
        mainContentPanel.add(lblBooksIssuedToday);
        mainContentPanel.add(lblBooksReturnedToday);

        frame.add(mainContentPanel);
    }

    private void addFooterPanel() {
        JPanel footerPanel = new JPanel(null);
        footerPanel.setBounds(10, 330, 580, 30);
        JLabel lblFooter = new JLabel("Library Management System");
        lblFooter.setBounds(0, 0, 570, 30);
        lblFooter.setHorizontalAlignment(SwingConstants.RIGHT);
        footerPanel.add(lblFooter);
        footerPanel.setBorder(new LineBorder(new Color(0, 120, 215), 2));
        frame.add(footerPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnManageBooks) {
            // Navigate to Manage Books Frame
            frame.dispose();
            new BooksManagementFrame();
        } else if (e.getSource() == btnManageMembers) {
            // Navigate to Manage Members Frame
            frame.dispose();
            new MembersManagementFrame();
        } else if (e.getSource() == btnIssueBook) {
            // Navigate to Issue Book Frame
            frame.dispose();
            new IssueReturnBooksFrame();
        } else if (e.getSource() == btnReturnBook) {
            // Navigate to Return Book Frame
            frame.dispose();
            new IssueReturnBooksFrame();
        } else if (e.getSource() == btnLogout) {
            // Logout and navigate to Login Frame
            frame.dispose();
            new LoginForm();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Perform any cleanup or save operations before closing
        super.windowClosing(e);
    }

    // Dummy methods until I feel like implementing them
    // replace this with actual implementation if you have time.
    private int getTotalBooks() {
        return 150;
    }

    private int getTotalMembers() {
        return 75;
    }

    private int getBooksIssuedToday() {
        return 5;
    }

    private int getBooksReturnedToday() {
        return 3;
    }

    public void updateDashboardStatistics() {
        lblTotalBooks.setText("Total Books: " + getTotalBooks());
        lblTotalMembers.setText("Total Members: " + getTotalMembers());
        lblBooksIssuedToday.setText("Books Issued Today: " + getBooksIssuedToday());
        lblBooksReturnedToday.setText("Books Returned Today: " + getBooksReturnedToday());
    }

}
