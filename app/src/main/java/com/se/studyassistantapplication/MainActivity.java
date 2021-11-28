package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

/**
 * Main 클래스는 학습 계획 목록과 학습 캘린더를 보여주는 기능을 수행하고 사용자가 선택한
 * 학습 계획 정보를 StudyPlan 클래스로 전달하는 역할을 한다.
 */
public class MainActivity extends AppCompatActivity {
    // Calendar API를 이용하기 위한 객체
    public Calendar calendar;
    // 학습 계획 정보(학습 계획 제목, 학습 계획 내용, 학습
    // 시작 날짜와 종료 날짜, 학습 계획 상태)
    public ArrayList<StudyPlan> study_plan;
    // 사용자가 학습 캘린더에서 선택한 날짜(기본 설정값은
    // 현재 날짜)
    public Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB 없으면 생성
        SQLiteDatabase database;
        database = openOrCreateDatabase("study_plan_db", MODE_PRIVATE, null);

        database.execSQL("create table if not exists study_plan_tb"
                + " ("
                + "_id integer primary key autoincrement" //id 어떻게 처리할지 고민해야됨
                + ", title text"
                + ", content text"
                + ", start_day date"
                + ", end_day date"
                + ", status boolean"
                + ")");
    }

    /**
     * 주 단위로 학습 캘린더를 보여준다. 슬라이드
     * 방식을 통해 조작하여 학습 캘린더에서 다른
     * 주의 날짜를 볼 수 있다.
     */
    public void showCalendar()
    {

    }

    /**
     * 사용자가 학습 캘린더에서 선택한 날짜를
     * selectedDate 변수에 저장한다.
     */
    public void selectDate()
    {

    }

    /**
     * selectedDate 변수에 저장된 날짜를 기준으
     * 로 학습 계획 목록을 보여준다.
     */
    public void showStudyPlanList()
    {

    }

    /**
     * 학습 계획 작성 액티비티를 실행한다.
     *  #파라미터 변경함 (void) -> (View v)
     */
    public void clickCreateStudyPlan(View v)
    {
        Intent intent = new Intent(getApplicationContext(), CreateStudyPlan.class);
        startActivity(intent);
    }

    /**
     * selectedDate 변수에 저장된 날짜를 기준으
     * 로 데이터베이스에서 해당 날짜의 학습 계획
     * 들을 조회하여 Cursor 형태로 결과를 반환한
     * 다.
     */
    public void selectStudyPlanListDB()
    {

    }

//    /**
//     * 사용자가 학습 계획 목록에서 학습 계획 선
//     * 택시에 학습 계획 목록에서 보여지는 정보(학
//     * 습 계획 제목, 시작 날짜, 종료 날짜)를 통해
//     * showStudyPlan 메소드를 호출하여 학습 계
//     * 획 열람 기능을 실행한다.
//     * @param title 학습 계획 제목
//     * @param startday 시작 날짜
//     * @param endday 종료 날짜
//     * @param id ID
//     * @return 학습 계획 열람 기능 실행???
//     */
//    public StudyPlan selectStudyPlan(String title, Date startday, Date endday, int id)
//    {
//
//    }



}