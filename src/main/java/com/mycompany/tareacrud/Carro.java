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

public class Carro extends Entity {
    private String marca;
    private String color;

    public Carro() {
    }

    public Carro(int id, String marca, String color) {
        super(id);
        this.marca = marca;
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    

    // Implementación del método buildEntityFromResultSet para Carro
    @Override
    public Carro buildEntityFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String marca = resultSet.getString("marca");
        String color = resultSet.getString("color");
        return new Carro(id, marca, color);
    }
}

