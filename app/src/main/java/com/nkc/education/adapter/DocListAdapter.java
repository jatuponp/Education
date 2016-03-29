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
import com.nkc.education.model.Document;
import com.nkc.education.model.Exam;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by itdep on 6/3/2559.
 */
public class DocListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Document> docItems;
    private Context context;

    public DocListAdapter(Activity activity, List<Document> docItems) {
        this.activity = activity;
        this.docItems = docItems;
    }

    @Override
    public int getCount() {
        return docItems.size();
    }

    @Override
    public Object getItem(int location) {
        return docItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String status = "";
        Typeface typeFace = Typeface.createFromAsset(activity.getAssets(), "fonts/thaisansneue-regular-webfont.ttf");
        Typeface typeFaceBold = Typeface.createFromAsset(activity.getAssets(), "fonts/thaisansneue-bold-webfont.ttf");

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_doc, null);

        TextView txtFeeid = (TextView) convertView.findViewById(R.id.txtFeeid);
        TextView txtRequest = (TextView) convertView.findViewById(R.id.txtRequestDate);
        TextView txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);

        Document d = docItems.get(position);
        if(!d.getFeeidweb().equals("")) {
            txtFeeid.setText(d.getFeeidweb().trim());
        }else{
            txtFeeid.setText(d.getFeeidname().trim());
        }
        txtRequest.setText("วันที่ยื่นเรื่อง: " + d.getRequestdate());

        if(d.getStatus().equals("YES")){
            status = "ติดต่อรับเอกสาร";
        }else{
            status = "ระหว่างดำเนินการ";
        }
        txtStatus.setText("สถานะ: " + status);

        txtFeeid.setTypeface(typeFaceBold);
        txtRequest.setTypeface(typeFace);
        txtStatus.setTypeface(typeFace);

        return convertView;
    }
}
