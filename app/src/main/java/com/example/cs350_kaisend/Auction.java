package com.example.cs350_kaisend;

public class Auction {
    String name, initDest, finalDest, price, fee, deadline, description,id, owner;
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
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setOwner(String owner){
        this.owner = owner;
    }
    public String getOwner(){
        return this.owner;
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


