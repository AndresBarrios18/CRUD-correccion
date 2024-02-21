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
        T entity = null;
    String sql = "SELECT * FROM " + entity.getClass().getSimpleName() + " WHERE id = ?";
    
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            // Construye una nueva instancia de la clase T y establece sus atributos
            entity = buildEntityFromResultSet(resultSet);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Manejo de errores
    }
    
    return entity;
    }

    
    
    @Override
    public T update(T entity) {
         String sql = "UPDATE " + entity.getClass().getSimpleName() + " SET ";
    Method[] methods = entity.getClass().getMethods();
    List<String> columnNames = new ArrayList<>();
    
    for (Method method : methods) {
        if (method.getName().startsWith("get")) {
            String columnName = method.getName().substring(3); // Elimina el prefijo 'get'
            columnNames.add(columnName + " = ?");
        }
    }
    
    sql += String.join(", ", columnNames) + " WHERE id = ?";
    
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        int parameterIndex = 1;
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                Object value = method.invoke(entity); // Invoca el método 'get' correspondiente para obtener el valor
                statement.setObject(parameterIndex, value);
                parameterIndex++;
            }
        }
        // Agrega el ID como último parámetro
        statement.setInt(parameterIndex, entity.getId());
        
        // Ejecuta la sentencia SQL de actualización
        statement.executeUpdate();
    } catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
        // Manejo de errores
    }
    return entity;
    }

    
    
    
    
    @Override
    public void delete(int id) {
        
    String sql = "DELETE FROM " + T.getClass().getSimpleName() + " WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        // Manejo de errores
    }
}

    @Override
    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
       
    String sql = "SELECT * FROM " + entity.getClass().getSimpleName();
    
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            // Para cada fila en el ResultSet, construye una instancia de la clase T y agrégala a la lista
            T = buildEntityFromResultSet(resultSet);
            entities.add(entity);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Manejo de errores
    }
    
    return entities;
    }

    private User buildUserFromResultSet(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("id");
    String name = resultSet.getString("name");
    String email = resultSet.getString("email");
    return new User(id, name, email);
}
    
    private Carro buildCarroFromResultSet(ResultSet resultSet) throws SQLException {
    int id = resultSet.getInt("id");
    String color = resultSet.getString("color");
    String marca = resultSet.getString("marca");
    return new Carro(id, color, marca);
}
    
}
