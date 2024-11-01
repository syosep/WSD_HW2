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

    public void updateBook(int id, String newTitle, String newAuthor, String newPublisher, String newPublicationDate) {
        String sql = "UPDATE book SET title = ?, author = ?, publisher = ?, publication_date = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newAuthor);
            pstmt.setString(3, newPublisher);
            pstmt.setString(4, newPublicationDate);
            pstmt.setInt(5, id);

            int index = pstmt.executeUpdate();
            if (index > 0) {
                System.out.println("도서 정보가 수정되었습니다.");
            } else {
                System.out.println("해당 ID의 도서를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            System.out.println("도서 수정 오류 : " + e.getMessage());
        }
    }

    public void deleteBook(int id) {
        String sql = "DELETE FROM book WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int index = pstmt.executeUpdate();
            if (index > 0) {
                System.out.println("도서가 삭제되었습니다.");
            } else {
                System.out.println("해당 ID의 도서를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            System.out.println("도서 삭제 오류 : " + e.getMessage());
        }
    }

    public List<Book> searchBook(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE title LIKE ? OR author LIKE ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

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
            System.out.println("도서 검색 오류 : " + e.getMessage());
        }
        return books;
    }

    public List<Book> sortBook(String sortType) {
        List<Book> books = new ArrayList<>();
        String sql;

        if ("author".equalsIgnoreCase(sortType)) {
            sql = "SELECT * FROM book ORDER BY author";
        } else if ("publisher".equalsIgnoreCase(sortType)) {
            sql = "SELECT * FROM book ORDER BY publisher";
        } else {
            System.out.println("저자와 출판사로만 정렬이 가능합니다.");
            return books;
        }

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
            System.out.println("도서 정렬 오류 : " + e.getMessage());
        }
        return books;
    }
}