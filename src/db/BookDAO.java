package db;

import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // Method to add a book to the database
    public void addBook(Book book) {
        String query = "INSERT INTO books_tb (id, title, author, publisher, year, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, book.getId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getYear());
            pstmt.setString(6, book.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a book in the database
    public void updateBook(Book book) {
        String query = "UPDATE books_tb SET title = ?, author = ?, publisher = ?, year = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublisher());
            pstmt.setInt(4, book.getYear());
            pstmt.setString(5, book.getStatus());
            pstmt.setInt(6, book.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a book from the database
    public void deleteBook(int id) {
        String query = "DELETE FROM books_tb WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a book from the database by ID
    public Book getBook(int id) {
        String query = "SELECT * FROM books_tb WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("year"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all books from the database
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books_tb";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("year"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Method to issue a book
    public void issueBook(int bookId, int memberId, String issueDate, String returnDate) {
        String query = "INSERT INTO issued_books_tb (book_id, member_id, issue_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, bookId);
            pstmt.setInt(2, memberId);
            pstmt.setString(3, issueDate);
            pstmt.setString(4, returnDate);

            pstmt.executeUpdate();

            // Update book status to 'issued'
            updateBookStatus(bookId, "issued");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//          WTH IS THIS!!!
//    public void returnBook(int bookId) {
//        String updateReturnDateQuery = "UPDATE issued_books_tb SET return_date = CURRENT_DATE WHERE book_id = ? AND return_date IS NULL";
//        String deleteIssuedBookQuery = "DELETE FROM issued_books_tb WHERE book_id = ? AND return_date IS NOT NULL";
//        String updateBookStatusQuery = "UPDATE books_tb SET status = 'available' WHERE id = ?";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement updateReturnDateStmt = conn.prepareStatement(updateReturnDateQuery);
//             PreparedStatement deleteIssuedBookStmt = conn.prepareStatement(deleteIssuedBookQuery);
//             PreparedStatement updateBookStatusStmt = conn.prepareStatement(updateBookStatusQuery)) {
//
//            // Update the return date
//            updateReturnDateStmt.setInt(1, bookId);
//            int updatedRows = updateReturnDateStmt.executeUpdate();
//
//            if (updatedRows > 0) {
//                // Delete the record from issued_books_tb where return_date is not null
//                deleteIssuedBookStmt.setInt(1, bookId);
//                deleteIssuedBookStmt.executeUpdate();
//
//                // Update the book status to 'available'
//                updateBookStatusStmt.setInt(1, bookId);
//                updateBookStatusStmt.executeUpdate();
//
//                System.out.println("Book with ID: " + bookId + " successfully returned.");
//            } else {
//                // Handle case where book with specified ID was not found in issued_books_tb
//                System.out.println("No book found in issued_books_tb with ID: " + bookId + " that needs to be returned.");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void returnBook(int bookId) {
        String deleteIssuedBookQuery = "DELETE FROM issued_books_tb WHERE book_id = ?";
        String updateBookStatusQuery = "UPDATE books_tb SET status = 'available' WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement deleteIssuedBookStmt = conn.prepareStatement(deleteIssuedBookQuery);
             PreparedStatement updateBookStatusStmt = conn.prepareStatement(updateBookStatusQuery)) {

            // Delete the book from the issued_books_tb table
            deleteIssuedBookStmt.setInt(1, bookId);
            int rowsAffected = deleteIssuedBookStmt.executeUpdate();

            if (rowsAffected > 0) {
                // Update the book status to 'available'
                updateBookStatusStmt.setInt(1, bookId);
                updateBookStatusStmt.executeUpdate();

                System.out.println("Book with ID: " + bookId + " successfully returned and marked as available.");
            } else {
                // Handle case where book with specified ID was not found in issued_books_tb
                System.out.println("No book found in issued_books_tb with ID: " + bookId + ".");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to update the status of a book
    private void updateBookStatus(int bookId, String status) {
        String query = "UPDATE books_tb SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get all issued books (with return dates)
    public List<Book> getAllIssuedBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.id, ib.book_id, ib.member_id, ib.issue_date, ib.return_date " +
                "FROM issued_books_tb ib " +
                "JOIN books_tb b ON ib.book_id = b.id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getString("issue_date"),
                        rs.getString("return_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
