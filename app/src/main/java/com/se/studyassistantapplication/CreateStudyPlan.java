package com.se.studyassistantapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    // 생성할 학습 계획 객체
    public StudyPlan study_plan;

    // #임의로 생성
    public static int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_plan);



        // 날짜를 출력하는 텍스트뷰에 오늘 날짜 설정.

        TextView startDay = findViewById(R.id.startDay);
        TextView endDay = findViewById(R.id.endDay);

        Calendar cal = Calendar.getInstance();

//        startDay.setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));

        //https://blog.naver.com/wizand02/221691801438
        startDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick_DatePick(view, startDateSetListener);
            }
        });

        endDay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mOnClick_DatePick(view, endDateSetListener);
            }
        });

        FloatingActionButton button = findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createStudyPlan();
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
     * 학습 계획 작성 화면에서 입력받는 학습 계획
     * 정보로 StudyPlan 클래스의 메소드를 통해 객
     * 체를 생성하고 데이터베이스에 저장하는 메소
     * 드를 호출하여 학습 계획 작성 기능을 수행한
     * 다.
     */
    public void createStudyPlan()
    {
        EditText title, content;
        TextView startDayTv, endDayTv;

        title = findViewById(R.id.planTitle);
        content = findViewById(R.id.planContent);
        startDayTv = findViewById(R.id.startDay);
        endDayTv = findViewById(R.id.endDay);

        SimpleDateFormat fm = new SimpleDateFormat("yy-MM-dd");
        Date startDay = null;
        Date endDay = null;
        try {
            startDay = fm.parse(startDayTv.getText().toString());
            endDay = fm.parse(endDayTv.getText().toString());
        }catch (ParseException e){
            e.printStackTrace();
        }

        StudyPlan studyPlan = new StudyPlan(title.getText().toString()
                , content.getText().toString(), startDay, endDay, false);

        SQLiteDatabase database;
        database = openOrCreateDatabase("study_plan_db", MODE_PRIVATE, null);

//        database.execSQL("insert into study_plan_tb values (31, 't', 'c', '2011-11-11', '2011-11-11', TRUE)");
        insertStudyPlanDB(studyPlan);

        Toast.makeText(getApplicationContext(), studyPlan.toDBInsertString(), Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(), studyPlan.toString(), Toast.LENGTH_LONG).show();
    }

    /**
     * 입력받은 StudyPlan 객체의 plan_id를 입력으로
     * 하여 데이터베이스에 작성한 학습 계획 정보를
     * 저장한다.
     * # 파라미터 변경 () -> (StudyPlan studyPlan)
     */
    public void insertStudyPlanDB(StudyPlan studyPlan)
    {
        SQLiteDatabase database;
        String dbInsert = "insert into study_plan_tb (title, content, start_day, end_day, status)  "
                + "values "
                + studyPlan.toDBInsertString();

        database = openOrCreateDatabase("study_plan_db", MODE_PRIVATE, null);
        //예외처리 필요

        database.execSQL(dbInsert);
        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
    }
}