package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UpdateStudyPlan extends AppCompatActivity {
    // 수정할 학습 계획 객체
    public StudyPlan study_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_study_plan);
    }

    /**
     * 사용자가 수정한 학습 계획 정보로 StudyPlan 객
     * 체를 생성하고 데이터베이스에 저장하는 메소드
     * 를 호출하여 학습 계획 수정 기능을 수행한다
     */
    public void updateStudyPlan()
    {

    }

    /**
     * 생성한 StudyPlan 객체의 plan_id를 입력으로 하
     * 여 데이터베이스에 작성한 학습 계획 정보를 수
     * 정한다.
     */
    public void updateStudyPlanDB()
    {

    }
}