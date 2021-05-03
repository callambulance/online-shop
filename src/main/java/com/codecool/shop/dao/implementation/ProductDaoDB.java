package com.codecool.shop.dao.implementation;


import com.codecool.shop.dao.ProductDao;
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

public class ProductDaoDB implements ProductDao {
    private DataSource dataSource;
    private static ProductDaoDB instance = null;


    public ProductDaoDB(){
        try {
            this.dataSource = DatabaseManager.connect();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void add(Product product) {
//        product.setId(data.size() + 1);
//        data.add(product);
    }

    @Override
    public Product find(int id) {
//        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        return null;
    }

    @Override
    public void remove(int id) {
//        data.remove(find(id));
    }

    @Override
    public List<Product> getAll() {
        List<Product> data = new ArrayList<>();
        String sql = "SELECT product.id as prod_id, name,product.description as description, price, category, supplier, supplier_name, " +
                "s.description as sup_description, category_name, department, c.description as cat_description" +
                " FROM product INNER JOIN category c on c.category_name = product.category " +
                "INNER JOIN supplier s on s.supplier_name = product.supplier";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             data = createProductList(ps);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        List<Product> data = new ArrayList<>();
        String sql = "SELECT product.id as prod_id, name,product.description as description, price, category, supplier, supplier_name, " +
                "s.description as sup_description, category_name, department, c.description as cat_description" +
                " FROM product INNER JOIN category c on c.category_name = product.category " +
                "INNER JOIN supplier s on s.supplier_name = product.supplier WHERE supplier = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, supplier.getName());
            data = createProductList(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public Product getBy(String productName) {
        List<Product> data = new ArrayList<>();
        String sql = "SELECT product.id as prod_id, name,product.description as description, price, category, supplier, supplier_name, " +
                "s.description as sup_description, category_name, department, c.description as cat_description" +
                " FROM product INNER JOIN category c on c.category_name = product.category " +
                "INNER JOIN supplier s on s.supplier_name = product.supplier WHERE name = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, productName);
            data = createProductList(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data.get(0);
    }

        @Override
        public List<Product> getBy (ProductCategory productCategory){
//        return data.stream().filter(t -> t.getProductCategory().equals(productCategory)).collect(Collectors.toList());
            List<Product> data = new ArrayList<>();
            String sql = "SELECT product.id as prod_id, name,product.description as description, price, category, supplier, supplier_name, " +
                "s.description as sup_description, category_name, department, c.description as cat_description" +
                " FROM product INNER JOIN category c on c.category_name = product.category " +
                "INNER JOIN supplier s on s.supplier_name = product.supplier WHERE category_name = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, productCategory.getName());
            data = createProductList(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public List<Product> getByCategoryAndSupplier(ProductCategory productCategory, Supplier supplier) {
        List<Product> data = new ArrayList<>();
        String sql = "SELECT product.id as prod_id, name,product.description as description, price, category, supplier, supplier_name, " +
                "s.description as sup_description, category_name, department, c.description as cat_description" +
                " FROM product INNER JOIN category c on c.category_name = product.category " +
                "INNER JOIN supplier s on s.supplier_name = product.supplier WHERE category_name = ? AND supplier_name = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, productCategory.getName());
            System.out.println( supplier.getName());
            ps.setString(2, supplier.getName());
            data = createProductList(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public List<Product> createProductList(PreparedStatement ps){
        List<Product> data = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            Product product;
            while (rs.next()) {
                data.add(product = new Product(rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getString("description"),
                        new ProductCategory(rs.getString("category_name"),
                                rs.getString("department"), rs.getString("cat_description")),
                        new Supplier(rs.getString("supplier_name"), rs.getString("sup_description"))
                ));
                product.setId(rs.getInt("prod_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

}
