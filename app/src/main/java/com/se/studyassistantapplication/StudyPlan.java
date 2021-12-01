package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 학습 계획 정보를 가지는 객첸
 */
public class StudyPlan implements Parcelable {
    // 학습 계획 제목
    public String plan_title;
    // 학습 계획 내용
    public String plan_content;
    // 학습 계획 시작 날짜
    public Date plan_start_day;
    // 학습 계획 종료 날짜
    public Date plan_end_day;
    // 학습 계획 완료/미완료(true/false) 상태
    public Boolean plan_status;
    // 학습 계획의 중복을 구별하기 위한 학습 계획 ID
    public int plan_id;

    /**
     * 학습 계획 정보(학습 계획 제목, 내용, 시작
     * 날짜, 종료 날짜, 상태를 입력으로 하여
     * StudyPlan 객체를 생성하는 생성자 메소드
     * 다.
     * DB에 넣을 StudyPlan 객체를 만들때, id의 값은
     * DB에서 AUTOINCREMENT로 설정되어 있으므로 id값 입력이 불필요하다.
     * @param title 학습 계획 제목
     * @param content 내용
     * @param startDay 시작 날짜
     * @param endDay 종료 날짜
     * @param status 상태
     */
    public StudyPlan(String title, String content, Date startDay, Date endDay, Boolean status)
    {
        this.plan_title = title;
        this.plan_content = content;
        this.plan_start_day = startDay;
        this.plan_end_day = endDay;
        this.plan_status = status;
    }

    /**
     * 학습 계획 정보(학습 계획 제목, 내용, 시작
     * 날짜, 종료 날짜, 상태, ID)를 입력으로 하여
     * StudyPlan 객체를 생성하는 생성자 메소드
     * 다.
     * @param title 학습 계획 제목
     * @param content 내용
     * @param startDay 시작 날짜
     * @param endDay 종료 날짜
     * @param status 상태
     * @param id ID
     */
    public StudyPlan(String title, String content, Date startDay, Date endDay, Boolean status, int id)
    {
        this.plan_title = title;
        this.plan_content = content;
        this.plan_start_day = startDay;
        this.plan_end_day = endDay;
        this.plan_status = status;
        this.plan_id = id;
    }

    //Parcel 객체로부터 StudyPlan 객체 생성
    public StudyPlan(Parcel src) {
        this.plan_id = src.readInt();
        this.plan_title = src.readString();
        this.plan_content = src.readString();
        //out.writeLong(date_object.getTime());
        this.plan_start_day = new Date(src.readLong());
        this.plan_end_day = new Date(src.readLong());
        // api레벨 문제로 문자열로 변환하였다가 받음
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

    /**
     * # 새로 추가
     * DB 업데이트시 사용할 질의어를 반환하는 메서드
     * @return 업데이트 질의어
     */
    public String toDBUpdateString()
    {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = fm.format(plan_start_day);
        String endDay = fm.format(plan_end_day);

        //UPDATE study_plan_tb SET title=plan_title, content=plan_content, start_day=startDay
        //, end_day=endDay WHERE _id=study_plan.plan_id
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

    /**
     * # 새로추가
     * DB에 새로 삽입할 경우 사용할 질의어를 반환하는 메서드
     * @return 삽입 질의어
     */
    public String toDBInsertString()
    {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = fm.format(plan_start_day);
        String endDay = fm.format(plan_end_day);

        // INSERT INTO study_plan_tb (title, content, start_day, end_day, status) VALUES (plan_title
        // , plan_content, startDay, endDay, plan_status);
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

    // for test
    public String toString()
    {
//        return "title: " + plan_title + ", "
//                + "content: " + plan_content + "\n,"
//                + "sday: " + plan_start_day + ", "
//                + "eday: " + plan_end_day + ", "
//                + "status: " + plan_status + ", "
//                + "id: " + plan_id;

        return "title: " + plan_title + ", "
                + "content: " + plan_content + "\n,"
                + "sday: " + plan_start_day + ", "
                + "eday: " + plan_end_day + ", "
                + "status: " + plan_status;
    }
}