package com.aorbco.aorbcoserver.controller;

import com.aorbco.aorbcoserver.service.impl.BaseServiceImpl;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
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
    BaseServiceImpl indexService;

    @RequestMapping({"/", "/index"})
    public String index() {
        return "/index.html";
    }

    @RequestMapping({"/sendMes"})
    @ResponseBody
    public String sendMes(String text) {
        //return CoreSynonymDictionary.rewrite(indexService.processMes(text));
        return indexService.processMes(text);
    }

    /**
     * 用户点击url后会将对应的url返回
     *
     * @param url
     */
    @RequestMapping({"/onclickUrl"})
    @ResponseBody
    public String onclickUrl(String url) {
        return indexService.onclickUrl(url);
    }
}