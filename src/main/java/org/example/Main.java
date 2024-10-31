package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");

            String dbFile = "book.db";
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            System.out.println("도서 DataBase 연결 성공!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}