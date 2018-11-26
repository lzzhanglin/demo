package com.spring.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class BaseErrorController implements ErrorController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String getErrorPath() {
        logger.info("进入自定义错误页面");
        return "error";
    }

    @RequestMapping
    public String error() {
        return getErrorPath();
    }


}
