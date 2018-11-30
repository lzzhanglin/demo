package com.spring.demo.service.serviceImpl;

import com.spring.demo.entity.VisitRecord;
import com.spring.demo.mapper.VisitMapper;
import com.spring.demo.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service("visitService")
public class VisitServiceImpl implements VisitService {


    @Autowired
    private VisitMapper visitMapper;

    public void addOrUpdateVisitRecord(VisitRecord visitRecord) {
        Long vId = visitMapper.getVisitorId(visitRecord.getIpAddr());
        if (Objects.equals("", vId) || Objects.equals(null, vId)) {
            visitMapper.addVisitRecord(visitRecord);
        } else {
            visitMapper.updateVisitFreq(vId);
        }
    }
}
