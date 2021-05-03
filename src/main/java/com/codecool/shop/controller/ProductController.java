package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoDB;
import com.codecool.shop.dao.implementation.ProductDaoDB;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoDB;
import com.codecool.shop.model.Order;
import com.codecool.shop.entities.OrderManager;
import com.codecool.shop.model.User;
import com.codecool.shop.model.Product;
import com.codecool.shop.util.ProductFilter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    private List<Product> productList;
    private String category;
    private String supplier;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductCategoryDao productCategoryDataStore = new ProductCategoryDaoDB();
        ProductDao productDataStore = new ProductDaoDB();
        SupplierDao supplierCategoryDataStore = new SupplierDaoDB();
        OrderManager orderManager = OrderManager.getInstance();
        User user = User.getInstance();

        Order order = orderManager.findByUserID(user.getId());

        category = req.getParameter("category");
        supplier = req.getParameter("supplier");
        if(supplier==null){
            supplier="all";
        }

        productList = ProductFilter.filteringProducts(category, supplier);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("userOrder", order);
         Map<String, Object> params = new HashMap<>();
         params.put("categories", productCategoryDataStore.getAll());
         params.put("suppliers", supplierCategoryDataStore.getAll());
         params.put("products", productList);
         context.setVariables(params);

        engine.process("product/index.html", context, resp.getWriter());
    }


}
