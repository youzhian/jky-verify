package com.jky.verify.modules.main.controller;

import com.jky.verify.modules.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 主页controller
 * @author youzhian
 */
@Controller
public class MainController extends BaseController {

    @GetMapping("/jky/login")
    public Object login(String token){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

}
