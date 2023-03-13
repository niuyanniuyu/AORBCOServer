package com.aorbco.aorbcoserver.controller;

import com.aorbco.aorbcoserver.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Ben
 */
@Slf4j
@Controller
public class IndexController {

    @Resource
    BaseService indexService;

    @RequestMapping({"/","/index"})
    public String index(){
        return "/index.html";
    }


    @RequestMapping({"/sendMes"})
    @ResponseBody
    public String sendMes(String text){
        return indexService.processMes(text);
    }
}