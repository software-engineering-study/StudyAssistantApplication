package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 학습 계획 정보를 가지는 객첸
 */
public class StudyPlan{
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
     * 날짜, 종료 날짜, 상태, ID)를 입력으로 하여
     * StudyPlan 객체를 생성하는 생성자 메소드
     * 다.
     * @param title 학습 계획 제목
     * @param content 내용
     * @param startday 시작 날짜
     * @param endday 종료 날짜
     * @param status 상태
     */
    public StudyPlan(String title, String content, Date startday, Date endday, Boolean status)
    {
        this.plan_title = title;
        this.plan_content = content;
        this.plan_start_day = startday;
        this.plan_end_day = endday;
        this.plan_status = status;
//        this.plan_id = id;
    }

    //id 입력하는 생성자
    public StudyPlan(int id, String title, String content, Date startday, Date endday, Boolean status)
    {
        this.plan_id = id;
        this.plan_title = title;
        this.plan_content = content;
        this.plan_start_day = startday;
        this.plan_end_day = endday;
        this.plan_status = status;
    }

    //for db
    public String toDBInsertString()
    {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = fm.format(plan_start_day);
        String endDay = fm.format(plan_end_day);

        return "("
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

//        return "("
//                + plan_id       //??
//                + ", "
//                + "'"
//                + plan_title
//                + "', "
//                + "'"
//                + plan_content
//                + "', "
//                + "'"
//                + startDay
//                + "', "
//                + "'"
//                + endDay
//                + "', "
//                + "'"
//                + plan_status
//                + "'"
//                + ")";
    }


    // test
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