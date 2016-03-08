package com.nkc.education.model;

/**
 * Created by Jumpon-pc on 7/3/2559.
 */
public class Document {
    private Integer Autoid,Semester;
    private String Batchno, Requestdate, Studentcode, Studentname, Studentsurname, Acadyear, Feeid, Feeidname, Feeidweb, Quantity, Reason, Remark, Status;

    public Document(){}
    public Document(Integer Autoid, String Batchno, String Requestdate, String Studentcode, String Studentname, String Studentsurname,
                    String Acadyear, Integer Semester, String Feeid, String Feeidname, String Feeidweb, String Quantity, String Reason,
                    String Remark, String Status){
        this.Autoid = Autoid;
        this.Batchno = Batchno;
        this.Requestdate = Requestdate;
        this.Studentcode = Studentcode;
        this.Studentname = Studentname;
        this.Studentsurname = Studentsurname;
        this.Acadyear = Acadyear;
        this.Semester = Semester;
        this.Feeid = Feeid;
        this.Feeidname = Feeidname;
        this.Feeidweb = Feeidweb;
        this.Quantity = Quantity;
        this.Reason = Reason;
        this.Remark = Remark;
        this.Status = Status;
    }



    public void setFeeidname(String feeidname){
        this.Feeidname = feeidname;
    }

    public String getFeeidname(){
        return Feeidname;
    }

    public void setFeeidweb(String feeidweb){
        this.Feeidweb = feeidweb;
    }

    public String getFeeidweb(){
        return Feeidweb;
    }
    public void setRequestdate(String requestdate){
        this.Requestdate = requestdate;
    }

    public String getRequestdate(){
        return Requestdate;
    }
    public void setStatus(String status){
        this.Status = status;
    }

    public String getStatus(){
        return Status;
    }
}
