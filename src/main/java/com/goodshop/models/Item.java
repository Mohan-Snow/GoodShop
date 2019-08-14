package com.goodshop.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Item {

    private long id;
    private long customerId;

    private double price;
    private Timestamp saleDate;
    private StockItemType itemType;
}
