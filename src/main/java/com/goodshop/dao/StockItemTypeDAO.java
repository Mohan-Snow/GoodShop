package com.goodshop.dao;

import com.goodshop.models.StockItemType;
import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockItemTypeDAO implements DAO<StockItemType, String> {

    @NotNull
    private final Connection connection;


    public StockItemTypeDAO(@NotNull final Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(@NotNull StockItemType itemModel) {
        if (isExist(itemModel.getName())) return false;

        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO item_types (id, name) VALUES (DEFAULT, (?)) RETURNING id;")) {

            statement.setString(1, itemModel.getName());
            result = statement.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("!SQLException: " + e.getMessage());
        }
        return result;
    }


    @Override
    public StockItemType read(@NotNull String name) {
        StockItemType proxyItem = new StockItemType();
//        proxyItem.setName(name);

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM item_types WHERE name = (?)")) {

            statement.setString(1, name);
            final ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                proxyItem.setId(rs.getLong("id"));
                proxyItem.setName(rs.getString("name"));
            } else {
                proxyItem.setName("this entity doesn't exist in item_types table");
                proxyItem.setId(-1);
            }

        } catch (SQLException e) {
            System.out.println("!SQLException: " + e);
        }
        return proxyItem;
    }

    @Override
    public boolean update(@NotNull StockItemType itemModel) {
        if (!isExist(itemModel.getName())) return false;

        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE item_types SET name = (?) WHERE id = (?) RETURNING id")) {

            statement.setString(1, itemModel.getName());
            statement.setLong(2, itemModel.getId());

            result = statement.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("!SQLException: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(@NotNull StockItemType itemModel) {
        if (!isExist(itemModel.getName())) return false;

        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM item_types WHERE name = (?) RETURNING id")) {

            statement.setString(1, itemModel.getName());

            result = statement.executeQuery().next();

        } catch (SQLException e) {
            System.out.println("!SQLException: " + e.getMessage());
        }
        return result;
    }


    // check by name whether this item exists in stock
    private boolean isExist(String name) {
        return read(name).getId() != -1;
    }

    enum queriesSQL {
        GET("SELECT id FROM item_types WHERE name = (?)"),
        DELETE("DELETE FROM item_types WHERE name = (?) RETURNING id"),
        ADD("INSERT INTO item_types (id, name) VALUES (DEFAULT, (?)) RETURNING id;"),
        UPDATE("UPDATE item_types SET name (?) WHERE id = (?) RETURNING id");

        String QUERY;

        queriesSQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
