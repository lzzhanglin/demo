package com.spring.demo.mapper;

import com.spring.demo.entity.VisitRecord;

public interface VisitMapper {

    public int addVisitRecord(VisitRecord visitRecord);

    public int updateVisitFreq(Long visitorId);

    public Long getVisitorId(String ipAddr);
}
