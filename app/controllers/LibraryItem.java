package controllers;


public abstract class LibraryItem implements Comparable<LibraryItem> {

    private int id;
    private String title;
    private Date pubDate;
    private Reader current;
    private Date burrowedDate;
    private Date dueDate;
    private double dueDays;
    private double dueAmount;
    private String sector;


    private String type;

    public LibraryItem(int id, String title, Date pubDate,String type,String sector) {
        this.id = id;
        this.title = title;
        this.pubDate = pubDate;
        this.type = type;
        this.sector = sector;
    }

    @Override
    public int compareTo(LibraryItem o) {
        return (int)(o.getDueDays()-this.getDueDays());
    }

    public abstract String getItemType();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Reader getCurrent() {
        return current;
    }

    public void setCurrent(Reader current) {
        this.current = current;
    }

    public Date getBurrowedDate() {
        return burrowedDate;
    }

    public void setBurrowedDate(Date burrowedDate) {
        this.burrowedDate = burrowedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getDueDays() {
        return dueDays;
    }

    public void setDueDays(double dueDays) {
        this.dueDays = dueDays;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    //
//    public int id;
//    public String name;
//
//    public LibraryItem(int id,String name){
//        this.id = id;
//        this.name = name;
//    }
//
//    public static ArrayList<LibraryItem> returnlist() {
//        ArrayList<LibraryItem> list_1 = new ArrayList<>();
//
//        list_1.add(new LibraryItem(4, "Book1"));
//        list_1.add(new LibraryItem(10, "Book2"));
//        list_1.add(new LibraryItem(10, "Book10"));
//
//        return list_1;
//
//    }



}
