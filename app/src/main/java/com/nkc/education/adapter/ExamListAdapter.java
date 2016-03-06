package com.nkc.education.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nkc.education.R;
import com.nkc.education.model.Exam;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by itdep on 6/3/2559.
 */
public class ExamListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Exam> examItems;
    private Context context;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ExamListAdapter(Activity activity, List<Exam> roomItems) {
        this.activity = activity;
        this.examItems = roomItems;
    }

    @Override
    public int getCount() {
        return examItems.size();
    }

    @Override
    public Object getItem(int location) {
        return examItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Typeface typeFace = Typeface.createFromAsset(activity.getAssets(), "fonts/thaisansneue-regular-webfont.ttf");

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_exam, null);

        TextView courseCode = (TextView) convertView.findViewById(R.id.courseCode);
        TextView courseName = (TextView) convertView.findViewById(R.id.courseName);
        TextView txtDay = (TextView) convertView.findViewById(R.id.txtDay);
        TextView txtMonth = (TextView) convertView.findViewById(R.id.txtMonth);
        TextView txtDateTime = (TextView) convertView.findViewById(R.id.txtDateTime);
        TextView txtRoom = (TextView) convertView.findViewById(R.id.txtRoom);

        // getting movie data for the row
        Exam e = examItems.get(position);

        String[] DateMid = e.getDateMid().split("-");
        txtDay.setText(DateMid[2].trim());
        txtDay.setTypeface(typeFace);

        TimeZone timezone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timezone);
        calendar.set(Integer.valueOf(DateMid[0]), Integer.valueOf(DateMid[1]), 1, 1, 1, 1);
        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        txtMonth.setText(monthName);
        txtMonth.setTypeface(typeFace);

        courseCode.setText(e.getCoursecode() + " (Sec. " + e.getSection() + ")");
        courseCode.setTypeface(typeFace);
        courseName.setText(e.getCoursenameeng());
        courseName.setTypeface(typeFace);

        txtDateTime.setText(DateMid[2] + " " + monthName + " " + (Integer.valueOf(DateMid[0]) + 543) + " เวลา:" + e.getTimeBegin() + "-" + e.getTimeEnd());
        txtDateTime.setTypeface(typeFace);

        txtRoom.setText("ห้องสอบ: " + e.getRoomID() + " เลขที่นั้งสอบ: " + e.getRunning());
        txtRoom.setTypeface(typeFace);

        // meter_start
        //meter_start.setText(e.getMeterStart());

        // meter_end
        //meter_end.setText(e.getMeterEnd());

        return convertView;
    }
}
