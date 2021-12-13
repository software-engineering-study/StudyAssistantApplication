package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudyPlan implements Parcelable {
    public String plan_title;
    public String plan_content;
    public Date plan_start_day;
    public Date plan_end_day;
    public Boolean plan_status;
    public int plan_id;

    public StudyPlan(String title, String content, Date startDay, Date endDay, Boolean status)
    {
        this.plan_title = title;
        this.plan_content = content;
        this.plan_start_day = startDay;
        this.plan_end_day = endDay;
        this.plan_status = status;
    }

    public StudyPlan(String title, String content, Date startDay, Date endDay, Boolean status, int id)
    {
        this.plan_title = title;
        this.plan_content = content;
        this.plan_start_day = startDay;
        this.plan_end_day = endDay;
        this.plan_status = status;
        this.plan_id = id;
    }

    public StudyPlan(Parcel src) {
        this.plan_id = src.readInt();
        this.plan_title = src.readString();
        this.plan_content = src.readString();
        this.plan_start_day = new Date(src.readLong());
        this.plan_end_day = new Date(src.readLong());
        this.plan_status = src.readString().equalsIgnoreCase("true");
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public StudyPlan createFromParcel(Parcel in){
            return new StudyPlan(in);
        }
        public StudyPlan[] newArray(int size){
            return new StudyPlan[size];
        }
    };
    public int describeContents(){
        return 0;
    }
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(this.plan_id);
        dest.writeString(this.plan_title);
        dest.writeString(this.plan_content);
        dest.writeLong(this.plan_start_day.getTime());
        dest.writeLong(this.plan_end_day.getTime());
        dest.writeString(Boolean.toString(this.plan_status));
    }

    public String toDBUpdateString()
    {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = fm.format(plan_start_day);
        String endDay = fm.format(plan_end_day);

        return "UPDATE " + MainActivity.TB_NAME + " SET " +
                "title='" +
                plan_title +
                "', content='" +
                plan_content +
                "', start_day='" +
                startDay +
                "', end_day='" +
                endDay +
                "' " +
                "WHERE _id=" +
                plan_id;
    }

    public String toDBInsertString()
    {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = fm.format(plan_start_day);
        String endDay = fm.format(plan_end_day);

        return "INSERT INTO " + MainActivity.TB_NAME + " (title, content, start_day, end_day, status) "
                + "VALUES "
                + "("
                + "'"
                + plan_title
                + "', "
                + "'"
                + plan_content
                + "', "
                + "'"
                + startDay
                + "', "
                + "'"
                + endDay
                + "', "
                + "'"
                + plan_status
                + "'"
                + ")";
    }

    public String toString()
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(plan_status == false) {
            return plan_title + "\n"
                    + "시작일: " + format.format(plan_start_day) + "\n"
                    + "종료일: " + format.format(plan_end_day) + "\n"
                    + "상태: 미완료";
        } else {
            return plan_title + "\n"
                    + "시작일: " + format.format(plan_start_day) + "\n"
                    + "종료일: " + format.format(plan_end_day) + "\n"
                    + "상태: 완료";
        }
    }
}