package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class OneWayActivity extends AppCompatActivity {
    private DateUtil dateUtil;
    private NumberPicker datePicker;
    private TimePicker timePicker;
    private Button btnRound;
    private Button btnGetTime;
    private String[] datesArray;
    private TextView tvSelectedDate;
    private TextView tvReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_way);
        this.init();
        this.initDatePicker();
        this.clickEvent();
    }

    private void init() {
        dateUtil = DateUtil.getInstance();
        timePicker = findViewById(R.id.time_picker);
        datePicker = findViewById(R.id.date_picker);
        btnRound = findViewById(R.id.bt_round);
        btnGetTime= findViewById(R.id.btn_get_time);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        tvReset = findViewById(R.id.tv_reset);
    }

    private void initDatePicker() {
        datesArray = dateUtil.getDates(dateUtil.getInitialDate(), dateUtil.getFinalDate());
        datePicker.setMaxValue(datesArray.length-1);
        datePicker.setMinValue(0);
        datePicker.setDisplayedValues(datesArray);
        datePicker.setWrapSelectorWheel(false);
        setSelectedDate();
    }

    private void clickEvent() {
        btnGetTime.setOnClickListener(v -> {
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            String AM_PM = (hour >=0 && hour < 12) ? "AM" : "PM";
            String Date = datesArray[datePicker.getValue()];
        });

        tvReset.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            timePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
            datePicker.setValue(0);
            setSelectedDate();
        });

        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            setSelectedDate();
        });

        datePicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            setSelectedDate();
        });

        btnRound.setOnClickListener(v -> {
            startActivity(new Intent(this, RoundTripActivity.class));
        });
    }

    private void setSelectedDate() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        String AM_PM = (hour >=0 && hour < 12) ? "AM" : "PM";
        String date = "Starting " + datesArray[datePicker.getValue()]+ " " + dateUtil.formatHour(hour) + ":"+ minute + " " + AM_PM;
        tvSelectedDate.setText(date);
    }
}