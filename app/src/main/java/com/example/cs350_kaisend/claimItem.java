package com.example.cs350_kaisend;

public class claimItem {
    private String requester, sender, itemName;
    private Integer price;

    public claimItem(String requester, String sender, String itemName, Integer price) {
        this.requester = requester;
        this.sender = sender;
        this.itemName = itemName;
        this.price = price;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
