package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoDB;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.entities.OrderManager;
import com.codecool.shop.model.User;
import com.codecool.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@WebServlet(urlPatterns = {"/order"})
public class OrderController extends HttpServlet {
    private Product product;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String referer = req.getHeader("Referer");
        String productName = req.getParameter("name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        ProductDao productDataStore = new ProductDaoDB();
        OrderManager orderManager = OrderManager.getInstance();
        User user = User.getInstance();

        product = productDataStore.getBy(productName);

        Order order = orderManager.findByUserID(user.getId());

        if (order == null) {
            order = new Order();
            order.setId(1);
            order.setDate(Calendar.getInstance().getTime());
            order.setUser(user);
            orderManager.add(order);
        }

        LineItem existingLineItem = order.findLineItem(productName);
        if (existingLineItem != null) {
            existingLineItem.setQuantity(existingLineItem.getQuantity() + quantity);
            existingLineItem.setTotalPrice(product.getDefaultPrice() * existingLineItem.getQuantity());
        } else {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(quantity);
            lineItem.setTotalPrice(product.getDefaultPrice() * quantity);
            order.getLineItems().add(lineItem);
            }

        resp.sendRedirect(referer);
    }
}
