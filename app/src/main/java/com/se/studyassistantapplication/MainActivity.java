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

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {

    public Calendar calendar;
    public ArrayList<StudyPlan> study_plan;
    public Date selected_date;
    public static final String DB_NAME = "study_plan_db";
    public static final String TB_NAME = "study_plan_tb";
    public static final int REQUEST_CODE_OPENSTUDYPLAN = 100;
    public static final int REQUEST_CODE_CREATESTUDYPLAN = 200;
    public static final int REQUEST_CODE_UPDATESTUDYPLAN = 300;

    public ArrayAdapter<StudyPlan> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showCalendar();
        ListView lv_listView = findViewById(R.id.planList);
        FloatingActionButton fab_newPlan = findViewById(R.id.newPlan);

        SQLiteDatabase database;
        database = openOrCreateDatabase(MainActivity.DB_NAME, MODE_PRIVATE, null);

        database.execSQL("CREATE TABLE IF NOT EXISTS " +
                TB_NAME
                + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ", title TEXT"
                + ", content TEXT"
                + ", start_day DATE"
                + ", end_day DATE"
                + ", status BOOLEAN"
                + ")");

        study_plan = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, study_plan);
        lv_listView.setAdapter(adapter);

        lv_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StudyPlan studyPlan = adapter.getItem(i);

                Intent intent = new Intent(getApplicationContext(), OpenStudyPlan.class);
                intent.putExtra("StudyPlan", studyPlan);
                startActivityForResult(intent, REQUEST_CODE_OPENSTUDYPLAN);
            }
        });

        fab_newPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCreateStudyPlan();
            }
        });

        SimpleDateFormat fm = new SimpleDateFormat("yyyy MM dd");
        String[] today = fm.format(new Date()).split(" ");

        selectDate(Integer.parseInt(today[0]), Integer.parseInt(today[1]) - 1, Integer.parseInt(today[2]));
        showStudyPlanList(selected_date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showStudyPlanList(selected_date);
    }

    public void showCalendar()
    {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.YEAR, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.YEAR, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                selected_date = date.getTime();
                showStudyPlanList(selected_date);
                Log.e("TAG", "CURRENT DATE IS " + date);
            }
        });
    }

    public void selectDate(int year, int month, int day)
    {
        calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0);
        selected_date = calendar.getTime();
    }

    public void showStudyPlanList(Date selectedDate)
    {
        Cursor cursor = selectStudyPlanListDB(selectedDate);
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

        study_plan.clear();
        adapter.notifyDataSetChanged();

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
            adapter.notifyDataSetChanged();
        }
    }

    public void clickCreateStudyPlan()
    {
        Intent intent = new Intent(getApplicationContext(), CreateStudyPlan.class);
        startActivityForResult(intent, REQUEST_CODE_CREATESTUDYPLAN);
    }

    public Cursor selectStudyPlanListDB(Date selectedDate)
    {
        SQLiteDatabase database;
        database = openOrCreateDatabase(MainActivity.DB_NAME, MODE_PRIVATE, null);

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = fm.format(selectedDate);

        Cursor cursor = database.rawQuery("SELECT * FROM " +
                MainActivity.TB_NAME +
                " WHERE start_day <= '" +
                currDate +
                "' AND end_day >= '" +
                currDate +
                "'", null);
        return cursor;
    }

}