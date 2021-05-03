package com.codecool.shop.util;
import com.codecool.shop.model.Order;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class EmailSender {
    public static boolean sendEmail(String recipient, Order order) {
        String emailText = "Your order number " + order.getId() + " was paid successfully and going to be shipped within 2-3 days.";
        String host = "smtp.ukr.net";
        String userName = "code-cool-shop@ukr.net";
        String password = "3vJ5cqir5UB59elQ";
        String port = "465";

        try
        {
            java.util.Properties props = null;
            props = System.getProperties();
            props.put("mail.smtp.user", userName);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.enable", "true");

            if(!"".equals(port))
            {
                props.put("mail.smtp.port", port);
                props.put("mail.smtp.socketFactory.port", port);
            }


            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName));
            msg.setSubject("Codecool shop order");
            msg.setText(emailText);
            msg.setSentDate(new Date());
            msg.setHeader("content-Type", "text/html");
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(host, userName, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

            return true;
        }
        catch (Exception mex)
        {
            mex.printStackTrace();
            return false;
        }
    }
    }


