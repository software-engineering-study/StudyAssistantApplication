package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import java.util.Date;

/**
 * 학습 계획을 수정, 삭제, 상태변경하는 기능 수행
 */
public class StudyPlan extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_plan);
    }

//    /**
//     * 학습 계획 정보(학습 계획 제목, 내용, 시작
//     * 날짜, 종료 날짜, 상태, ID)를 입력으로 하여
//     * StudyPlan 객체를 생성하는 생성자 메소드
//     * 다.
//     * @param title 학습 계획 제목
//     * @param content 내용
//     * @param startday 시작 날짜
//     * @param endday 종료 날짜
//     * @param status 상태
//     * @param id ID
//     */
//    public StudyPlan(String title, String content, Date startday, Date endday, Boolean status, int id)
//    {
//        this.plan_title = title;
//        this.plan_content = content;
//        this.plan_start_day = startday;
//        this.plan_end_day = endday;
//        this.plan_status = status;
//        this.plan_id = id;
//    }

    /**
     * Main 클래스의 selectStudyPlan 메소드 호출
     * 시 호출되며, selectStudyPlan 메소드에서 전
     * 달받은 StudyPlan 객체의 정보로 showStudy
     * PlanDB 메소드 호출을 통해 학습 계획을 조
     * 회하여 학습 계획 열람 기능을 수행한다.
     */
    public void showStudyPlan()
    {

    }

//    /**
//     * 입력받은 StudyPlan 객체의 plan_id를 통하
//     * 여 해당 변수의 정보로 데이터베이스에서 학
//     * 습 계획을 조회한 후에 Cursor 형태로 결과
//     * 를 반환한다.
//     * @param study_plan StudyPlan 객체
//     * @return 데이터베이스에서 조회한 학습 계획
//     */
//    public Cursor showStudyPlanDB(StudyPlan study_plan)
//    {
//
//        return
//    }

    /**
     * 해당 학습 계획이 완료 상태일 경우, 미완료
     * 상태로 변경하고 미완료 상태일 경우, 완료
     * 상태로 변경한다.
     */
    public void setStudyPlanStatus()
    {

    }

    /**
     * 학습 계획 수정 액티비티를 실행한다
     */
    public void clickUpdateStudyPlan()
    {

    }

    /**
     * 입력받은 StudyPlan 객체의 plan_id를 통하
     * 여 데이터베이스에서 학습 계획을 조회하여
     * 학습 계획 상태를 변경한다.
     */
    public void updatePlanStatusDB()
    {

    }

    /**
     * 입력받은 StudyPlan 객체의 plan_id를 통하
     * 여 해당 학습 계획 정보를 데이터베이스에서
     * 삭제한다.
     */
    public void deleteStudyPlanDB()
    {}


}