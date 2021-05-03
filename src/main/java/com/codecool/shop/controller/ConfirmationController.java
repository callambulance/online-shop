package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.entities.OrderManager;
import com.codecool.shop.model.Order;
import com.codecool.shop.util.EmailSender;
import org.json.simple.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


@WebServlet(urlPatterns = {"/confirmation"})
public class ConfirmationController extends HttpServlet {
    private String recipient;
    private float totalPrice;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        int orderId = Integer.parseInt(req.getParameter("orderId"));
        OrderManager orderManager = OrderManager.getInstance();
        Order order = orderManager.findByID(1);
        System.out.println(order.getCheckoutInfo().get("Email"));
        recipient = order.getCheckoutInfo().get("Email");

        order.getLineItems().forEach((p) ->  {
            float price = p.getProduct().getDefaultPrice();
            int q = p.getQuantity();
            totalPrice+= price*q;
        });

        saveOrderToJson(order);
        emailSender(order);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("order", order);
        context.setVariable("totalPrice", totalPrice);

        engine.process("confirmation/confirmation.html", context, resp.getWriter());
        }



    private void saveOrderToJson(Order order){
        JSONObject sampleObject = new JSONObject();
        sampleObject.put("id", order.getId());
        sampleObject.put("date", order.getDate());
        sampleObject.put("user", order.getUser());
        sampleObject.put("checkout information", order.getCheckoutInfo());
        order.getLineItems().forEach((i) -> {
            sampleObject.put(i.getProduct().getName(),i.getQuantity() + " x " + i.getProduct().getPrice());
        });

        try (FileWriter file = new FileWriter("sample_data/order.json")) {

            file.write(sampleObject.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void emailSender(Order order){
        String resultMessage = "";

        try {
            EmailSender.sendEmail(recipient, order);
            resultMessage = "The e-mail was sent successfully";
        } catch (Exception ex) {
            ex.printStackTrace();
            resultMessage = "There were an error: " + ex.getMessage();
        }finally {
            System.out.println(resultMessage);
        }


    }

}
