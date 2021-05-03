package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoDB;
import com.codecool.shop.entities.OrderManager;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.validator.routines.EmailValidator;


@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController extends HttpServlet {
    private HashMap<String, String> checkoutInfo;
    private int orderId;
    int orderIdDb;
    private OrderDao orderDb = new OrderDaoDB();

    public CheckoutController() throws IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        orderId = Integer.parseInt(req.getParameter("orderId"));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

        WebContext context = new WebContext(req, resp, req.getServletContext());

        engine.process("checkout/checkout.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        String msg = checkingInfo(request);
        if (!msg.equals("true")){
            request.setAttribute("error", msg);
            RequestDispatcher rd=request.getRequestDispatcher("WEB-INF/checkout.jsp");
            rd.include(request, response);

        }else {
            OrderManager orderManager = OrderManager.getInstance();
            User user = User.getInstance();
            Order order = orderManager.findByUserID(user.getId());
            order.setCheckoutInfo(checkoutInfo);
            try {
                orderIdDb = orderDb.add(order);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            HttpSession session = request.getSession();
            session.setAttribute("orderId", orderIdDb);
            response.sendRedirect("/payment");
        }

    }


    private String checkingInfo(HttpServletRequest request){


         checkoutInfo = new HashMap<>(){{
            put("First name", request.getParameter("firstname"));
            put("Last name", request.getParameter("lastname"));
            put("Email", request.getParameter("email"));
            put("Phone number", request.getParameter("phone_number"));

            put("Billing address", request.getParameter("billing_address"));
            put("Billing country", request.getParameter("billing_country"));
            put("Billing zipcode", request.getParameter("billing_zipcode"));
            put("Billing city", request.getParameter("billing_city"));

            put("Shipping address", request.getParameter("shipping_address"));
            put("Shipping country", request.getParameter("shipping_country"));
            put("Shipping zipcode", request.getParameter("shipping_zipcode"));
            put("Shipping city", request.getParameter("shipping_city"));

        }};

        for (Map.Entry<String, String> entry : checkoutInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value.length()<1){
                System.out.println("false_length");
                return "Fill in all the fields";
            }

            if (key.equals("Email")) {
                String email = value;
                System.out.println(value);
                if (!EmailValidator.getInstance().isValid(email)) {
                    System.out.println("false_email");
                    return "Wrong email";
                }}

            if(key.equals("Phone number")){
                if (!value.matches("[0-9]+") || value.length() < 6) {
                    System.out.println("false_number");
                    return "Wrong phone number";
                }}
        }

        return "true";
    }

}

