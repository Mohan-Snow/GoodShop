package com.goodshop.dao;

import com.goodshop.models.User;
import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO<User, String> {

    @NotNull
    private final Connection connection;

    public UserDAO(@NotNull final Connection connection) {
        this.connection = connection;
    }


    // set values to the statement which were got from the model
    @Override
    public boolean create(@NotNull User usrModel) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO users (id, name, pass, role) VALUES (DEFAULT, (?),(?),(?)) RETURNING id")) {

            statement.setLong(1, usrModel.getId());
            statement.setString(2, usrModel.getName());
            statement.setString(3, usrModel.getPass());
            statement.setLong(4, usrModel.getRole().getId());

            result = statement.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("!SQLException: " + e.getMessage());
        }
        return result;
    }


    // get the values from the db and set them to the model
    @Override
    public User read(@NotNull String name) {
        User proxyUser = new User();
//        proxyUser.setId(-1);

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT u.id, u.name, u.pass, r.id AS rol_id, r.role FROM users AS u LEFT JOIN roles AS r ON u.role = r.id WHERE u.name = (?)")) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                proxyUser.setId(resultSet.getLong("id"));
                proxyUser.setName(resultSet.getString("name"));
                proxyUser.setPass(resultSet.getString("pass"));
                proxyUser.setRole(new User.Role(resultSet.getLong("rol_id"),
                        resultSet.getString("role")));
            }
        } catch (SQLException e) {
            System.out.println("!SQLException: " + e);
        }
        return proxyUser;
    }

    // set values to the statement which were got from the model
    @Override
    public boolean update(@NotNull User usrModel) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE users SET pass = (?) WHERE id = (?) RETURNING id")) {

            statement.setLong(1, usrModel.getId());
            statement.setString(2, usrModel.getPass());

            result = statement.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("!SQLException: " + e.getMessage());
        }
        return result;
    }

    // set values to the statement which were got from the model
    @Override
    public boolean delete(@NotNull User usrModel) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM users WHERE id = (?) AND name = (?) AND pass = (?) RETURNING id")) {

            statement.setLong(1, usrModel.getId());
            statement.setString(2, usrModel.getName());
            statement.setString(3, usrModel.getPass());

            result = statement.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("!SQLException: " + e.getMessage());
        }
        return result;
    }
}
