package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoDB implements ProductCategoryDao {
    public DataSource dataSource;


    public ProductCategoryDaoDB() {
        try {
            this.dataSource = DatabaseManager.connect();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void add(ProductCategory category) {
//        category.setId(data.size() + 1);
//        data.add(category);
    }

    @Override
    public ProductCategory find(String name) {
        ProductCategory category = null;
//        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        String sql = "SELECT category_name, department, description FROM category " +
                "WHERE category_name = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    category = new ProductCategory(rs.getString("category_name"),
                            rs.getString("department"),
                            rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public void remove(int id) {
//        data.remove(find(id));
    }

    @Override
    public List<ProductCategory> getAll() {
        List<ProductCategory> data = new ArrayList<>();
        String sql = "SELECT category_name, department, description FROM category";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.add(new ProductCategory(rs.getString("category_name"),
                            rs.getString("department"),
                                    rs.getString("description")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
