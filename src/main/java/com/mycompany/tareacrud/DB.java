/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareacrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author pipe2
 */
public class DB {
    
    private static final String URL = "jdbc:mysql://localhost:3306/crud";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;
    
    
    public static synchronized Connection getConnection() throws SQLException {
    
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }
        return connection;
    }
}