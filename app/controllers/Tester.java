package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import play.libs.Json;
import play.mvc.*;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collections;


class StringMaker {
    private String content;

    StringMaker(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

public class Tester extends Controller {

    private static WestminsterLibraryManager lb1 = new WestminsterLibraryManager();
    private static boolean started = false;

    public Result appSummary() {
        JsonNode jsonNode = Json.toJson(new StringMaker("Java Play Angular Seed"));
        return ok(jsonNode).as("application/json");
    }

    public Result postTest() {
        JsonNode jsonNode = Json.toJson(new StringMaker("Post Request Test => Data Sending Success"));
        return ok(jsonNode).as("application/json");
    }

    public Result postTest2() {
        JsonNode jsonNode = Json.toJson(new StringMaker("Receiving data from back end"));
        return ok(jsonNode).as("application/json");
    }


    public Result addList() {
        if (!started) {
            started = true;


            DataBaseConnect d1 = new DataBaseConnect();
            d1.getBookList();
            d1.getDVDList();
            d1.getReader();
            d1.getBorrowedData();


            for (int i = 0; i < lb1.getBookList().size(); i++) {
                System.out.println("Loop1");
                for (int j = 0; j < lb1.getBookList().get(i).getAuthors().size(); j++) {
                    System.out.println(lb1.getBookList().get(i).getAuthors().get(j));
                    System.out.println("Loop2");
                }
            }

        }


        JsonNode jsonNode = Json.toJson(new StringMaker("Receiving data from back end"));
        return ok(jsonNode).as("application/json");
    }


    public Result postFormData() {
        JsonNode json = request().body().asJson();

        System.out.println("Name " + json.get("name").asText());
        return ok(json).as("application/json");
    }

    //add item logic
    public Result AddItem() {
        JsonNode json = request().body().asJson();
        int id;
        //getting form data into variables
        String title = json.get("title").asText();
        String sector = json.get("sector").asText();
        String author1 = json.get("author").asText();
        String[] author = author1.split(",");
        int noPages;
        String publisher = json.get("publisher").asText();
        int day;
        int month;
        int year;

        String[] dateSeperated;
        //splitting the date to day,month,year
        dateSeperated = json.get("date").asText().split("-");
//check whether integer starts with o as it will be an octal number
        if (dateSeperated[1].indexOf("0") == 0) {
            month = Integer.parseInt(dateSeperated[1].substring(1));
        } else {
            month = Integer.parseInt(dateSeperated[1]);
        }

        if (dateSeperated[2].indexOf("0") == 0) {
            day = Integer.parseInt(dateSeperated[1].substring(1));
        } else {
            day = Integer.parseInt(dateSeperated[1]);
        }
        year = Integer.parseInt(dateSeperated[0]);
//connecting to the database
        DataBaseConnect db2 = new DataBaseConnect();


        try {
            noPages = Integer.parseInt(json.get("pages").asText());
            id = Integer.parseInt(json.get("id").asText());
            lb1.addnewItem(id, title, day, month, year, noPages, publisher, sector);
            db2.insertBook(id, title, day, month, year, noPages, publisher, sector, author1);

        } catch (Exception e) {
            System.out.println("Error");
        }
//Adding authors
        for (int i = 0; i < author.length; i++) {
            Book.getCurrent_book().AddItem(Book.getCurrent_book().getAuthors(), author[i]);
        }

        JsonNode jsonNode12 = Json.toJson(new StringMaker("Receiving data from back end"));
        return ok(jsonNode12).as("application/json");
    }

    //logic to add a DVD
    public Result AddItem_2() {
        JsonNode json = request().body().asJson();
        int id;
        //getting form data to variables
        String title = json.get("title").asText();
        String sector = json.get("sector").asText();

        String languages1 = json.get("language").asText();
        String subtitles1 = json.get("subtitles").asText();
        String actors1 = json.get("actors").asText();


//splitting multiple values into single ones
        String[] languages = languages1.split(",");
        String[] subtitles = subtitles1.split(",");
        String[] actors = actors1.split(",");
        int day;
        int month;
        int year;

        String[] dateSeperated;

        dateSeperated = json.get("date").asText().split("-");

        if (dateSeperated[1].indexOf("0") == 0) {
            month = Integer.parseInt(dateSeperated[1].substring(1));
        } else {
            month = Integer.parseInt(dateSeperated[1]);
        }

        if (dateSeperated[2].indexOf("0") == 0) {
            day = Integer.parseInt(dateSeperated[1].substring(1));
        } else {
            day = Integer.parseInt(dateSeperated[1]);
        }
        year = Integer.parseInt(dateSeperated[0]);
//adding the data to database
        DataBaseConnect db3 = new DataBaseConnect();
        try {
            id = Integer.parseInt(json.get("id").asText());
            lb1.addnewItem(id, title, day, month, year, sector);
//adding dvd
            db3.insertDVD(id, title, day, month, year, sector, actors1, languages1, subtitles1);
//adding if multiple languages exist
            for (int i = 0; i < languages.length; i++) {
                DVD.getCurrent_dvd().AddItem(DVD.getCurrent_dvd().getLanguages(), languages[i]);
            }
            //adding if multiples subtitles exist
            for (int i = 0; i < subtitles.length; i++) {
                DVD.getCurrent_dvd().AddItem(DVD.getCurrent_dvd().getSubtitles(), subtitles[i]);
            }
            //adding if multiple actors exist
            for (int i = 0; i < actors.length; i++) {
                DVD.getCurrent_dvd().AddItem(DVD.getCurrent_dvd().getActors(), actors[i]);
            }


        } catch (Exception e) {
            System.out.println("Error");
        }

        for (int i = 0; i < lb1.getDvdList().size(); i++) {
            System.out.println(lb1.getDvdList().get(i));
        }
//returning string as json
        JsonNode jsonNode12 = Json.toJson(new StringMaker("Receiving data from back end"));
        return ok(jsonNode12).as("application/json");
    }

    //delete item
    public Result deleteItem() {
        JsonNode json = request().body().asJson();
        lb1.deleteItem(Integer.parseInt(json.get("id").asText()));

        return ok(json).as("application/json");
    }

    //returnItem
    public Result returnItem() {
        JsonNode json = request().body().asJson();
        String details = "";
        int tempItemPosition = (Integer.parseInt(json.get("id").asText()));

        String[] dateSeperated;

        dateSeperated = json.get("date").asText().split("/");
        int day = Integer.parseInt(dateSeperated[0]);
        int month = Integer.parseInt(dateSeperated[1]);
        int year = Integer.parseInt(dateSeperated[2]);


        int correctPosition_book = lb1.searchBook(lb1.getBookList(), tempItemPosition);
        int correctPosition_dvd = lb1.searchDVD(lb1.getDvdList(), tempItemPosition);
        DataBaseConnect db5 = new DataBaseConnect();

        if (correctPosition_book != -1) {

            int hoursBorrowed=lb1.calculateBorrowedHours(new Date(day,month,year),lb1.getBookList().get(correctPosition_book).getBurrowedDate());
            int borrowed_days=Math.round((hoursBorrowed/24.0F));
            db5.InsertBookData(lb1.getBookList().get(correctPosition_book).getId(),borrowed_days);

            details = lb1.returnItem(lb1.getBookList().get(correctPosition_book), new Date(day, month, year), lb1.getBookList().get(correctPosition_book).getDueDate());

            db5.ReturnBook(tempItemPosition);



        } else if (correctPosition_dvd != -1) {

            int hoursBorrowed=lb1.calculateBorrowedHours(new Date(day,month,year),lb1.getDvdList().get(correctPosition_dvd).getBurrowedDate());
            int borrowed_days=Math.round((hoursBorrowed/24.0F));
            db5.InsertBookData(lb1.getDvdList().get(correctPosition_dvd).getId(),borrowed_days);


            details = lb1.returnItem(lb1.getDvdList().get(correctPosition_dvd), new Date(day, month, year), lb1.getDvdList().get(correctPosition_dvd).getDueDate());
            db5.ReturnDVD(tempItemPosition);

        } else {
            details = "Error Returning Item";
            System.out.println("Error Returning Item");
        }
        JsonNode jsonNode = Json.toJson(new StringMaker(details));
        return ok(jsonNode).as("application/json");

    }

    //getting both og array lists into a single one with intended polymorphism
    public Result getBookList() {

        ArrayList<LibraryItem> libItems = new ArrayList<>();

        for (int i = 0; i < lb1.getBookList().size(); i++) {
            libItems.add(lb1.getBookList().get(i));
        }

        for (int i = 0; i < lb1.getDvdList().size(); i++) {
            libItems.add(lb1.getDvdList().get(i));
        }

        lb1.setLibraryItems(libItems);


        //connverting arraylist to json object
        String json_1 = new Gson().toJson(libItems);

        return ok(json_1).as("application/json");
    }

    //deleteItem
    public Result getDeletedItem() {
        JsonNode json = request().body().asJson();
        int tempItemPosition;
        JsonNode jsonNode;
        try {
            tempItemPosition = (Integer.parseInt(json.get("id").asText()));
        } catch (NumberFormatException e) {
            jsonNode = Json.toJson(new StringMaker("Need Numerical ID"));
            return ok(jsonNode).as("application/json");
        }

//checking whether the certain item is a book or a dvd
        int correctPosition_book = lb1.searchBook(lb1.getBookList(), tempItemPosition);
        int correctPosition_dvd = lb1.searchDVD(lb1.getDvdList(), tempItemPosition);

        if (correctPosition_book != -1) {
            jsonNode = Json.toJson(new StringMaker("Are you sure you want to delete " + lb1.getBookList().get(correctPosition_book).getTitle() + " of Type Book"));
        } else if (correctPosition_dvd != -1) {
            jsonNode = Json.toJson(new StringMaker("Are you sure you want to delete " + lb1.getDvdList().get(correctPosition_dvd).getTitle() + " of Type DVD"));
        } else {
            jsonNode = Json.toJson(new StringMaker("ID does not exist"));
        }

        return ok(jsonNode).as("application/json");
    }

    //getting dvd arraylist as a json
    public Result getDVDList() {
        String json_1 = new Gson().toJson(lb1.getDvdList());
        JsonNode jsonNode_1 = Json.toJson(json_1);
        return ok(json_1).as("application/json");
    }

    //borrowing item
    public Result borrowItem() {

        String[] dateSeperated;
        JsonNode json = request().body().asJson();

        dateSeperated = json.get("date").asText().split("/");
        int day = Integer.parseInt(dateSeperated[0]);
        int month = Integer.parseInt(dateSeperated[1]);
        int year = Integer.parseInt(dateSeperated[2]);

        //getting dat as day month year


        int itemId = (Integer.parseInt(json.get("itemId").asText()));
        int readerId = (Integer.parseInt(json.get("readerId").asText()));

        //storing the location of item in its respective array
        int tempBookid = lb1.searchBook(lb1.getBookList(), itemId);
        int tempDVDid = lb1.searchDVD(lb1.getDvdList(), itemId);

//storing location of reader in its respective array
        int tempReaderId = lb1.getReader(lb1.getReaderList(), readerId);
        DataBaseConnect db4 = new DataBaseConnect();
//if to be borrower has not borrowed any item
        if (tempReaderId != -1) {
            //if previously search matched a book
            if (tempBookid != -1) {
                //borrowing the book and adding to database
                lb1.BorrowItem(lb1.getBookList().get(tempBookid), lb1.getReaderList().get(tempReaderId), day, month, year);
                db4.BorrowBook(itemId, readerId, day, month, year);
            } else if (tempDVDid != -1) {
                lb1.BorrowItem(lb1.getDvdList().get(tempDVDid), lb1.getReaderList().get(tempReaderId), day, month, year);
                db4.BorrowDVD(itemId, readerId, day, month, year);
            }
        } else {
            return ok(json).as("application/json");
        }
        return ok(json).as("application/json");
    }

    //get the book slots
    public Result getBookSlots() {
        String size = WestminsterLibraryManager.getMAXBOOKS() - lb1.getBookList().size() + "";
        JsonNode jsonNode = Json.toJson(new StringMaker(size));
        return ok(jsonNode).as("application/json");

    }

    //get the dvd slots
    public Result getDvdSlots() {
        String size = WestminsterLibraryManager.getMAXDVD() - lb1.getDvdList().size() + "";
        JsonNode jsonNode = Json.toJson(new StringMaker(size));

        return ok(jsonNode).as("application/json");

    }

    //get the confirmation to add item and validating
    public Result confirmAddItem() {
        JsonNode json = request().body().asJson();
        JsonNode jsonNode;
        int id;
        try {
            id = Integer.parseInt(json.get("id").asText());
        } catch (NumberFormatException e) {
            jsonNode = Json.toJson(new StringMaker("Need Numerical ID"));
            return ok(jsonNode).as("application/json");
        }

        if ((lb1.searchBook(lb1.getBookList(), id) == -1) && (lb1.searchDVD(lb1.getDvdList(), id) == -1)) {
            jsonNode = Json.toJson(new StringMaker("success"));

        } else {
            jsonNode = Json.toJson(new StringMaker("ID already exists"));

        }


        return ok(jsonNode).as("application/json");

    }


    //confirming borrowing
    public Result confirmBorrowItem() {
        JsonNode json = request().body().asJson();
        JsonNode jsonNode;
        int lib_id;
        int reader_id;
        //validating
        try {
            lib_id = Integer.parseInt(json.get("itemId").asText());
            reader_id = Integer.parseInt(json.get("readerId").asText());
        } catch (NumberFormatException e) {
            jsonNode = Json.toJson(new StringMaker("Need Numerical ID"));
            return ok(jsonNode).as("application/json");
        }
//getting position of reader and library item of its respective
        int x = lb1.searchBook(lb1.getBookList(), lib_id);
        int y = lb1.searchDVD(lb1.getDvdList(), lib_id);
        int z = lb1.getReader(lb1.getReaderList(), reader_id);

        if (((x != -1) || (y != -1)) && (z != -1)) {
//if reader exists and library item ecists
            if (lb1.getReaderList().get(z).getItem() != null) {
                //check whether reader has already borrowed a book
                jsonNode = Json.toJson(new StringMaker("User has already borrowed a book"));
                return ok(jsonNode).as("application/json");
            }


//if book exists
            if (x != -1) {
                if (lb1.getBookList().get(x).getCurrent() == null) {
                    //if  book is not borrowed by anyone
                    jsonNode = Json.toJson(new StringMaker("success ," + lb1.getBookList().get(x).getTitle() + "(Book)" + "; To be burrowed by - " + lb1.getReaderList().get(z).getName()));
                    return ok(jsonNode).as("application/json");
                } else {
                    //if book is not available, will display when it will be displayed next
                    int day = lb1.getBookList().get(x).getDueDate().getDay();
                    int month = lb1.getBookList().get(x).getDueDate().getMonth();
                    int year = lb1.getBookList().get(x).getDueDate().getYear();
                    jsonNode = Json.toJson(new StringMaker("Book not available at the moment, Expected to be available in " + day + "/" + month + "/" + year));
                    return ok(jsonNode).as("application/json");
                }
            } else {
                //if dvd exists
                if (lb1.getDvdList().get(y).getCurrent() == null) {
                    //if dvd not burrowed by anyone

                    jsonNode = Json.toJson(new StringMaker("success ," + lb1.getDvdList().get(y).getTitle() + "(DVD)" + "; To be burrowed by - " + lb1.getReaderList().get(z).getName()));
                    return ok(jsonNode).as("application/json");
                }
                //if dvd burrowed by someelse, give an estimate when it will be available
                else {
                    int day = lb1.getDvdList().get(y).getDueDate().getDay();
                    int month = lb1.getDvdList().get(y).getDueDate().getMonth();
                    int year = lb1.getDvdList().get(y).getDueDate().getYear();
                    jsonNode = Json.toJson(new StringMaker("DVD not available at the moment, Expected to be available in" + day + "/" + month + "/" + year));
                    return ok(jsonNode).as("application/json");
                }
            }


        } else {
            //if id is not valid
            jsonNode = Json.toJson(new StringMaker("ID non-existant"));
            return ok(jsonNode).as("application/json");

        }


    }

    //confirming when returning item
    public Result confirmReturnItem() {
        JsonNode json = request().body().asJson();
        JsonNode jsonNode;
        int id;
        try {
            id = Integer.parseInt(json.get("id").asText());
        } catch (NumberFormatException e) {
            jsonNode = Json.toJson(new StringMaker("Need Numerical ID"));
            return ok(jsonNode).as("application/json");
        }
        int x = (lb1.searchBook(lb1.getBookList(), id));
        int y = (lb1.searchDVD(lb1.getDvdList(), id));

        //item exists
        if ((x != -1) || (y != -1)) {
            if (x != -1) {
                if (lb1.getBookList().get(x).getCurrent() == null) {
                    jsonNode = Json.toJson(new StringMaker("Book is not burrowed so it cannot be returned!!"));
                    return ok(jsonNode).as("application/json");
                } else {
                    jsonNode = Json.toJson(new StringMaker("Book to be returned. Title : " + lb1.getBookList().get(x).getTitle() + " Burrower: " + lb1.getBookList().get(x).getCurrent().getName()));
                    return ok(jsonNode).as("application/json");
                }
            } else if (y != -1) {
                if (lb1.getDvdList().get(y).getCurrent() == null) {
                    jsonNode = Json.toJson(new StringMaker("DVD is not burrowed so it cannot  be returned!!"));
                    return ok(jsonNode).as("application/json");

                } else {
                    jsonNode = Json.toJson(new StringMaker("DVD to be returned. Title : " + lb1.getDvdList().get(y).getTitle() + " Burrower: " + lb1.getDvdList().get(y).getCurrent().getName()));
                    return ok(jsonNode).as("application/json");
                }
            } else {
                jsonNode = Json.toJson(new StringMaker("Error"));
                return ok(jsonNode).as("application/json");

            }


        } else {
            jsonNode = Json.toJson(new StringMaker("Item does not exist"));
            return ok(jsonNode).as("application/json");
        }


    }

    public Result getConfirmedBorrowedDetails() {
        JsonNode json = request().body().asJson();
        int readerid;

        JsonNode jsonNode = Json.toJson(new StringMaker("DVD returned"));
        return ok(jsonNode).as("application/json");
    }

    //confirm to add reader and validating
    public Result confirmAddReader() {
        JsonNode json = request().body().asJson();
        JsonNode jsonNode;
        int id;
        int tel;

        try {
            id = Integer.parseInt(json.get("id").asText());
        } catch (NumberFormatException e) {
            jsonNode = Json.toJson(new StringMaker("Need Numerical ID"));
            return ok(jsonNode).as("application/json");
        }

        try {
            tel = (json.get("mobile").asInt());

        } catch (NumberFormatException e1) {
            jsonNode = Json.toJson(new StringMaker("Need Valid Phone Number"));
            return ok(jsonNode).as("application/json");
        }

        int z = lb1.getReader(lb1.getReaderList(), id);

        if (z == -1) {
            jsonNode = Json.toJson(new StringMaker("success"));
            return ok(jsonNode).as("application/json");
        } else {
            jsonNode = Json.toJson(new StringMaker("Id already exists"));
            return ok(jsonNode).as("application/json");
        }
    }

    //add reader
    public Result AddReader() {
        JsonNode json = request().body().asJson();
        JsonNode jsonNode;
        int id = Integer.parseInt(json.get("id").asText());
        String name = json.get("name").asText();
        int mobile = Integer.parseInt(json.get("mobile").asText());
        String email = json.get("email").asText();

        try {
            lb1.addnewReader(id, name, mobile, email);
        } catch (Exception e) {
            jsonNode = Json.toJson(new StringMaker("Error Adding reader"));
            return ok(jsonNode).as("application/json");
        }
        DataBaseConnect db6 = new DataBaseConnect();
        db6.InsertReader(id, name, mobile, email);

        jsonNode = Json.toJson(new StringMaker("Reader: " + name + " added successfully"));
        return ok(jsonNode).as("application/json");

    }

    //get list of available readers
    public Result getReaderList() {
        String json_1 = new Gson().toJson(lb1.getReaderList());

        return ok(json_1).as("application/json");
    }

    //get the duelist
    public Result dueBookList() {
        JsonNode json = request().body().asJson();
        System.out.println(json.get("time").asText());

        String[] datereceived = json.get("time").asText().split("/");
        int day = Integer.parseInt(datereceived[0]);
        int month = Integer.parseInt(datereceived[1]);
        int year = Integer.parseInt(datereceived[2]);

        for (int i = 0; i < lb1.getBookList().size(); i++) {
            if (lb1.getBookList().get(i).getCurrent() != null) {
                lb1.checkFee(lb1.getBookList().get(i), day, month, year);
            }
        }

        for (int i = 0; i < lb1.getDvdList().size(); i++) {
            if (lb1.getDvdList().get(i).getCurrent() != null) {
                lb1.checkFee(lb1.getDvdList().get(i), day, month, year);

            }
        }
        lb1.setDueItems();
        for (int i = 0; i < lb1.getBookList().size(); i++) {
            if (lb1.getBookList().get(i).getDueDays() > 0) {
                lb1.getDueItems().add(lb1.getBookList().get(i));
            }
        }
        for (int i = 0; i < lb1.getDvdList().size(); i++) {
            if (lb1.getDvdList().get(i).getDueDays() > 0) {
                lb1.getDueItems().add(lb1.getDvdList().get(i));
            }
        }

        Collections.sort(lb1.getDueItems());


        String json_1 = new Gson().toJson(lb1.getDueItems());
        return ok(json_1).as("application/json");
    }


    //search item
    public Result searchItem() {
        JsonNode json = request().body().asJson();

        String title = json.get("search").asText();


        ArrayList<LibraryItem> itemList1 = new ArrayList<>();

        for (int i = 0; i < lb1.getLibraryItems().size(); i++) {
            if (lb1.getLibraryItems().get(i).getTitle().contains(title)) {
                itemList1.add(lb1.getLibraryItems().get(i));
            }
        }
        String value = new Gson().toJson(itemList1);
        return ok(value).as("application/json");


    }

    public Result AverageDay() {
        JsonNode json = request().body().asJson();
        String[] dateSeperated;

        dateSeperated = json.get("date").asText().split("/");
        int day = Integer.parseInt(dateSeperated[0]);
        int month = Integer.parseInt(dateSeperated[1]);
        int year = Integer.parseInt(dateSeperated[2]);

        int id;
        int readerID;
        try {
            id = Integer.parseInt(json.get("id").asText());
            readerID = Integer.parseInt(json.get("readerId").asText());
        } catch (NumberFormatException e) {
            JsonNode jsonNode = Json.toJson(new StringMaker("Item ID or Reader ID is in Wrong format"));
            return ok(jsonNode).as("application/json");
        }


        DataBaseConnect db7 = new DataBaseConnect();
        int days_received = db7.getAverageDay(id);


        int x = lb1.searchBook(lb1.getBookList(), id);
        int y = lb1.searchDVD(lb1.getDvdList(), id);
        int z = lb1.getReader(lb1.getReaderList(), readerID);

        if (z == -1) {
            JsonNode jsonNode = Json.toJson(new StringMaker("Reader Does not exist"));
            return ok(jsonNode).as("application/json");
        }


        Date sample;

        if ((x == -1) && (y == -1)) {
            JsonNode jsonNode = Json.toJson(new StringMaker("Item Id does not exist"));
            return ok(jsonNode).as("application/json");
        } else {
            if (x != -1) {
                if (lb1.getBookList().get(x).getCurrent() == null) {
                    JsonNode jsonNode = Json.toJson(new StringMaker("Book is available, Contact the librarian"));
                    return ok(jsonNode).as("application/json");
                }
                sample = lb1.getBookList().get(x).getBurrowedDate();
            } else {
                if (lb1.getDvdList().get(y).getCurrent() == null) {
                    JsonNode jsonNode = Json.toJson(new StringMaker("DVD is available, Contact the librarian"));
                    return ok(jsonNode).as("application/json");
                }
                sample = lb1.getDvdList().get(y).getBurrowedDate();
            }

        }


        int timeInFavorDays = (days_received - (Math.round(lb1.calculateBorrowedHours(new Date(day, month, year), sample) / 24.0F)));
        if (timeInFavorDays < 0) {
            timeInFavorDays = 0;
        }
        DataBaseConnect db8 = new DataBaseConnect();
        int count = db8.getCount(id);


        int totalEstimate = (timeInFavorDays + (count * days_received));

        if (totalEstimate == 0) {
            JsonNode jsonNode = Json.toJson(new StringMaker("Data is not enough to provide a prediction,but do you wish to get added to the que"));
            return ok(jsonNode).as("application/json");
        }
        JsonNode jsonNode = Json.toJson(new StringMaker("You will have to wait an aproximate amount of " + totalEstimate + " days , do you wish to enter the que?"));
        return ok(jsonNode).as("application/json");
    }

    public Result addToQue() {
        JsonNode json = request().body().asJson();
        int id = Integer.parseInt(json.get("id").asText());
        int readerID = Integer.parseInt(json.get("readerId").asText());
        DataBaseConnect db10 = new DataBaseConnect();
        db10.addToQue(id, readerID);
        JsonNode jsonNode = Json.toJson(new StringMaker("Successfully added to que"));
        return ok(jsonNode).as("application/json");
    }
         /*
         First it checks whether item is burrowed or not. If available it will terminate saying
         contact librarian else it would proceed as expected. With the previous history of the item's returns
         I was able to track the average amount of hours it was borrowed before returned. lets name the variable/24 as x.
         x is the average days it took to return object

         Therefore whenever a person wants a prediction it will first calculate x.
          and find the difference between current date and the day it was  borrowed. it will then subtract
          the difference from x. And for each of the person who is the que already waiting for the item, the days will
          increase by x.

          After the prediction the person will be prompted to be added to the que or not

          */


    public static WestminsterLibraryManager getLb1() {
        return lb1;
    }

}
