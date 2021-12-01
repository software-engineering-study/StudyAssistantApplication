package com.se.studyassistantapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    public Date selected_date;

    // 데이터베이스명 # 새로 추가
    public static final String DB_NAME = "study_plan_db";

    // 테이블명 # 새로 추가
    public static final String TB_NAME = "study_plan_tb";
    
    // reqeust 코드 # 새로 추가
    public static final int REQUEST_CODE_OPENSTUDYPLAN = 100;
    public static final int REQUEST_CODE_CREATESTUDYPLAN = 200;
    public static final int REQUEST_CODE_UPDATESTUDYPLAN = 300;

    // study_plan과 리스트뷰를 연결하기 위한 어댑터 # 새로 추가
    public ArrayAdapter<StudyPlan> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView cv_calendarView = findViewById(R.id.calendarView);
        ListView lv_listView = findViewById(R.id.planList);
        FloatingActionButton fab_newPlan = findViewById(R.id.newPlan);

        //DB 없으면 생성
        SQLiteDatabase database;
        database = openOrCreateDatabase(MainActivity.DB_NAME, MODE_PRIVATE, null);

        // CREATE TABLE IF NOT EXISTS study_plan_tb (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT
        // , start_day DATE, end_day DATE, status BOOLEAN)
        database.execSQL("CREATE TABLE IF NOT EXISTS " +
                TB_NAME
                + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT" //id 어떻게 처리할지 고민해야됨
                + ", title TEXT"
                + ", content TEXT"
                + ", start_day DATE"
                + ", end_day DATE"
                + ", status BOOLEAN"
                + ")");
        
        // 캘린더에서 날짜 변경시
        cv_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                selectDate(year, month, day);
                showStudyPlanList(selected_date);
            }
        });

        study_plan = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, study_plan);
        lv_listView.setAdapter(adapter);
        
        // 리스트뷰에서 항목 선택시
        lv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StudyPlan studyPlan = adapter.getItem(i);

                Intent intent = new Intent(getApplicationContext(), OpenStudyPlan.class);
                intent.putExtra("StudyPlan", studyPlan);
                startActivityForResult(intent, REQUEST_CODE_OPENSTUDYPLAN);
            }
        });

        // 새 계획 작성 버튼
        fab_newPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCreateStudyPlan();
            }
        });
    }

    // CalculateActivity 에서 처리된 결과를 받는 메소드
    // 처리된 결과 코드 (resultCode) 가 RESULT_OK 이면 requestCode 를 판별해 결과 처리를 진행한다.
    // CalculateActivity 에서 처리 결과가 담겨온 데이터를 TextView 에 보여준다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            showStudyPlanList(selected_date);
            Toast.makeText(getApplicationContext(), "onAcitivyResult called", Toast.LENGTH_SHORT).show();
        }
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
     * # () -> (int year, int month, int day)
     */
    public void selectDate(int year, int month, int day)
    {
        calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0);
        selected_date = calendar.getTime();
    }

    /**
     * selectedDate 변수에 저장된 날짜를 기준으
     * 로 학습 계획 목록을 보여준다.
     * 캘린더뷰 날짜 변경시 showStudyPlanList이 호출된다.
     * # 멤버변수로 selectedDate 가지고 있는데 파라미터에 쓰는 이유는 뭘까?
     */
    public void showStudyPlanList(Date selectedDate)
    {
        Cursor cursor = selectStudyPlanListDB(selectedDate);
//        if(cursor != null)
//            Log.e(this.getClass().getName(), "not null");
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        
        //리스트 초기화
        study_plan.clear();
        adapter.notifyDataSetChanged();
//        Toast.makeText(getApplicationContext(), Integer.toString(recordCount), Toast.LENGTH_SHORT).show();

        int recordCount = cursor.getCount();
        for(int i = 0; i < recordCount; i++){
            cursor.moveToNext();

            int id = cursor.getInt(0);
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

            study_plan.add(new StudyPlan(title, content, startDay, endDay, status, id));
//            Log.e(this.getClass().getName(), startDay.toString() + endDay.toString() + status);
            adapter.notifyDataSetChanged();
//            Toast.makeText(getApplicationContext(), startDay.toString() + endDay.toString() + status, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 학습 계획 작성 액티비티를 실행한다.
     */
    public void clickCreateStudyPlan()
    {
        Intent intent = new Intent(getApplicationContext(), CreateStudyPlan.class);
        startActivityForResult(intent, REQUEST_CODE_CREATESTUDYPLAN);
    }

    /**
     * selectedDate 변수에 저장된 날짜를 기준으
     * 로 데이터베이스에서 해당 날짜의 학습 계획
     * 들을 조회하여 Cursor 형태로 결과를 반환한
     * 다.
     * # 멤버변수로 selectedDate 가지고 있는데 파라미터에 쓰는 이유는 뭘까?
     */
    public Cursor selectStudyPlanListDB(Date selectedDate)
    {
        SQLiteDatabase database;
        database = openOrCreateDatabase(MainActivity.DB_NAME, MODE_PRIVATE, null);

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = fm.format(selectedDate);

        //SELECT * FROM study_plan_tb WHERE start_day <= 'currDate' AND end_day >= 'currDate'
        Cursor cursor = database.rawQuery("SELECT * FROM " +
                MainActivity.TB_NAME +
                " WHERE start_day <= '" +
                currDate +
                "' AND end_day >= '" +
                currDate +
                "'", null);

//        Toast.makeText(getApplicationContext(), Integer.toString(cursor.getCount()) , Toast.LENGTH_SHORT).show();
        return cursor;
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