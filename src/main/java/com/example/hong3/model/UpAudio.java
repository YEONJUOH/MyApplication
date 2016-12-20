package com.example.hong3.model;

/**
 * Created by OHBABY on 2016-12-20.
 */
public class UpAudio {

    String s_loc;
    String s_id;
    String score;
    String s_name;
    String a_id;

    public UpAudio(String s_loc, String s_id, String score, String s_name, String a_id) {
        this.s_loc = s_loc;
        this.s_id = s_id;
        this.score = score;
        this.s_name = s_name;
        this.a_id = a_id;
    }

    public UpAudio() {
    }

    public String getS_loc() {
        return s_loc;
    }

    public void setS_loc(String s_loc) {
        this.s_loc = s_loc;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getA_id() {
        return a_id;
    }

    public void setA_id(String a_id) {
        this.a_id = a_id;
    }
}
