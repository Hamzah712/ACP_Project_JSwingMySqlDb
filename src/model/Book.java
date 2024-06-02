package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private String status;
    private String issueDate;
    private String returnDate;
    private int memberId;
    private int bookId; // Add this field

    // Constructor for book details without issue/return information
    public Book(int id, String title, String author, String publisher, int year, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.status = status;
    }

    // Constructor for book details with issue/return information
    public Book(int id, String title, String author, String publisher, int year, String status, String issueDate, String returnDate, int memberId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.status = status;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.memberId = memberId;
    }

    // Constructor for issue/return information only
    public Book(int id, int bookId, int memberId, String issueDate, String returnDate) {
        this.id = id;
        this.bookId = bookId; // Corrected
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
