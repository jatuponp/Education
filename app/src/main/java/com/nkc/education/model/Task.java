package com.nkc.education.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Jumpon-pc on 28/3/2559.
 */
public class Task {
    /**************************************************************************
     * Static fields and methods                                              *
     **************************************************************************/

    // Extra intent flags
    public static final String EXTRA_TASK = "com.nkc.education.TASK";
    public static final String EXTRA_TASK_ID = "com.nkc.education.TASK_ID";

    // Priority constants
    public static final String[] PRIORITY_LABELS = {"Trivial", "Normal", "Urgent"};
    public static final String[] REPEAT_LABELS = {"minutes", "hours", "days", "weeks", "months", "years"};
    public static final int TRIVIAL = 0;
    public static final int NORMAL = 1;
    public static final int URGENT = 2;

    // Repeat constants
    public static final int MINUTES = 0;
    public static final int HOURS = 1;
    public static final int DAYS = 2;
    public static final int WEEKS = 3;
    public static final int MONTHS = 4;
    public static final int YEARS = 5;

    /**************************************************************************
     * Private fields                                                         *
     **************************************************************************/

    private int id;
    private String name;
    private boolean isCompleted;
    private int priority;
    private int category;
    private boolean hasDateDue;
    private boolean hasFinalDateDue;
    private boolean isRepeating;
    private int repeatType;
    private int repeatInterval;
    private long dateCreated;
    private long dateModified;
    private long dateDue;
    private String gID;
    private String notes;
    private Calendar dateCreatedCal;
    private Calendar dateModifiedCal;
    private Calendar dateDueCal;
    private String title;
    private String detail;
    private String dateMid;

    /**************************************************************************
     * Constructors                                                           *
     **************************************************************************/

    /**
     * Default constructor. Creates an empty task.
     */
    public Task(){

    }

    /**
     * Constructor, all fields
     * @param id
     * @param name
     * @param isCompleted
     * @param dateDue
     */
    public Task(int id,
                String name,
                boolean isCompleted,
                long dateDue,
                boolean notType,
                String detail,
                String timeend
                ) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
        this.dateDue = dateDue;
        if(notType) {
            this.title = "พรุ่งนี้สอบปลายภาค";
        }else{
            this.title = "อีก 1 ชั่วโมงถึงเวลาเข้าห้องสอบ";
        }
        Calendar t = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt = sdf.parse(detail);
            t.setTime(dt);
            Date resultdate = new Date(t.getTimeInMillis());
            Locale locale = new Locale("th","TH");
            Locale.setDefault(locale);
            SimpleDateFormat sdf1 = new SimpleDateFormat("d MMMM yyyy เวลา: HH:mm",Locale.getDefault());
            this.detail = sdf1.format(resultdate) + "-" + timeend;
        }catch (ParseException e){
            Log.e("Error:", e.toString());
        }

        updateDateDueCal();
    }

    /**************************************************************************
     * Class methods                                                          *
     **************************************************************************/

    private void updateDateCreatedCal() {
        if (dateCreatedCal == null)
            dateCreatedCal = new GregorianCalendar();

        dateCreatedCal.setTimeInMillis(dateCreated);
    }

    private void updateDateModifiedCal() {
        if (dateModifiedCal == null)
            dateModifiedCal = new GregorianCalendar();

        dateModifiedCal.setTimeInMillis(dateModified);
    }

    private void updateDateDueCal() {
        if (!hasDateDue)
        {
            dateDueCal = null;
            return;
        }

        if (dateDueCal == null)
            dateDueCal = new GregorianCalendar();

        dateDueCal.setTimeInMillis(dateDue);
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public boolean isPastDue(){
        if (!hasDateDue || isCompleted)
            return false;

        return dateDue - System.currentTimeMillis() < 0;
    }

    public boolean isRepeating(){
        return isRepeating;
    }

    public void setIsRepeating(boolean isRepeating){
        this.isRepeating = isRepeating;
    }

    public boolean isCompleted(){
        return isCompleted;
    }

    public void setIsCompleted(boolean is_completed){
        this.isCompleted = is_completed;
    }

    public long getDateDue(){
        return dateDue;
    }

    public Calendar getDateDueCal(){
        return dateDueCal;
    }

    public void setDateDue(long date_due){
        this.hasDateDue = true;
        this.dateDue = date_due;
        updateDateDueCal();
    }

    public int getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(int repeatType) {
        if (repeatType >= 0 && repeatType <= 5) {
            this.isRepeating = true;
            this.repeatType = repeatType;
        }
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public long getDateModified() {
        return dateModified;
    }

    public Calendar getDateModifiedCal() {
        return dateModifiedCal;
    }

    public void setDateModified(long date_modified) {
        this.dateModified = date_modified;
        updateDateModifiedCal();
    }

    public boolean hasFinalDateDue() {
        return hasFinalDateDue;
    }

    public void setHasFinalDateDue(boolean hasFinalDateDue) {
        this.hasFinalDateDue = hasFinalDateDue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDetail(){
        return detail;
    }

    public void setDetail(String detail){
        this.detail = detail;
    }
}
