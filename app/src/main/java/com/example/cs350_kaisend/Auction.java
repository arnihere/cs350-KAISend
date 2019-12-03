package com.example.cs350_kaisend;

public class Auction {
    String name, initDest, finalDest, price, fee, deadline, description;
    boolean active;

    public Auction(){}
    public Auction(String name, String initDest, String finalDest, String price, String fee, String deadline, String description){
        this.name = name;
        this.initDest = initDest;
        this.finalDest = finalDest;
        this.price = price;
        this.fee = fee;
        this.deadline = deadline;
        this.description = description;
        this.active = true;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getDescription() {
        return description;
    }

    public String getFee() {
        return fee;
    }

    public String getFinalDest() {
        return finalDest;
    }

    public String getInitDest() {
        return initDest;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
    public boolean isActive(){
        return active;
    }
}


