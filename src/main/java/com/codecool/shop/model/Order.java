package com.codecool.shop.model;



import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class Order {
    private Integer id;
    private Date date;
    private User user;
    private Set<LineItem> lineItems = new LinkedHashSet<LineItem>();
    private HashMap<String, String> checkoutInfo = new HashMap<>();

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Set<LineItem> linetems) {
        this.lineItems = linetems;
    }

    public HashMap<String, String> getCheckoutInfo() { return checkoutInfo; }

    public void setCheckoutInfo(HashMap<String, String> checkoutInfo) { this.checkoutInfo = checkoutInfo; }

    public LineItem findLineItem(String productName) {
        return lineItems.stream().filter(t -> t.getProduct().getName().equals(productName)).findFirst().orElse(null);
    }

    public void removeLineItem(LineItem lineItem) {
        lineItems.remove(lineItem);
    }
}
