package com.example.commande.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            // Configuration FreeSQLDatabase
            String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7810365?useSSL=false&serverTimezone=UTC";
            String username = "sql7810365";
            String password = "AJBzBLXTl8";

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erreur de connexion: " + e.getMessage(), e);
        }
    }
}
















//package com.example.commande.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DatabaseConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/commande_db";
//private static final String USER = "root";
//    private static final String PASSWORD = "";
//
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("Driver MySQL non trouvé", e);
//        }
//    }
//
//    public static Connection getConnection() {
//        try {
//            return DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException("Erreur de connexion à la base de données", e);
//        }
//    }
//}