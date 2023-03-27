package org.example;

public class Order {
    @Prim
    private int id;
    private int orderSerialNumber;
    private int clientID;
    private int productId;
    private int productQuantity;
    private Double sumOfOrder;

    public Order(int orderSerialNumber, int clientID, Product product, int productId, int productQuantity) {
        this.orderSerialNumber = orderSerialNumber;
        this.clientID = clientID;
        this.productId = productId;
        this.productQuantity = productQuantity;
        this.sumOfOrder = product.getPrice() * productQuantity;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public int getOrderSerialNumber() {
        return orderSerialNumber;
    }

    public int getClientID() {
        return clientID;
    }

    public int getProductId() {
        return productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }
    public double getSumOfOrder() {
        return sumOfOrder;
    }
    public void setOrderSerialNumber(int orderSerialNumber) {
        this.orderSerialNumber = orderSerialNumber;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderSerialNumber=" + orderSerialNumber +
                ", clientID=" + clientID +
                ", productId=" + productId +
                ", productQuantity=" + productQuantity +
                ", sumOfOrder=" + sumOfOrder +
                '}';
    }
}
