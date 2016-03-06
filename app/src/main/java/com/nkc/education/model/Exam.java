package com.nkc.education.model;

/**
 * Created by itdep on 6/3/2559.
 */
public class Exam {
    private String Nox, Coursecode, Section, Studentid, Studentcode, Coursenameeng, DateMid, TimeBegin, TimeEnd, Runcode, chk, Classid;
    private String Codex, Enroll148, Prefixname, Studentname, Studentsurname, RoomID, Number, Programname, Studentyear, Acadyear, Semester;
    private String Financestatus, Programabbeng, ExamType, ExamYear, ExamYearX, BYTEDES, Comment;
    private Integer Running;

    public Exam(){}
    public Exam(String Nox, Integer Running, String Coursecode, String Section, String Studentid, String Studentcode, String Coursenameeng, String DateMid,
                String TimeBegin, String TimeEnd, String Runcode, String chk, String Classid, String Codex, String Enroll148, String Prefixname, String Studentname, String Studentsurname,
                String RoomID, String Number, String Programname, String Studentyear, String Acadyear, String Semester, String Financestatus, String Programabbeng,
                String ExamType, String ExamYear, String ExamYearX, String BYTEDES, String Comment){
        this.Nox = Nox;
        this.Coursecode = Coursecode;
        this.Section = Section;
        this.Studentid = Studentid;
        this.Studentcode = Studentcode;
        this.Coursenameeng = Coursenameeng;
        this.DateMid = DateMid;
        this.TimeBegin = TimeBegin;
        this.TimeEnd = TimeEnd;
        this.Runcode = Runcode;
        this.chk = chk;
        this.Classid = Classid;
        this.Running = Running;
        this.Codex = Codex;
        this.Enroll148 = Enroll148;
        this.Prefixname = Prefixname;
        this.Studentname = Studentname;
        this.Studentsurname= Studentsurname;
        this.RoomID = RoomID;
        this.Number = Number;
        this.Programname = Programname;
        this.Studentyear = Studentyear;
        this.Acadyear = Acadyear;
        this.Semester = Semester;
        this.Financestatus = Financestatus;
        this.Programabbeng = Programabbeng;
        this.ExamType = ExamType;
        this.ExamYear = ExamYear;
        this.ExamYearX = ExamYearX;
        this.BYTEDES = BYTEDES;
        this.Comment = Comment;
    }

    public void setCoursecode(String coursecode){
        this.Coursecode = coursecode;
    }

    public String getCoursecode(){
        return Coursecode;
    }

    public void setSection(String section){
        this.Section = section;
    }

    public String getSection(){
        return Section;
    }

    public void setCoursenameeng(String coursenameeng){
        this.Coursenameeng = coursenameeng;
    }

    public String getCoursenameeng(){
        return Coursenameeng;
    }

    public void setDateMid(String dateMid){
        this.DateMid = dateMid;
    }

    public String getDateMid(){
        return DateMid;
    }

    public void setTimeBegin(String timeBegin){
        this.TimeBegin = timeBegin;
    }

    public String getTimeBegin(){
        return TimeBegin;
    }

    public void setTimeEnd(String timeEnd){
        this.TimeEnd = timeEnd;
    }

    public String getTimeEnd(){
        return TimeEnd;
    }

    public void setRoomID(String roomID){
        this.RoomID = roomID;
    }

    public String getRoomID(){
        return RoomID;
    }

    public void setRunning(Integer running){
        this.Running = running;
    }

    public Integer getRunning(){
        return Running;
    }
}
