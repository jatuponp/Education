package com.nkc.education.model;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
     * @param priority
     * @param category
     * @param hasDateDue
     * @param hasFinalDateDue
     * @param isRepeating
     * @param repeatType
     * @param repeatInterval
     * @param dateCreated
     * @param dateModified
     * @param dateDue
     * @param gID
     * @param notes
     */
    public Task(int id,
                String name,
                boolean isCompleted,
                long dateDue,
                int notType
                ) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
        /*this.priority = priority;
        this.category = category;
        this.hasDateDue = hasDateDue;
        this.hasFinalDateDue = hasFinalDateDue;
        this.isRepeating = isRepeating;
        this.repeatType = repeatType;
        this.repeatInterval = repeatInterval;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;*/
        this.dateDue = dateDue;
        this.title = ((notType == 0)? "พรุ่งนี้สอบปลายภาค":"อีก 1 ชั่วโมงถึงเวลาเข้าห้องสอบ");
        /*this.gID = gID;
        this.notes = notes;*/

        //updateDateCreatedCal();
        //updateDateModifiedCal();
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
}
