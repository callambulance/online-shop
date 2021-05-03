package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

import java.sql.SQLException;

public interface OrderDao {
    int add(Order checkoutInfo) throws SQLException;
    Order find (int id);
    void changePiedColumn(int orderIdDb);
}
