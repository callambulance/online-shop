package com.codecool.shop.util;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.ProductDaoDB;
import com.codecool.shop.dao.implementation.SupplierDaoDB;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.io.IOException;
import java.util.List;

public class ProductFilter {

    public static List<Product> filteringProducts(String categoryName, String supplierName) throws IOException {
        ProductDao productDao = new ProductDaoDB();
        ProductCategoryDao productCategoryDao = new ProductCategoryDaoDB();
        SupplierDao supplierDao = new SupplierDaoDB();

        List<Product> products;

        if(categoryName==null){
            categoryName = "vegetable";
        }

        ProductCategory category = productCategoryDao.find(categoryName);

        Supplier supplier = supplierDao.find(supplierName);
        if(supplier==null) {
            products = productDao.getBy(category);
        }else if(category==null && supplier!=null){
            products = productDao.getBy(supplier);
        }else{
            products = productDao.getByCategoryAndSupplier(category, supplier);
        }
        return products;
    }
}
