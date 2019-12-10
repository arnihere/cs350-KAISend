package com.example.cs350_kaisend;

public class claimItem {
    private String requester, sender, itemName, senderUID, requsterUID, title, content;
    private Integer price;

    public claimItem(String requester, String sender, String itemName, Integer price, String senderUID, String requsterUID) {
        this.requester = requester;
        this.sender = sender;
        this.itemName = itemName;
        this.senderUID = senderUID;
        this.requsterUID = requsterUID;
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

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public String getRequsterUID() {
        return requsterUID;
    }

    public void setRequsterUID(String requsterUID) {
        this.requsterUID = requsterUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
