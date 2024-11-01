package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void saveFile() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        String timestamp = LocalDateTime.now().format(dtf);
        String fileName = "data_" + timestamp + ".txt";

        List<Book> books = getAllBooks();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Book book : books) {
                bw.write("ID : " + book.getId());
                bw.write(", 제목 : " + book.getTitle());
                bw.write(", 저자 : " + book.getAuthor());
                bw.write(", 출판사 : " + book.getPublisher());
                bw.write(", 출판일 : " + book.getPublicationDate());
                bw.write(", 추가일 : " + book.getCreateDate());
                bw.newLine();
            }
            System.out.println("도서 DB가 txt 파일로 저장되었습니다.");
        } catch (IOException e) {
            System.out.println("파일 저장 오류 : " + e.getMessage());
        }
    }

    public int totalBook() {
        String sql = "SELECT COUNT(*) AS count FROM book";
        int count = 0;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("전체 도서 수 조회 오류 : " + e.getMessage());
        }
        return count;
    }

    public Map<String, Integer> bookByAuthor() {
        String sql = "SELECT author, COUNT(*) AS count FROM book GROUP BY author";
        Map<String, Integer> authorCountMap = new HashMap<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String author = rs.getString("author");
                int count = rs.getInt("count");
                authorCountMap.put(author, count);
            }
        } catch (SQLException e) {
            System.out.println("저자별 도서 수 조회 오류 : " + e.getMessage());
        }
        return authorCountMap;
    }

    public Map<String, Integer> bookByPublisher() {
        String sql = "SELECT publisher, COUNT(*) AS count FROM book GROUP BY publisher";
        Map<String, Integer> publisherCountMap = new HashMap<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String publisher = rs.getString("publisher");
                int count = rs.getInt("count");
                publisherCountMap.put(publisher, count);
            }
        } catch (SQLException e) {
            System.out.println("출판사별 도서 수 조회 오류 : " + e.getMessage());
        }
        return publisherCountMap;
    }

    public void recordUpdateHistory(int bookId, String fieldName, String oldValue, String newValue) {
        String fileName = "book_" + bookId + "_history.txt";
        String updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String record = updateDate + " - " + fieldName + " 변경: '" + oldValue + "' → '" + newValue + "'";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(record);
            writer.newLine();
            System.out.println("수정 이력이 파일에 기록되었습니다.");
        } catch (IOException e) {
            System.out.println("이력 기록 파일 오류 : " + e.getMessage());
        }
    }
}