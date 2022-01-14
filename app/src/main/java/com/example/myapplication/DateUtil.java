package com.example.myapplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtil {

    public static DateUtil instance = null;
    private DateUtil() {
    }

    public static DateUtil getInstance() {
        if(null == instance) {
            instance = new DateUtil();
        }
        return instance;
    }

    Date getInitialDate() {
        final Calendar c = Calendar.getInstance();
        c.get(Calendar.YEAR);
        c.get(Calendar.MONTH);
        c.get(Calendar.DAY_OF_MONTH);
        return c.getTime();
    }

    Date getFinalDate() {
        final Calendar c = Calendar.getInstance();
        c.get(Calendar.YEAR);
        c.get(Calendar.MONTH);
        c.get(Calendar.DAY_OF_MONTH);
        c.add(Calendar.DAY_OF_MONTH, 6);
        return c.getTime();
    }

    int formatHour(int hour){
        return (hour %= 12) == 0 ? 12 : hour;
    }

    String[] getDates(Date initialDate, Date finalDate) {
        DateFormat dateFormat = new SimpleDateFormat("EEE dd MMM", Locale.US);
        List<String> dates = new ArrayList<>();
        String[] datesArray;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);

        while (calendar.getTime().before(finalDate)) {
            Date result = calendar.getTime();
            dates.add(dateFormat.format(result));
            calendar.add(Calendar.DATE, 1);
        }
        datesArray = new String[dates.size()];
        datesArray = dates.toArray(datesArray);
        return datesArray;
    }

}
