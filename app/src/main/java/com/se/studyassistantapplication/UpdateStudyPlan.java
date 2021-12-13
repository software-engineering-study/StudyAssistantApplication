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

public class UpdateStudyPlan extends AppCompatActivity {
    public StudyPlan study_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_study_plan);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        study_plan = bundle.getParcelable("StudyPlan");

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
                if(et_title.getText().toString().equals("")) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(UpdateStudyPlan.this);
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
                    updateStudyPlan();
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
                    // Date Picker에서 선택한 날짜를 TexstartDayiew에 설정
                    TextView startDay = findViewById(R.id.startDay);
                    startDay.setText(String.format("%d-%d-%d", yy,mm+1,dd));

                }
            };

    DatePickerDialog.OnDateSetListener endDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    TextView endDay = findViewById(R.id.endDay);
                    endDay.setText(String.format("%d-%d-%d", yy,mm+1,dd));

                }
            };

    public void mOnClick_DatePick(View view, DatePickerDialog.OnDateSetListener listener){
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, listener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
    }

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
        Date today = null;
        try {
            startDay = fm.parse(startDayTv.getText().toString());
            endDay = fm.parse(endDayTv.getText().toString());
            today = fm.parse(fm.format(new Date()));
        }catch (ParseException e){
            e.printStackTrace();
            Log.e(this.getClass().getName(), "error");
        }

        if(endDay.before(startDay)){
            Toast.makeText(getApplicationContext(), "종료 날짜가 시작 날짜보다 앞에 있습니다. 다시 선택해 주세요.", Toast.LENGTH_LONG).show();
            return;
        }

        StudyPlan updatedStudyPlan = new StudyPlan(title.getText().toString()
                , content.getText().toString(), startDay, endDay, study_plan.plan_status, study_plan.plan_id);

        updateStudyPlanDB(updatedStudyPlan);
        Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_LONG).show();
    }

    public void updateStudyPlanDB(StudyPlan studyPlan)
    {
        SQLiteDatabase database;
        database = openOrCreateDatabase(MainActivity.DB_NAME, MODE_PRIVATE, null);
        database.execSQL(studyPlan.toDBUpdateString());
    }
}