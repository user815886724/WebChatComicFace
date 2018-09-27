package com.face.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
