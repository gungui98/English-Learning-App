package com.loader.user;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by GUNGUI on 10/18/2017.
 */
public class UserManager {
    static UserManager userManager = new UserManager();
    static DataBaseHelper db;
    static ArrayList<UserRecord> records;

    private UserManager() {
        records  = new ArrayList<>();
        db = new DataBaseHelper();
        ArrayList<java.sql.Date> allDate = null;
        ArrayList<Integer> reviewedScore = null;
        ArrayList<Integer> newWordScore = null;
        try {
           allDate = db.getAllDate();
           reviewedScore =db.getAllReviewedScore();
           newWordScore = db.getAllNewWordScore();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i=0;i<allDate.size();i++){
            UserRecord userRecord = new UserRecord(allDate.get(i),reviewedScore.get(i),newWordScore.get(i));
            records.add(userRecord);
        }
    }
    public static ArrayList<UserRecord> getRecords(){
        return records;
    }
    public static boolean doneToday(){
        return db.isToday();
    }
    public static void insertNewRecord(int reviewedScore,int newWordScore){
        db.importNewRecord(reviewedScore,newWordScore);
    }

    /**
     * user's record
     */
    public class UserRecord{
        public java.sql.Date date;
        public int Reviewed;
        public int NewWord;

        public UserRecord(java.sql.Date date, int reviewed, int newWord) {
            this.date = date;
            Reviewed = reviewed;
            NewWord = newWord;
        }

        public String getDate() {
            return Integer.toString(date.getDate())+"/"+Integer.toString(date.getMonth());
        }

        public int getReviewed() {
            return Reviewed;
        }

        public int getNewWord() {
            return NewWord;
        }
    }

    /**
     * test
     * @param args
     */
    public static void main(String[] args) {


    }
}
