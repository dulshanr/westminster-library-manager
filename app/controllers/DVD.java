package controllers;

import java.util.ArrayList;

public class DVD extends LibraryItem {
    private ArrayList<String> languages=new ArrayList<>();
    private ArrayList<String> subtitles=new ArrayList<>();
    private ArrayList<String> actors=new ArrayList<>();

    private static DVD current_dvd;

    public DVD(int id, String title, Date pubDate,String sector) {
        super(id, title, pubDate,"DVD",sector);
        current_dvd = this;

    }

    @Override
    public String getItemType() {
        return "DVD";
    }

    public void AddItem(ArrayList<String> a1, String value) {
        a1.add(value);
    }


    public ArrayList<String> getLanguages() {
        return languages;
    }

    public ArrayList<String> getSubtitles() {
        return subtitles;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    @Override
    public String toString() {
        String temp = "ID: " + this.getId() + " ,Title: " + this.getTitle() + " ,Pub Date " + this.getPubDate().toString()+" Languages: ";

        for (int i=0;i<languages.size();i++) {
            temp += languages.get(i)+" ";
        }
        temp += ",Subtitles: ";
        for (int i=0;i<subtitles.size();i++) {
            temp += subtitles.get(i)+" ";
        }
        temp += ",Actors: ";
        for (int i=0;i<actors.size();i++) {
            temp += actors.get(i)+" ";
        }

        if (this.getCurrent()!=null) {
            temp += "Burrower: "+this.getCurrent().getName();
        }

        return temp;
    }

    public static DVD getCurrent_dvd() {
        return current_dvd;
    }
}