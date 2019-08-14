package com.goodshop;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        final Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/goods_shop", "postgres", "541454");

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=(?)")) {
            statement.setInt(1, 1);

            statement.executeQuery();
        } finally {
            connection.close();
        }
    }
}
