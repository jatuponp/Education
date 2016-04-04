package com.nkc.education.model;

/**
 * Created by Jumpon-pc on 18/3/2559.
 */
public class Inboxs {
    private Integer ids;
    private String MForm, MTo, MSubject, MBody, MRead, MReadDate, MSendDate;

    public Inboxs(){}
    public Inboxs(Integer ids,String MForm, String MTo, String MSubject, String MBody, String MRead, String MReadDate, String MSendDate){
        this.ids = ids;
        this.MForm = MForm;
        this.MTo = MTo;
        this.MSubject = MSubject;
        this.MBody = MBody;
        this.MRead = MRead;
        this.MReadDate = MReadDate;
        this.MSendDate = MSendDate;
    }

    public void setId(Integer ids){
        this.ids = ids;
    }

    public Integer getId(){
        return ids;
    }

    public void setMForm(String MForm){
        this.MForm = MForm;
    }
    public String getMForm(){
        return MForm;
    }

    public void setMTo(String MTo){
        this.MTo = MTo;
    }
    public String getMTo(){
        return MTo;
    }

    public void setMSubject(String MSubject){
        this.MSubject = MSubject;
    }
    public String getMSubject(){
        return MSubject;
    }

    public void setMBody(String MBody){
        this.MBody = MBody;
    }
    public String getMBody(){
        return MBody;
    }

    public void setMRead(String MRead){
        this.MRead = MRead;
    }
    public String getMRead(){
        return MRead;
    }

    public void setMReadDate(String MReadDate){
        this.MRead = MReadDate;
    }
    public String getMReadDate(){
        return MReadDate;
    }

    public void setMSendDate(String MSendDate){
        this.MSendDate = MSendDate;
    }
    public String getMSendate(){
        return MSendDate;
    }

}
