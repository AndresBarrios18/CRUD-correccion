package dao;

import com.mycompany.tareacrud.CRUD;
import com.mycompany.tareacrud.Carro;
import com.mycompany.tareacrud.DB;
import com.mycompany.tareacrud.Entity;
import com.mycompany.tareacrud.User;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO<T extends Entity> implements CRUD<T> {

    private Connection connection;
    private static DAO<?> instance;

    private DAO() {
        try {
            connection = DB.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DAO<?> getInstance() {
        if (instance == null) {
            instance = new DAO<>();
        }
        return instance;
    }

    @Override
    public T create(T entity) {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
        sqlBuilder.append(entity.getClass().getSimpleName()).append(" (");

        Method[] methods = entity.getClass().getMethods();
        List<String> columnNames = new ArrayList<>();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                String columnName = method.getName().substring(3); // Elimina el prefijo 'get'
                columnNames.add(columnName);
            }
        }
        sqlBuilder.append(String.join(", ", columnNames)).append(") VALUES (");

        for (int i = 0; i < columnNames.size(); i++) {
            if (i > 0) {
                sqlBuilder.append(", ");
            }
            sqlBuilder.append("?");
        }
        sqlBuilder.append(")");

        String sql = sqlBuilder.toString();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int parameterIndex = 1;
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    Object value = method.invoke(entity);
                    statement.setObject(parameterIndex, value);
                    parameterIndex++;
                }
            }

            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    public T read(int id, Class<T> entityType) {
        T entity = null;
        String sql = "SELECT * FROM " + entityType.getSimpleName() + " WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = buildEntityFromResultSet(resultSet, entityType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entity;
    }


    public void delete(int id, Class<T> entityType) {
        String sql = "DELETE FROM " + entityType.getSimpleName() + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 
    public List<T> getAll(Class<T> entityType) {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + entityType.getSimpleName();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = buildEntityFromResultSet(resultSet, entityType);
                entities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities;
    }

    private T buildEntityFromResultSet(ResultSet resultSet, Class<T> entityType) throws SQLException {
        if (entityType == User.class) {
            return (T) buildUserFromResultSet(resultSet);
        } else if (entityType == Carro.class) {
            return (T) buildCarroFromResultSet(resultSet);
        } else {
            // Implementa la lógica para otras entidades si es necesario
            return null;
        }
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

    @Override
    public T read(int id) {
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

    @Override
    public T update(T entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
