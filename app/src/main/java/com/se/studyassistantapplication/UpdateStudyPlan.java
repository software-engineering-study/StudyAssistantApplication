package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateStudyPlan extends AppCompatActivity {
    // 수정할 학습 계획 객체
    public StudyPlan study_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_study_plan);
        
        //StudyPlan 객체 받아오기
        Intent intent = getIntent();
        //예외처리 필요
        Bundle bundle = intent.getExtras();
        study_plan = bundle.getParcelable("StudyPlan");

        Toast.makeText(this, study_plan.toString(), Toast.LENGTH_SHORT).show();

        EditText et_title = findViewById(R.id.planTitle);
        EditText et_content = findViewById(R.id.planContent);
        TextView tv_startDay = findViewById(R.id.startDay);
        TextView tv_endDay = findViewById(R.id.endDay);
        FloatingActionButton fab_save = findViewById(R.id.saveButton);

        et_title.setText(study_plan.plan_title);
        et_content.setText(study_plan.plan_content);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        try {
            tv_startDay.setText(fm.format(study_plan.plan_start_day));
            tv_endDay.setText(fm.format(study_plan.plan_end_day));
        }catch (Exception e){
            e.printStackTrace();
            Log.e(this.getClass().getName(), "error");
        }

        //https://blog.naver.com/wizand02/221691801438
        tv_startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick_DatePick(view, startDateSetListener);
            }
        });

        tv_endDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mOnClick_DatePick(view, endDateSetListener);
            }
        });
        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudyPlan();
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
    }

    DatePickerDialog.OnDateSetListener startDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    // Date Picker에서 선택한 날짜를 TexstartDayiew에 설정
                    TextView startDay = findViewById(R.id.startDay);
                    startDay.setText(String.format("%d-%d-%d", yy,mm+1,dd));

                }
            };

    DatePickerDialog.OnDateSetListener endDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    // Date Picker에서 선택한 날짜를 TexstartDayiew에 설정
                    TextView endDay = findViewById(R.id.endDay);
                    endDay.setText(String.format("%d-%d-%d", yy,mm+1,dd));

                }
            };

    public void mOnClick_DatePick(View view, DatePickerDialog.OnDateSetListener listener){
        // DATE Picker가 처음 떴을 때, 오늘 날짜가 보이도록 설정.
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, listener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
    }

    /**
     * 사용자가 수정한 학습 계획 정보로 StudyPlan 객
     * 체를 생성하고 데이터베이스에 저장하는 메소드
     * 를 호출하여 학습 계획 수정 기능을 수행한다
     */
    public void updateStudyPlan()
    {
        EditText title, content;
        TextView startDayTv, endDayTv;

        title = findViewById(R.id.planTitle);
        content = findViewById(R.id.planContent);
        startDayTv = findViewById(R.id.startDay);
        endDayTv = findViewById(R.id.endDay);

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        Date startDay = null;
        Date endDay = null;
        try {
            startDay = fm.parse(startDayTv.getText().toString());
            endDay = fm.parse(endDayTv.getText().toString());
        }catch (ParseException e){
            e.printStackTrace();
            Log.e(this.getClass().getName(), "error");
        }

        StudyPlan updatedStudyPlan = new StudyPlan(study_plan.plan_id, title.getText().toString()
                , content.getText().toString(), startDay, endDay, study_plan.plan_status);

        updateStudyPlanDB(updatedStudyPlan);
    }

    /**
     * 생성한 StudyPlan 객체의 plan_id를 입력으로 하
     * 여 데이터베이스에 작성한 학습 계획 정보를 수
     * 정한다.
     * @param study_plan 데이터베이스에 저장할 객체
     */
    public void updateStudyPlanDB(StudyPlan study_plan)
    {
        SQLiteDatabase database;
        String dbUpdateString = "UPDATE study_plan_tb SET "
                + study_plan.toDBUpdateString()
                + " where _id = " +
                study_plan.plan_id;

        database = openOrCreateDatabase("study_plan_db", MODE_PRIVATE, null);
        //예외처리 필요

        database.execSQL(dbUpdateString);
        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
    }
}