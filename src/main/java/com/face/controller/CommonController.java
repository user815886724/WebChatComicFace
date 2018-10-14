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

    @RequestMapping("/create_face_data")
    public String create(){
        return "create_data";
    }

    @RequestMapping("/face_data_list")
    public String list(){
        return "face_data_list";
    }

    @RequestMapping("/create_face_shape")
    public String createFaceShape(){return "create_face_shape";}

}
