package com.nkc.education.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nkc.education.R;

import org.w3c.dom.Text;

/**
 * Created by Jumpon-pc on 15/2/2559.
 */
public class IconAdapter extends BaseAdapter {
    private Context context;
    private final String[] iconValues;

    public IconAdapter(Context context, String[] iconValues){
        this.context = context;
        this.iconValues = iconValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/thaisansneue-regular-webfont.ttf");
        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.grid_layout, null);

            TextView txtTitleEN = (TextView) gridView.findViewById(R.id.titleEN);
            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
            TextView txtTitleTH = (TextView) gridView.findViewById(R.id.titleTH);
            txtTitleEN.setText(iconValues[position]);
            txtTitleEN.setTypeface(typeFace);
            txtTitleTH.setTypeface(typeFace);

            //"{fa-calendar #3BAFDA 50sp}", "{fa-bullhorn #3BAFDA 50sp}", "{fa-envelope #3BAFDA 50sp}", "{fa-comments #3BAFDA 50sp}", "{fa-phone #3BAFDA 50sp}", "{fa-globe #3BAFDA 50sp}", "{fa-info #3BAFDA 50sp}", "{fa-power-off #3BAFDA 50sp}"};
            String icon = iconValues[position];

            if (icon.equals("Exam Schedule")) {
                textView.setText("{fa-calendar #3BAFDA 50sp}");
                txtTitleTH.setText("ตรวจสอบตารางสอบ");
            } else if (icon.equals("News")) {
                textView.setText("{fa-bullhorn #3BAFDA 50sp}");
                txtTitleTH.setText("ข่าวประชาสัมพันธ์");
            } else if (icon.equals("Inbox")) {
                textView.setText("{fa-envelope #3BAFDA 50sp}");
                txtTitleTH.setText("กล่องข้อความเข้า");
            } else if (icon.equals("Feedback")) {
                textView.setText("{fa-bug #3BAFDA 50sp}");
                txtTitleTH.setText("รายงานข้อผิดพลาด");
            } else if (icon.equals("Documents Service")) {
                textView.setText("{fa-book #3BAFDA 50sp}");
                txtTitleTH.setText("เอกสารทางการศึกษา");
            } else if (icon.equals("Links")) {
                textView.setText("{fa-globe #3BAFDA 50sp}");
                txtTitleTH.setText("เว็บไซต์ที่เกี่ยวข้อง");
            } else if (icon.equals("About")) {
                textView.setText("{fa-info #3BAFDA 50sp}");
                txtTitleTH.setText("เกี่ยวกับโปรแกรม");
            } else if (icon.equals("Log out")) {
                textView.setText("{fa-power-off #3BAFDA 50sp}");
                txtTitleTH.setText("ออกจากระบบ");
            } else {
                textView.setText("");
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return iconValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
