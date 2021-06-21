package controllers;

import java.sql.*;


public class DataBaseConnect {
private Connection connection_1;
private Statement statement_1;

private ResultSet result_1;



    public DataBaseConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection_1=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_system","root","");
            statement_1 = connection_1.createStatement();


        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        }
    }



    public void getBookList(){
        try{
            String query = "select * from books";
            result_1=statement_1.executeQuery(query);
            System.out.println("Book records");
            while (result_1.next()) {
                int id = result_1.getInt("Id");
                String title = result_1.getString("title");
                int  pub_day = result_1.getInt("pub_day");

                int  pub_month = result_1.getInt("pub_month");
                int  pub_year = result_1.getInt("pub_year");

                int pages = result_1.getInt("pages");
                String publisher = result_1.getString("publisher");
                String sector = result_1.getString("sector");
                String authors = result_1.getString("authors");


                String[] split_authors=authors.split(",");

                Tester.getLb1().addnewItem(id,title,pub_day,pub_month,pub_year,pages,publisher,sector);

                for (int i = 0; i < split_authors.length; i++) {
                    Book.getCurrent_book().AddItem(Book.getCurrent_book().getAuthors(), split_authors[i]);
                }

            }
        } catch (Exception ex){
            System.out.println(ex);
        }
    }


    public void getDVDList(){
        try{
            String query = "select * from dvd";
            result_1=statement_1.executeQuery(query);
            System.out.println("Book records");
            while (result_1.next()) {
                int id = result_1.getInt("Id");
                String title = result_1.getString("title");
                int  pub_day = result_1.getInt("pub_day");

                int  pub_month = result_1.getInt("pub_month");
                int  pub_year = result_1.getInt("pub_year");

                String actors = result_1.getString("actors");
                String languages = result_1.getString("languages");
                String sector = result_1.getString("sector");
                String subtitles = result_1.getString("subtitles");


                String[] split_actors=actors.split(",");
                String[] split_languages=actors.split(",");
                String[] split_subtitles=actors.split(",");


                Tester.getLb1().addnewItem(id,title,pub_day,pub_month,pub_year,sector);

                for (int i = 0; i < split_actors.length; i++) {
                    DVD.getCurrent_dvd().AddItem(DVD.getCurrent_dvd().getActors(), split_actors[i]);
                }
                for (int i = 0; i < split_languages.length; i++) {
                    DVD.getCurrent_dvd().AddItem(DVD.getCurrent_dvd().getLanguages(), split_languages[i]);
                }
                for (int i = 0; i < split_subtitles.length; i++) {
                    DVD.getCurrent_dvd().AddItem(DVD.getCurrent_dvd().getSubtitles(), split_subtitles[i]);
                }

            }
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void getReader(){
        try{
            String query = "select * from reader";
            result_1=statement_1.executeQuery(query);

            while (result_1.next()) {

                int id = result_1.getInt("id");
                String name = result_1.getString("name");
                int mobile = result_1.getInt("mobile");
                String email  = result_1.getString("email");

                Tester.getLb1().addnewReader(id,name,mobile,email);
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
    }


    public void getBorrowedData(){
        try{
            String query = "select * from book_reader";
            result_1=statement_1.executeQuery(query);

            while (result_1.next()) {
                int book_id = result_1.getInt("book_id");
                int reader_id = result_1.getInt("reader_id");

                int borrow_day = result_1.getInt("borrowed_day");
                int borrowed_month  = result_1.getInt("borrowd_month");
                int borrowed_year = result_1.getInt("borrowed_year");

                Tester.getLb1().BorrowItem(Tester.getLb1().searchGetBook(book_id), Tester.getLb1().searchGetReader(reader_id),borrow_day,borrowed_month,borrowed_year);
            }
        } catch (Exception ex){
            System.out.println(ex);
        }


        try{
            String query = "select * from dvd_reader";
            result_1=statement_1.executeQuery(query);

            while (result_1.next()) {
                int dvd_id = result_1.getInt("dvd_id");
                int reader_id = result_1.getInt("reader_id");

                int borrow_day = result_1.getInt("borrowed_day");
                int borrowed_month  = result_1.getInt("borrowed_month");
                int borrowed_year = result_1.getInt("borrowed_year");

                Tester.getLb1().BorrowItem(Tester.getLb1().searchGetDVD(dvd_id), Tester.getLb1().searchGetReader(reader_id),borrow_day,borrowed_month,borrowed_year);
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
    }

    public void insertBook(int id,String title,int pub_day,int pub_month,int pub_year,int pages,String publisher,String sector,String authors){



        String query="INSERT INTO `books`(`id`, `title`, `pub_day`, `pub_month`, `pub_year`, `pages`, `publisher`, `sector`, `authors`) VALUES ("+ id + "," + "\"" + title + "\"" + "," +pub_day+ "," + pub_month + "," + pub_year + "," + pages + "," + "\"" + publisher + "\"" + "," + "\"" + sector + "\"" + "," +"\"" + authors + "\""+")";
            System.out.println(query);

            try {
                statement_1.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }


    }

    public void insertDVD(int id,String title,int pub_day,int pub_month,int pub_year,String sectors,String actors,String langauges,String subtitles){

        String query="INSERT INTO `dvd`(`id`, `title`, `pub_day`, `pub_month`, `pub_year`, `sector`, `actors`, `languages`, `subtitles`) VALUES ("+id + "," + "\"" + title + "\"" + "," +pub_day+ "," + pub_month + "," + pub_year + "," + "\"" + sectors + "\"" + "," + "\"" + actors + "\"" + "," + "\"" + langauges + "\"" + "," +"\"" + subtitles + "\""+")";;
        System.out.println(query);

        try {
            statement_1.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void BorrowBook(int book_id,int reader_id,int borrow_day,int borrow_month,int borrow_year){

        String query="INSERT INTO `book_reader`(`book_id`, `reader_id`, `borrowed_day`, `borrowd_month`, `borrowed_year`) VALUES ("+book_id + "," + reader_id + "," +borrow_day+ "," + borrow_month + "," + borrow_year +")";;
        System.out.println(query);

        try {
            statement_1.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void BorrowDVD(int dvd_id,int reader_id,int borrow_day,int borrow_month,int borrow_year){

        String query="INSERT INTO `dvd_reader`(`dvd_id`, `reader_id`, `borrowed_day`, `borrowed_month`, `borrowed_year`) VALUES ("+dvd_id + "," + reader_id + "," +borrow_day+ "," + borrow_month + "," + borrow_year +")";;
        System.out.println(query);

        try {
            statement_1.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void ReturnBook(int id){

        String query="DELETE FROM `book_reader` WHERE book_id="+id;
        System.out.println(query);

        try {
            statement_1.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void ReturnDVD(int id){

        String query="DELETE FROM `dvd_reader` WHERE dvd_id="+id;
        System.out.println(query);

        try {
            statement_1.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void DeleteBook(int id){


        String query_1="DELETE FROM `books` WHERE id="+id;
        System.out.println(query_1);

        try {
            statement_1.executeUpdate(query_1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void DeleteDVD(int id){

        String query1="DELETE FROM `dvd` WHERE id="+id;
        System.out.println(query1);

        try {
            statement_1.executeUpdate(query1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void InsertReader(int id,String name,int mobile,String email){

        String query="INSERT INTO `reader`(`id`, `name`, `mobile`, `email`) VALUES ("+id + "," + "\"" + name + "\"" + "," +mobile+ "," + "\"" + email + "\"" +")";;
        System.out.println(query);

        try {
            statement_1.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void InsertBookData(int id,int days){

        String query="INSERT INTO `reserve`(`id`, `days`) VALUES ("+id+","+days+")";
        System.out.println(query);

        try {
            statement_1.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getAverageDay(int id){
        int correct_days=0;
        try{
            String query = "SELECT AVG(days) FROM reserve WHERE id ="+id;
            result_1=statement_1.executeQuery(query);
            System.out.println("Book records");

            while (result_1.next()) {
                double days = result_1.getDouble("AVG(days)");
                correct_days = (int) days;
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
        return correct_days;
    }

    public int getCount(int id){
        int count=0;
        try{
            String query = "SELECT * FROM `que_reserve` WHERE book_id="+id;
            result_1=statement_1.executeQuery(query);

            while (result_1.next()) {
                count++;
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
        return count;
    }

    public void addToQue(int id,int readerId){

        String query = "INSERT INTO `que_reserve`(`book_id`, `reader_id`) VALUES ("+id+","+readerId+")";
        System.out.println(query);

        try {
            statement_1.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




}
