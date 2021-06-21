package controllers;

public interface LibraryManager {
    public abstract void addnewItem(int isbn, String title, int day, int month, int year, int no_of_pages, String publisher,String sector);

    public abstract void deleteItem(int id);


    public abstract void BorrowItem(LibraryItem l1, Reader r1, int day, int month, int year);

    public abstract String returnItem(LibraryItem l1,Date d1,Date d2);

    public abstract void generateReport(Date d1);

    public abstract void displayItems();
}
