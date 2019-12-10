package com.example.cs350_kaisend;

public class Delivery {
    public boolean deliveryConfirm, paymentConfirm, valid;
    public int payment;
    public String requester, sender;
    public Item item;
    public Delivery(String requester, String sender, Item item, int payment){
        this.requester = requester;
        this.sender = sender;
        this.item = item;
        this.payment = payment;
        this.deliveryConfirm = false;
        this.paymentConfirm = false;
        this.valid = valid;
    }

}
