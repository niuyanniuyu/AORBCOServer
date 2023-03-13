package com.aorbco.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ben
 */
@Slf4j
@Controller
public class IndexController {
    @GetMapping(value="/index")
    public String view(HttpServletRequest request){
        log.info("进入测试页面");
        return "/index.html";
    }
}