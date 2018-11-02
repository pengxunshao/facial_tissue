package com.dida.facialtissue.controller;

import com.dida.facialtissue.commons.RedisTemplateHelper;
import com.dida.facialtissue.service.IWeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TestController {
    @Autowired
    IWeChatService iWeChatService;
    @Autowired
    RedisTemplateHelper redisTemplateHelper;
    @GetMapping("/")
    public String getHome(){
        return "search";
    }

    @GetMapping("/test/hello")
    @ResponseBody
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/test/search")
    @ResponseBody
    public String search(@RequestBody Map<String,Object> data){
        if(data == null || data.get("keyWord")==null){
            return "No content";
        }
        return data.get("keyWord").toString()+"(from server)";
    }

    public static void main(String[] args) {

    }
}
