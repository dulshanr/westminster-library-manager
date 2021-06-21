package controllers;

import java.util.ArrayList;

public class Book extends LibraryItem {
    private static int maxBooks = 100;

    private ArrayList<String> authors = new ArrayList<>();
    private int noPages;
    private String publisher;
    private static Book current_book;


    public Book(int id, String title, Date pub_date, int noPages, String publisher,String sector) {
        super(id,title,pub_date,"Book",sector);

        this.noPages = noPages;
        this.publisher = publisher;

        current_book=this;
    }

    public int getNoPages() {
        return noPages;
    }

    public void setNoPages(int noPages) {
        this.noPages = noPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void AddItem(ArrayList<String> a1, String value) {
        a1.add(value);
    }

    @Override
    public String toString() {
        String temp = "ID: " + this.getId() + " ,Title: " + this.getTitle() + " ,Pub Date " + this.getPubDate().toString() + " ,No of pages: " + this.noPages + " ,Publisher: " + this.publisher+" ,Authors: ";
        for (int i=0;i<this.authors.size();i++) {
            temp += authors.get(i)+" ";
        }

        if (this.getCurrent()!=null) {
            temp += "Burrower: "+this.getCurrent().getName();
        }

        return temp;
    }

    @Override
    public String getItemType() {
        return "Book";
    }

    public static Book getCurrent_book() {
        return current_book;
    }
}
