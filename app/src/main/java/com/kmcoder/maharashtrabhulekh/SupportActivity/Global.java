package com.kmcoder.maharashtrabhulekh.SupportActivity;

import java.util.Date;

public class Global {
    private static Global instance;

    // Global variable
    private Date data;
    private boolean f=true;

    // Restrict the constructor from being instantiated
    private Global(){}


    //Single instance of this class
    public static synchronized Global getInstance(){
        if(instance==null){
            instance=new Global();
        }
        return instance;
    }

    public void setData(Date d){
        this.data=d;

    }

    public void setDataFlagfalse(){
        this.f=false;

    }
    public boolean getDataflag(){
        return this.f;
    }

    public Date getData(){
        return this.data;
    }


}
