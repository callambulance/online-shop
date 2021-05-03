package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
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

public class SupplierDaoDB implements SupplierDao {
    private DataSource dataSource;


    public SupplierDaoDB() throws IOException {
        try {
            this.dataSource = DatabaseManager.connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void add(Supplier supplier) {
//        supplier.setId(data.size() + 1);
//        data.add(supplier);
    }

    @Override
    public Supplier find(String name) {
//        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        Supplier supplier = null;
        String sql = "SELECT supplier_name, description FROM supplier WHERE supplier_name = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    supplier = new Supplier(rs.getString("supplier_name"),
                            rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplier;
    }

    @Override
    public void remove(int id) {
//        data.remove(find(id));
    }

    @Override
    public List<Supplier> getAll() {
        List<Supplier> data = new ArrayList<>();
        String sql = "SELECT supplier_name, description FROM supplier";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.add(new Supplier(rs.getString("supplier_name"), rs.getString("description")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
