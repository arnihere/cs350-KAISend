package com.example.cs350_kaisend;

public class Item {
    public String deadline, initDest, finalDest, name, type;
    public int price;
    public Item(String deadline, String initDest, String finalDest, String name, String type, int price){
        this.deadline = deadline;
        this.initDest = initDest;
        this.finalDest = finalDest;
        this.name = name;
        this.type = type;
        this.price = price;
    }
}
