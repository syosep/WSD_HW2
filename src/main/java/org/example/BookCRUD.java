package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookCRUD {
    private Connection conn;

    public BookCRUD() {
        try {
            String dbFile = "book.db";
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            System.out.println("데이터베이스에 연결되었습니다.");
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 오류: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO book (title, author, publisher, publication_date, create_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublisher());
            pstmt.setString(4, book.getPublicationDate());
            pstmt.setString(5, book.getCreateDate());
            pstmt.executeUpdate();
            System.out.println("도서가 추가되었습니다.");
        } catch (SQLException e) {
            System.out.println("도서 추가 오류 : " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                String publicationDate = rs.getString("publication_date");
                String createDate = rs.getString("create_date");

                Book book = new Book(id, title, author, publisher, publicationDate, createDate);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("도서 조회 오류: " + e.getMessage());
        }
        return books;
    }
}