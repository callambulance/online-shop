package com.codecool.shop.controller;

import com.codecool.shop.entities.OrderManager;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/edit-quantity"})
public class EditQuantityController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String referer = req.getHeader("Referer");
        String productName = req.getParameter("name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        User user = User.getInstance();
        OrderManager orderManager = OrderManager.getInstance();
        Order order = orderManager.findByUserID(user.getId());
        LineItem existingLineItem = order.findLineItem(productName);

        if (quantity == 0) {
            order.removeLineItem(existingLineItem);
            if (order.getLineItems().size() == 0) {
                orderManager.remove(order);
            }
        } else {
            existingLineItem.setQuantity(quantity);
            existingLineItem.setTotalPrice((float) (Math.round((quantity *
                    existingLineItem.getProduct().getDefaultPrice()) * 100) / 100.00));
        }

        resp.sendRedirect(referer);
    }
}
