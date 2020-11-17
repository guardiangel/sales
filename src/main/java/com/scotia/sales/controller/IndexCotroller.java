package com.scotia.sales.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexCotroller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String root() {
        logger.debug("进入到IndexCotroller的root方法");
        return "redirect:/login.html";
    }
}
