package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 학습 계획을 열람, 수정, 삭제, 상태변경하는 기능 수행
 */
public class OpenStudyPlan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_study_plan);

        Intent intent = getIntent();
        int id = intent.getExtras().getInt("id");

        showStudyPlan(id);
    }

    /**
     * Main 클래스의 selectStudyPlan 메소드 호출
     * 시 호출되며, selectStudyPlan 메소드에서 전
     * 달받은 StudyPlan 객체의 정보로 showStudy
     * PlanDB 메소드 호출을 통해 학습 계획을 조
     * 회하여 학습 계획 열람 기능을 수행한다.
     * -> 메서드 설명 변경해야함
     * # () -> (int id)
     */
    public void showStudyPlan(int id)
    {
        Cursor cursor = showStudyPlanDB(id);
        StudyPlan studyPlan = null;
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

        while(cursor.moveToNext())
        {
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            Date startDay = null;
            Date endDay = null;
            try{
                startDay = fm.parse(cursor.getString(3));
                endDay = fm.parse(cursor.getString(4));
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            boolean status = Boolean.parseBoolean(cursor.getString(5));
            studyPlan = new StudyPlan(title, content, startDay, endDay, status);
        }

        TextView tv_planTitle = findViewById(R.id.planTitle);
        TextView tv_planContent = findViewById(R.id.planContent);
        TextView tv_startDay = findViewById(R.id.startDay);
        TextView tv_endDay = findViewById(R.id.endDay);
        Button btn_status = findViewById(R.id.statusBtn);

        tv_planTitle.setText(studyPlan.plan_title);
        tv_planContent.setText(studyPlan.plan_content);
        tv_startDay.setText(fm.format(studyPlan.plan_start_day));
        tv_endDay.setText(fm.format(studyPlan.plan_end_day));
        btn_status.setText(Boolean.toString(studyPlan.plan_status));
    }

    /**
     * 입력받은 StudyPlan 객체의 plan_id를 통하
     * 여 해당 변수의 정보로 데이터베이스에서 학
     * 습 계획을 조회한 후에 Cursor 형태로 결과
     * 를 반환한다.
     * #@param study_plan StudyPlan 객체 -> id로 변경 #####
     * @return 데이터베이스에서 조회한 학습 계획
     */
    public Cursor showStudyPlanDB(int id)
    {
        SQLiteDatabase database;
        database = openOrCreateDatabase("study_plan_db", MODE_PRIVATE, null);

        //select * from study_plan_tb where _id = id
        Cursor cursor = database.rawQuery("select * from study_plan_tb where _id = " + id, null);
        return cursor;
    }

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