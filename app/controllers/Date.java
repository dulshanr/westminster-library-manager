package controllers;

public class Date {
    private int day;
    private int month;
    private int year;


    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return day +"/"+ month+"/" + year+"";
    }

    public void setDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;

    }

    public static boolean isHighest(Date d1,Date d2) {
        if (d1.getYear() > d2.getYear()) {
            return true;
        }else if(d2.getYear()>d1.getYear()){
            return false;
        }else {
            if (d1.getMonth() > d2.getMonth()) {
                return true;
            } else if (d2.getMonth() > d1.getMonth()) {
                return false;
            }else {
                if (d1.getDay() > d2.getDay()) {
                    return true;
                }else {
                    return false;
                }
            }

        }


    }
}
