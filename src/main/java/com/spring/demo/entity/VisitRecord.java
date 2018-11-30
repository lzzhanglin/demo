package com.spring.demo.entity;

public class VisitRecord {
    private Long visitorId;

    private String ipAddr;

    private Long visitFreq;

    private String lastVistTime;

    public Long getVisitFreq() {
        return visitFreq;
    }

    public void setVisitFreq(Long visitFreq) {
        this.visitFreq = visitFreq;
    }

    public Long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getLastVistTime() {
        return lastVistTime;
    }

    public void setLastVistTime(String lastVistTime) {
        this.lastVistTime = lastVistTime;
    }
}
