package com.face.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

    @RequestMapping("/404")
    public String error404(){
        return "404";
    }

    @RequestMapping("")
    public String index(){
        return "index";
    }

}
