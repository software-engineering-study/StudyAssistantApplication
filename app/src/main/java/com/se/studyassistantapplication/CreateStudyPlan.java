package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreateStudyPlan extends AppCompatActivity {
    // 생성할 학습 계획 객체
    public StudyPlan study_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_plan);
    }

    /**
     * 학습 계획 작성 화면에서 입력받는 학습 계획
     * 정보로 StudyPlan 클래스의 메소드를 통해 객
     * 체를 생성하고 데이터베이스에 저장하는 메소
     * 드를 호출하여 학습 계획 작성 기능을 수행한
     * 다.
     */
    public void createStudyPlan()
    {

    }

    /**
     * 입력받은 StudyPlan 객체의 plan_id를 입력으로
     * 하여 데이터베이스에 작성한 학습 계획 정보를
     * 저장한다
     */
    public void insertStudyPlanDB()
    {}

}