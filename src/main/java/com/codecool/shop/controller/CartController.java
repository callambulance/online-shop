package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.entities.OrderManager;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer orderId = Integer.parseInt(req.getParameter("orderId"));

        OrderManager orderManager = OrderManager.getInstance();
        Order order = orderManager.findByID(orderId);
        double totalOrderPrice = 0;

        if (order != null) {
            for (LineItem lineItem : order.getLineItems()) {
                totalOrderPrice += lineItem.getTotalPrice();
                totalOrderPrice = Math.round(totalOrderPrice * 100) / 100.00;
            }
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("userOrder", order);
        context.setVariable("totalOrderPrice", totalOrderPrice);

        engine.process("cart/cart.html", context, resp.getWriter());
    }
}
