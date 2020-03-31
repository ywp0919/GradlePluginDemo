package com.wepon.doccomplie;

public class Entity {

    public String author;
    public String time;
    public String name;


    @Override
    public String toString() {
        return "Entity{" +
                "author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}