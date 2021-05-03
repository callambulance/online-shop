package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class OrderDaoDB implements OrderDao {
    public DataSource dataSource;
    float totalPrice = 0;


    public OrderDaoDB() throws IOException {
        try {
            this.dataSource = DatabaseManager.connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int add(Order order) throws SQLException {
        int returnedId = 0;
        ArrayList<String> items = new ArrayList();
        String sql = "INSERT INTO order_information (date, user_id, checkout_info, items, total_price, paid) VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, new Date(System.currentTimeMillis()));
            ps.setInt(2, order.getUser().getId());
            System.out.println(order.getCheckoutInfo().toString());
            ps.setString(3, order.getCheckoutInfo().toString());
            order.getLineItems().forEach(item -> items.add(item.getProduct().getName() + ": " + item.getQuantity()));
            System.out.println(String.valueOf(items));
            ps.setString(4, String.valueOf(items));
            order.getLineItems().forEach((p) -> {
                float price = p.getProduct().getDefaultPrice();
                int q = p.getQuantity();
                totalPrice += price * q;
            });
            ps.setFloat(5, totalPrice);
            ps.setBoolean(6, false);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                // Retrieve the auto generated key(s).
                returnedId = rs.getInt(1);
//                order.setId(returnedId);
            }
        }
        return returnedId;
    }

        @Override
    public Order find(int id) {
        return null;
    }

    @Override
    public void changePiedColumn(int orderIdDb) {
        String sql = "UPDATE order_information SET paid = ? WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setBoolean(1, true);
            ps.setInt(2, orderIdDb);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
