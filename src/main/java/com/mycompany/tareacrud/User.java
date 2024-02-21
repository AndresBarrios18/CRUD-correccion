/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareacrud;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author pipe2
 */
public class User extends Entity {
    private String name;
    private String email;

    public User() {
    }

    public User(int id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    // Implementación del método buildEntityFromResultSet para User
    @Override
    public User buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        return new User(id, name, email);
    }
}
