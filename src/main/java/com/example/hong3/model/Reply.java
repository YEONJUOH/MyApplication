package com.example.hong3.model;

/**
 * Created by OHBABY on 2016-12-17.
 */
public class Reply {

    String m_id ;
    String content;

    public Reply(String m_id, String content) {
        this.m_id = m_id;
        this.content = content;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
