package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home() {
        // ルートパスアクセス時はユーザー登録画面にリダイレクト
        return new ModelAndView("redirect:/user");
    }
}