package com.codecool.shop.entities;

import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> ordersList = new ArrayList<>();
    private static OrderManager instance = null;

    private OrderManager() {
    }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public List<Order> getAllOrders() {
        return ordersList;
    }

    public void setOrdersList(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

    public void add(Order order) {
        ordersList.add(order);
    }

    public void remove(Order order) {ordersList.remove(order);}

    public Order findByUserID(Integer userId) {
        for (int i = 0; i < ordersList.size(); i++) {
            if (ordersList.get(i).getUser().getId() == (userId)) {
                return ordersList.get(i);
            }
        }
        return null;
    }

    public Order findByID(Integer userId) {
        for (int i = 0; i < ordersList.size(); i++) {
            if (ordersList.get(i).getId().equals(userId)) {
                return ordersList.get(i);
            }
        }
        return null;
    }
}
