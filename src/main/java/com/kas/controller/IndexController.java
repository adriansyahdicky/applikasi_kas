package com.kas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("")
    private String redirectIndex(){
        return "redirect:dashboard";
    }
}
