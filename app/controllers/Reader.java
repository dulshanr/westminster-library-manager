package controllers;

import java.util.ArrayList;

public class Reader {
    private int id;
    private String Name;
    private int mobile;
    private String email;
    private transient LibraryItem item;
    private static Reader current_reader;

    public Reader(int id, String name, int mobile, String email) {
        this.id = id;
        Name = name;
        this.mobile = mobile;
        this.email = email;
        current_reader = this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + " Name: " + this.getName() + " Mobile: " + this.getMobile() + " Email: " + this.email;
    }

    public static Reader getCurrent_reader() {
        return current_reader;
    }

    public LibraryItem getItem() {
        return item;
    }

    public void setItem(LibraryItem item) {
        this.item = item;
    }
}
