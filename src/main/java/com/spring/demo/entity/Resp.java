package com.spring.demo.entity;

public class Resp {
    private String status;

    private String msg;

    private String data;



    public Resp(String status, String msg, String data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Resp() {
    }

    public Resp(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
