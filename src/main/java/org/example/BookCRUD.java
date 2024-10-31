package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookCRUD {
    Connection conn;

    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, publisher, publication_date, create_date) VALUES (?, ?, ?, ?, ?)";
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
}
