package com.example.myapplication;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;

public class DialogActivity extends AppCompatActivity {
    private DateUtil dateUtil;
    private NumberPicker datePicker;
    private TimePicker timePicker;
    private Button btnRound;
    private String[] datesArray;
    private TextView tvSelectedDateOneWay;
    private NumberPicker leaveDatePicker;
    private TimePicker leaveTimePicker;
    private NumberPicker returnDatePicker;
    private TimePicker returnTimePicker;
    private Button btnOneWay;
    private Button btnGetTimeRoundTrip;
    private TextView tvSelectedDateRoundTrip;
    private TextView tvLeave;
    private TextView tvReturn;
    private LinearLayout llLeave;
    private LinearLayout llReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        this.init();
        this.clickEvent();
    }

    private void init() {
        dateUtil = DateUtil.getInstance();
        btnOneWay = findViewById(R.id.bt_one_way);
        btnRound = findViewById(R.id.bt_round);
    }

    private void clickEvent() {
        btnOneWay.setOnClickListener(v -> {
            oneWayDialog();
        });
        btnRound.setOnClickListener(v -> {
            roundTripDialog();
        });
    }

    private void oneWayDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.SheetDialog);
        dialog.setContentView(dialog.getLayoutInflater().inflate(R.layout.one_way_bottom_layout,null,false));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationBottom;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePicker = dialog.findViewById(R.id.time_picker);
        datePicker = dialog.findViewById(R.id.date_picker);
        Button btnGetTimeOneWay = dialog.findViewById(R.id.btn_get_time);
        tvSelectedDateOneWay = dialog.findViewById(R.id.tv_selected_date);
        TextView tvReset = dialog.findViewById(R.id.tv_reset);

        datesArray = dateUtil.getDates(dateUtil.getInitialDate(), dateUtil.getFinalDate());
        datePicker.setMaxValue(datesArray.length-1);
        datePicker.setMinValue(0);
        datePicker.setDisplayedValues(datesArray);
        datePicker.setWrapSelectorWheel(false);
        setSelectedDate();
        assert btnGetTimeOneWay != null;
        btnGetTimeOneWay.setOnClickListener(v -> {
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            String AM_PM = (hour >=0 && hour < 12) ? "AM" : "PM";
            String Date = datesArray[datePicker.getValue()];
        });

        assert tvReset != null;
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

        dialog.show();
    }

    private void roundTripDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.SheetDialog);
        dialog.setContentView(dialog.getLayoutInflater().inflate(R.layout.round_trip_bottom_layout,null,false));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationBottom;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        leaveTimePicker = dialog.findViewById(R.id.leave_time_picker);
        leaveDatePicker = dialog.findViewById(R.id.leave_date_picker);
        returnTimePicker = dialog.findViewById(R.id.return_time_picker);
        returnDatePicker = dialog.findViewById(R.id.return_date_picker);
        btnGetTimeRoundTrip= dialog.findViewById(R.id.btn_get_time);
        tvSelectedDateRoundTrip = dialog.findViewById(R.id.tv_selected_date);
        tvLeave = dialog.findViewById(R.id.tv_leave);
        tvReturn = dialog.findViewById(R.id.tv_return);
        llLeave = dialog.findViewById(R.id.ll_leave);
        llReturn = dialog.findViewById(R.id.ll_return);

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

        tvLeave.setOnClickListener(v -> {
            tvLeave.setTextColor(getResources().getColor(R.color.black));
            tvReturn.setTextColor(getResources().getColor(R.color.grey));
            llLeave.setVisibility(View.VISIBLE);
            llReturn.setVisibility(View.GONE);
            btnGetTimeRoundTrip.setText("Next");
        });

        tvReturn.setOnClickListener(v -> {
            tvReturn.setTextColor(getResources().getColor(R.color.black));
            tvLeave.setTextColor(getResources().getColor(R.color.grey));
            llReturn.setVisibility(View.VISIBLE);
            llLeave.setVisibility(View.GONE);
            btnGetTimeRoundTrip.setText("Confirm");
            setSelectedDate(true);
        });

        btnGetTimeRoundTrip.setOnClickListener(v -> {
            if(btnGetTimeRoundTrip.getText().toString().trim().equalsIgnoreCase("Next")) {
                setSelectedDate(false);
                tvReturn.setTextColor(getResources().getColor(R.color.black));
                tvLeave.setTextColor(getResources().getColor(R.color.grey));
                llReturn.setVisibility(View.VISIBLE);
                llLeave.setVisibility(View.GONE);
                btnGetTimeRoundTrip.setText("Confirm");
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
        dialog.show();
    }

    private void setSelectedDate() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        String AM_PM = (hour >=0 && hour < 12) ? "AM" : "PM";
        String date = "Starting " + datesArray[datePicker.getValue()]+ " " + dateUtil.formatHour(hour) + ":"+ minute + " " + AM_PM;
        tvSelectedDateOneWay.setText(date);
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
        tvSelectedDateRoundTrip.setText(date);
    }
}