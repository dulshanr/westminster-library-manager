package controllers;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class WestminsterLibraryManager implements LibraryManager {

    private ArrayList<Book> bookList;
    private ArrayList<DVD> dvdList;
    private ArrayList<Reader> readerList;

    private ArrayList<LibraryItem> libraryItems;


    private ArrayList<Book> dueBooks;
    private ArrayList<DVD> dueDVDS;

    private ArrayList<LibraryItem> dueItems;


    private static final int MAXBOOKS = 100;
    private static final int MAXDVD = 50;

    public WestminsterLibraryManager() {
        bookList = new ArrayList<>();
        dvdList = new ArrayList<>();
        readerList = new ArrayList<>();

        dueBooks = new ArrayList<>();
        dueDVDS = new ArrayList<>();

        dueItems = new ArrayList<>();

        libraryItems = new ArrayList<>();

    }

    //add a new Book
    @Override
    public void addnewItem(int id, String title, int day, int month, int year, int no_of_pages, String publisher, String sector) {

        if (bookList.size() >= MAXBOOKS) {
            System.out.println("Max Number of books reached");
        } else {
            Book currentBook = new Book(id, title, new Date(day, month, year), no_of_pages, publisher, sector);
            bookList.add(currentBook);
            System.out.println("Book Inserted Successfully");
            System.out.println("Remaining slots: " + (MAXBOOKS - bookList.size()));


        }

    }

    //add a new DVD

    public void addnewItem(int id, String title, int day, int month, int year, String sector) {

        if (dvdList.size() >= MAXDVD) {
            System.out.println("Max Number of DVDS reached");
        } else {
            dvdList.add(new DVD(id, title, new Date(day, month, year), sector));

            System.out.println("DVD Inserted Successfully");
            System.out.println("Remaining slots: " + (MAXDVD - dvdList.size()));
        }

    }

    //add a new Reader
    public void addnewReader(int id, String name, int mobile, String email) {
        readerList.add(new Reader(id, name, mobile, email));
        System.out.println("DVD Inserted Successfully");
    }

//searchbook

    public int searchBook(ArrayList<Book> books, int id) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
//searchDVD
    }

    public int searchDVD(ArrayList<DVD> dvds, int id) {
        for (int i = 0; i < dvds.size(); i++) {
            if (dvds.get(i).getId() == id) {
                return i;
            }
        }
        return -1;

    }

    //search for Reader
    public int getReader(ArrayList<Reader> readers, int id) {

        for (int i = 0; i < readers.size(); i++) {
            if (readers.get(i).getId() == id) {
                return i;
            }
        }
        return -1;

    }


//delete Item

    @Override
    public void deleteItem(int id) {

        int x = searchBook(bookList, id);
        int y = searchDVD(dvdList, id);

        DataBaseConnect db6 = new DataBaseConnect();

        if (x != -1) {
            if (bookList.get(x).getCurrent() != null) {
                bookList.get(x).getCurrent().setItem(null);
            }
            bookList.remove(x);
            System.out.println("Book removed");
            db6.ReturnBook(id);
            db6.DeleteBook(id);
            return;
        } else if (y != -1) {
            if (dvdList.get(y).getCurrent() != null) {
                dvdList.get(y).getCurrent().setItem(null);
            }


            dvdList.remove(y);
            System.out.println("DVD removed");
            db6.ReturnDVD(id);
            db6.DeleteDVD(id);
            return;
        }

    }


    //Borrow-Item
    @Override
    public void BorrowItem(LibraryItem l1, Reader r1, int day, int month, int year) {
        int[] normal_arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31};
        int[] leap_arr = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 31};
        int[] month_arr;

        if (year % 4 == 0) {
            month_arr = leap_arr;
        } else {
            month_arr = normal_arr;
        }


        l1.setCurrent(r1);
        r1.setItem(l1);
        l1.setBurrowedDate(new Date(day, month, year));

        if (l1.getItemType().equals("Book")) {


            if (day + 7 > month_arr[month - 1]) {
                month++;
                day = 7 - (month_arr[month - 1] - day);
                if (month > 12) {
                    year++;
                    month = 1;
                }
                l1.setDueDate(new Date(day, month, year));
            } else {
                l1.setDueDate(new Date(day + 7, month, year));
            }
        } else {
            if (day + 3 > month_arr[month - 1]) {
                month++;
                day = 3 - (month_arr[month - 1] - day);
                if (month > 12) {
                    year++;
                    month = 1;
                }
                l1.setDueDate(new Date(day, month, year));
            } else {
                l1.setDueDate(new Date(day + 3, month, year));
            }
        }


    }

    //returnItem
    @Override
    public String returnItem(LibraryItem l1, Date d1, Date d2) {


        l1.getCurrent().setItem(null);
        l1.setCurrent(null);
        l1.setBurrowedDate(null);
        l1.setDueDate(null);

        l1.setDueDays(0);
        l1.setDueAmount(0);


        if (Date.isHighest(d1, d2)) {

            int total_hours = calculateBorrowedHours(d1, d2);
            double tot_fee = calculateTotalPrice(total_hours);


            System.out.println("The Total hours late is " + total_hours + " Fees: " + tot_fee);

            return ("The Total hours late is " + total_hours + ". Which correspond to " + total_hours / 24.0 + " days , Fees : " + tot_fee) + " Euros";

        }

        return ("Item Returned on time");

    }

    //check for the Fee when Item is returned
    public void checkFee(LibraryItem l1, int day, int month, int year) {

        Date returnedDate = new Date(day, month, year);
        double price = -1;
        Date d1 = returnedDate;
        Date d2 = l1.getDueDate();

        if (Date.isHighest(d1, d2)) {
            int hours = calculateBorrowedHours(d1, d2);
            price = calculateTotalPrice(hours);
            l1.setDueAmount(price);
            l1.setDueDays(hours / 24.0);
        }


    }
    //calculate the total price when number of hours supplied

    public double calculateTotalPrice(int total_hours) {

        double tot_fee;

        if (total_hours > (24 * 3)) {
            tot_fee = (24 * 3 * 0.2) + ((total_hours - (24 * 3)) * 0.5);
        } else {
            tot_fee = total_hours * 0.2;
        }


        return tot_fee;
    }


    //logic to calculate the number of hours between given dates
    public int calculateBorrowedHours(Date d1, Date d2) {

        int[] normal_arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int days = 0;

        int year = d1.getYear() - d2.getYear();

        if (d1.getMonth() != d2.getMonth()) {


            if (d1.getMonth() - d2.getMonth() >= 0) {
                days = (normal_arr[d2.getMonth() - 1] - d2.getDay()) + (d1.getDay());

                int temp_number = d2.getMonth() + 1;

                while (temp_number < d1.getMonth()) {
                    days += normal_arr[temp_number - 1];
                    temp_number++;
                }
            } else {
                days = (normal_arr[d2.getMonth() - 1] - d2.getDay()) + (d1.getDay());

                int temp_number = d2.getMonth() + 1;

                while (temp_number <= 12) {
                    days += normal_arr[temp_number - 1];
                    temp_number++;
                }

                temp_number = 0;

                while (temp_number < d1.getMonth()) {
                    days += normal_arr[temp_number - 1];
                }
            }

        } else {
            days = d1.getDay() - d2.getDay();
        }
        int total_hours = (days * 24) + (365 * year);

        return total_hours;
    }

    @Override
    public void generateReport(Date d1) {
        for (int i = 0; i < bookList.size(); i++) {
            if (Date.isHighest(d1, bookList.get(i).getDueDate())) {
                System.out.println(bookList.get(i).getTitle());
            }
        }
        for (int i = 0; i < dvdList.size(); i++) {
            if (Date.isHighest(d1, dvdList.get(i).getDueDate())) {
                System.out.println(dvdList.get(i).getTitle());
            }
        }
    }

    @Override
    public void displayItems() {
        for (int i = 0; i < bookList.size(); i++) {
            System.out.println("ID: " + bookList.get(i).getId() + " type: Book Title: " + bookList.get(i).getTitle());

        }

        for (int i = 0; i < dvdList.size(); i++) {
            System.out.println("ID: " + dvdList.get(i).getId() + " type: DVD Title: " + dvdList.get(i).getTitle());

        }

    }

    //logic to search the book
    public Book searchGetBook(int id) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId() == id) {
                return bookList.get(i);
            }
        }
        return null;
    }

    //logic to search DVD
    public DVD searchGetDVD(int id) {
        for (int i = 0; i < dvdList.size(); i++) {
            if (dvdList.get(i).getId() == id) {
                return dvdList.get(i);
            }
        }
        return null;
    }


    public Reader searchGetReader(int id) {
        for (int i = 0; i < readerList.size(); i++) {
            if (readerList.get(i).getId() == id) {
                return readerList.get(i);
            }
        }
        return null;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public ArrayList<DVD> getDvdList() {
        return dvdList;
    }

    public ArrayList<Reader> getReaderList() {
        return readerList;
    }

    public static int getMAXBOOKS() {
        return MAXBOOKS;
    }

    public static int getMAXDVD() {
        return MAXDVD;
    }

    public ArrayList<Book> getDueBooks() {
        return dueBooks;
    }

    public ArrayList<DVD> getDueDVDS() {
        return dueDVDS;
    }

    public ArrayList<LibraryItem> getDueItems() {
        return dueItems;
    }

    public void setDueItems() {
        this.dueItems = new ArrayList<>();

    }

    public ArrayList<LibraryItem> getLibraryItems() {
        return libraryItems;
    }

    public void setLibraryItems(ArrayList<LibraryItem> libraryItems) {
        this.libraryItems = libraryItems;
    }
}