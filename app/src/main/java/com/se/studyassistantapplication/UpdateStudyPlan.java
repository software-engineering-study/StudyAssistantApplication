package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class UpdateStudyPlan extends AppCompatActivity {
    // 수정할 학습 계획 객체
    public StudyPlan study_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_study_plan);
        
        //StudyPlan 객체 받아오기
        Intent intent = getIntent();
        int id = intent.getExtras().getInt("id");

        // 날짜를 출력하는 텍스트뷰에 지정된 날짜 설정.
        TextView startDay = findViewById(R.id.startDay);
        TextView endDay = findViewById(R.id.endDay);
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