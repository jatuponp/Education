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
import com.nkc.education.model.Inboxs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jumpon-pc on 18/3/2559.
 */
public class InboxListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Inboxs> inboxItems;
    private Context context;

    public InboxListAdapter(Activity activity, List<Inboxs> inboxItems) {
        this.activity = activity;
        this.inboxItems = inboxItems;
    }

    @Override
    public int getCount() {
        return inboxItems.size();
    }

    @Override
    public Object getItem(int location) {
        return inboxItems.get(location);
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
            convertView = inflater.inflate(R.layout.grid_inbox, null);

        TextView txtInboxTitle = (TextView) convertView.findViewById(R.id.txtInboxTitle);
        TextView txtSendDate = (TextView) convertView.findViewById(R.id.txtSendDate);
        TextView txtInboxBody = (TextView) convertView.findViewById(R.id.txtInboxBody);

        Inboxs d = inboxItems.get(position);
        txtInboxTitle.setText(d.getMSubject());

        Calendar t = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt = sdf.parse(d.getMSendate());
            Locale locale = new Locale("th","TH");
            Locale.setDefault(locale);
            SimpleDateFormat sdf1 = new SimpleDateFormat("d MMM yyyy HH:mm", Locale.getDefault());
            txtSendDate.setText(sdf1.format(dt));
        }catch (ParseException e){
            txtSendDate.setText("");
        }
        txtInboxBody.setText(d.getMBody());
        txtInboxTitle.setTypeface(typeFaceBold);
        txtSendDate.setTypeface(typeFace);
        txtInboxBody.setTypeface(typeFace);

        if(d.getMRead() == 0){
            convertView.setBackgroundResource(R.color.inboxUnread);
        }

        return convertView;
    }
}
