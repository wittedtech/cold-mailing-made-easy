package com.wittedtech.mailSender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplateController {
    @GetMapping(value ="/")
    public ModelAndView xlsxFileTemplate(){
    	ModelAndView model =new ModelAndView();
    	
		model.setViewName("fileupload");
        return model;
    }
}
