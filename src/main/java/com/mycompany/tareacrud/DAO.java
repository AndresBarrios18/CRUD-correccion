/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tareacrud;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pipe2
 */
public class DAO<T> implements CRUD<T> {
    
    
    private Connection connection;
    private static DAO<?> instance;
    
    
    private DAO(){
     
        try{
        
            connection = DB.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        
        }   
    }
        
    public static synchronized DAO<?> getInstance(){
    
        if(instance == null){
            instance = new DAO<>();
        
        }
        return instance;
    }
        
    
    @Override
    public T create(T entity) {
        // Genera una sentencia SQL de inserción dinámica basada en los métodos 'set' de la clase
    StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
    sqlBuilder.append(entity.getClass().getSimpleName()).append(" (");

    // Obtén la lista de métodos 'set' de la clase
    Method[] methods = entity.getClass().getMethods();
    List<String> columnNames = new ArrayList<>();
    for (Method method : methods) {
        if (method.getName().startsWith("set")) {
            String columnName = method.getName().substring(3); // Elimina el prefijo 'set'
            columnNames.add(columnName);
        }
    }
    sqlBuilder.append(String.join(", ", columnNames)).append(") VALUES (");

    // Añade los marcadores de posición para los valores
    for (int i = 0; i < columnNames.size(); i++) {
        if (i > 0) {
            sqlBuilder.append(", ");
        }
        sqlBuilder.append("?");
    }
    sqlBuilder.append(")");

    String sql = sqlBuilder.toString();

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        // Establece los valores de los parámetros de la sentencia SQL
        int parameterIndex = 1;
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                Object value = method.invoke(entity); // Invoca el método 'get' correspondiente para obtener el valor
                statement.setObject(parameterIndex, value);
                parameterIndex++;
            }
        }

        // Ejecuta la sentencia SQL de inserción
        statement.executeUpdate();
    } catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
        // Manejo de errores
        
    }
        return null;
    }

    @Override
    public T read(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public T update(T entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<T> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
