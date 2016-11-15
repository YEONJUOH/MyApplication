package com.example.hong3.model;

import java.sql.Timestamp;

/**
 * Created by OHBABY on 2016-10-31.
 */
public class Audio {

     //DB 필수 정보
      int a_id ;
      String a_name;
      String a_loc;
      int s_id;
      String a_comment;


      String a_date;//audio 만들어 진 날짜
      String a_duration;//audio 시간

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_loc() {
        return a_loc;
    }

    public void setA_loc(String a_loc) {
        this.a_loc = a_loc;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getA_comment() {
        return a_comment;
    }

    public void setA_comment(String a_comment) {
        this.a_comment = a_comment;
    }

    public String getA_date() {
        return a_date;
    }

    public void setA_date(String a_date) {
        this.a_date = a_date;
    }

    public String getA_duration() {
        return a_duration;
    }

    public void setA_duration(String a_duration) {
        this.a_duration = a_duration;

    }
}
