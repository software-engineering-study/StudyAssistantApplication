package com.se.studyassistantapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

public class CreateStudyPlan extends AppCompatActivity {
    public StudyPlan study_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_plan);

        TextView tv_startDay = findViewById(R.id.startDay);
        TextView tv_endDay = findViewById(R.id.endDay);
        EditText et_title;

        et_title = findViewById(R.id.planTitle);
        tv_startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDatePick(view, startDateSetListener);
            }
        });
        tv_endDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClickDatePick(view, endDateSetListener);
            }
        });
        FloatingActionButton fab_save = findViewById(R.id.saveButton);
        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
                Date startDay = null;
                Date endDay = null;
                try {
                    startDay = fm.parse(tv_startDay.getText().toString());
                    endDay = fm.parse(tv_endDay.getText().toString());
                }catch (ParseException e){
                    e.printStackTrace();
                    Log.e(this.getClass().getName(), "error");
                }
                if(et_title.getText().toString().equals("") || startDay == null || endDay == null) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(CreateStudyPlan.this);
                    ad.setIcon(R.mipmap.ic_launcher);
                    ad.setTitle("Error");
                    ad.setMessage("정보를 입력해 주세요");

                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ad.show();
                } else {
                    createStudyPlan();
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
            }
        });
    }
    DatePickerDialog.OnDateSetListener startDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    TextView tv_startDay = findViewById(R.id.startDay);
                    tv_startDay.setText(String.format("%d-%d-%d", yy,mm+1,dd));
                }
            };
    DatePickerDialog.OnDateSetListener endDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    TextView tv_endDay = findViewById(R.id.endDay);
                    tv_endDay.setText(String.format("%d-%d-%d", yy,mm+1,dd));
                }
            };
    public void onClickDatePick(View view, DatePickerDialog.OnDateSetListener listener){
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, listener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
    }

    public void createStudyPlan()
    {
        EditText et_title, et_content;
        TextView tv_startDay, tv_endDay;
        et_title = findViewById(R.id.planTitle);
        et_content = findViewById(R.id.planContent);
        tv_startDay = findViewById(R.id.startDay);
        tv_endDay = findViewById(R.id.endDay);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        Date startDay = null;
        Date endDay = null;
        Date today = null;

        try {
            startDay = fm.parse(tv_startDay.getText().toString());
            endDay = fm.parse(tv_endDay.getText().toString());
            today = fm.parse(fm.format(new Date()));
        }catch (ParseException e){
            e.printStackTrace();
            Log.e(this.getClass().getName(), "error");
        }

        if(startDay.before(today) || endDay.before(today)){
            Toast.makeText(getApplicationContext(), "지난 날짜에 대한 계획은 작성할 수 없습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        if(endDay.before(startDay)){
            Toast.makeText(getApplicationContext(), "종료 날짜가 시작 날짜보다 앞에 있습니다. 다시 선택해 주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        study_plan = new StudyPlan(et_title.getText().toString()
                , et_content.getText().toString(), startDay, endDay, false);

        insertStudyPlanDB(study_plan);
        Toast.makeText(getApplicationContext(), "작성이 완료되었습니다.", Toast.LENGTH_LONG).show();
    }

    public void insertStudyPlanDB(StudyPlan studyPlan)
    {
        SQLiteDatabase database;

        database = openOrCreateDatabase(MainActivity.DB_NAME, MODE_PRIVATE, null);

        database.execSQL(studyPlan.toDBInsertString());
    }
}