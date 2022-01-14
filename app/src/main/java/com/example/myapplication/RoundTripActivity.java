package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class RoundTripActivity extends AppCompatActivity {
    private DateUtil dateUtil;
    private NumberPicker leaveDatePicker;
    private TimePicker leaveTimePicker;
    private NumberPicker returnDatePicker;
    private TimePicker returnTimePicker;
    private Button btnOne;
    private Button btnGetTime;
    private String[] datesArray;
    private TextView tvSelectedDate;
    private TextView tvLeave;
    private TextView tvReturn;
    private LinearLayout llLeave;
    private LinearLayout llReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_trip);
        this.init();
        this.initDatePicker();
        this.clickEvent();
    }

    private void init() {
        dateUtil = DateUtil.getInstance();
        leaveTimePicker = findViewById(R.id.leave_time_picker);
        leaveDatePicker = findViewById(R.id.leave_date_picker);
        returnTimePicker = findViewById(R.id.return_time_picker);
        returnDatePicker = findViewById(R.id.return_date_picker);
        btnOne = findViewById(R.id.bt_one);
        btnGetTime= findViewById(R.id.btn_get_time);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        tvLeave = findViewById(R.id.tv_leave);
        tvReturn = findViewById(R.id.tv_return);
        llLeave = findViewById(R.id.ll_leave);
        llReturn = findViewById(R.id.ll_return);
    }

    private void initDatePicker() {
        datesArray = dateUtil.getDates(dateUtil.getInitialDate(), dateUtil.getFinalDate());
        leaveDatePicker.setMaxValue(datesArray.length-1);
        leaveDatePicker.setMinValue(0);
        leaveDatePicker.setDisplayedValues(datesArray);
        leaveDatePicker.setWrapSelectorWheel(false);
        returnDatePicker.setMaxValue(datesArray.length-1);
        returnDatePicker.setMinValue(0);
        returnDatePicker.setDisplayedValues(datesArray);
        returnDatePicker.setWrapSelectorWheel(false);
        setSelectedDate(false);
    }

    private void clickEvent() {
        tvLeave.setOnClickListener(v -> {
            tvLeave.setTextColor(getResources().getColor(R.color.black));
            tvReturn.setTextColor(getResources().getColor(R.color.grey));
            llLeave.setVisibility(View.VISIBLE);
            llReturn.setVisibility(View.GONE);
        });

        tvReturn.setOnClickListener(v -> {
            tvReturn.setTextColor(getResources().getColor(R.color.black));
            tvLeave.setTextColor(getResources().getColor(R.color.grey));
            llReturn.setVisibility(View.VISIBLE);
            llLeave.setVisibility(View.GONE);
        });

        btnGetTime.setOnClickListener(v -> {
            if(btnGetTime.getText().toString().trim().equalsIgnoreCase("Next")) {
              setSelectedDate(false);
                tvReturn.setTextColor(getResources().getColor(R.color.black));
                tvLeave.setTextColor(getResources().getColor(R.color.grey));
                llReturn.setVisibility(View.VISIBLE);
                llLeave.setVisibility(View.GONE);
                btnGetTime.setText("Confirm");
            } else {
            }
        });


        leaveTimePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            setSelectedDate(false);
        });

        leaveDatePicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            setSelectedDate(false);
        });

        returnTimePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            setSelectedDate(true);
        });

        returnDatePicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            setSelectedDate(true);
        });

        btnOne.setOnClickListener(v -> {
            finish();
        });
    }

    private void setSelectedDate(boolean isShowReturn) {
        String date;
        int leaveHour = leaveTimePicker.getCurrentHour();
        int leaveMinute = leaveTimePicker.getCurrentMinute();
        String amPmLeave = (leaveHour >=0 && leaveHour < 12) ? "AM" : "PM";
        String dateLeave = datesArray[leaveDatePicker.getValue()]+ ", " + dateUtil.formatHour(leaveHour) + ":"+ leaveMinute + " " + amPmLeave;
        date = "Starting " + dateLeave;
        if(isShowReturn) {
            int returnHour = returnTimePicker.getCurrentHour();
            int returnMinute = returnTimePicker.getCurrentMinute();
            String amPmReturn = (leaveHour >= 0 && leaveHour < 12) ? "AM" : "PM";
            String dateReturn = datesArray[returnDatePicker.getValue()] + ", " + dateUtil.formatHour(returnHour) + ":" + returnMinute + " " + amPmReturn;
            date = dateLeave + " - " + dateReturn;
        }
        tvSelectedDate.setText(date);
    }
}