package com.project.pojo.entities;

import java.util.ArrayList;

public class Overview {
    private ArrayList<Integer> usersCount;
    private ArrayList<Integer> ordersCount;
    private ArrayList<Integer> productsCount;

    public ArrayList<Integer> getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(ArrayList<Integer> usersCount) {
        this.usersCount = usersCount;
    }

    public ArrayList<Integer> getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(ArrayList<Integer> ordersCount) {
        this.ordersCount = ordersCount;
    }

    public ArrayList<Integer> getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(ArrayList<Integer> productsCount) {
        this.productsCount = productsCount;
    }

    @Override
    public String toString() {
        return "Overview{" +
                "usersCount=" + usersCount +
                ", ordersCount=" + ordersCount +
                ", productsCount=" + productsCount +
                '}';
    }
}
