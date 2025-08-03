package com.example.demo;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("statusCode", statusCode);
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                modelAndView.addObject("errorMessage", "お探しのページが見つかりません");
                modelAndView.addObject("errorDescription", "URLが正しいかご確認ください");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                modelAndView.addObject("errorMessage", "サーバーエラーが発生しました");
                modelAndView.addObject("errorDescription", "しばらく時間をおいて再度お試しください");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                modelAndView.addObject("errorMessage", "アクセス権限がありません");
                modelAndView.addObject("errorDescription", "このページにアクセスする権限がありません");
            } else {
                modelAndView.addObject("errorMessage", "エラーが発生しました");
                modelAndView.addObject("errorDescription", "何らかの問題が発生しました");
            }
            
            return modelAndView;
        }
        
        // デフォルトのエラーページ
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("statusCode", 500);
        modelAndView.addObject("errorMessage", "エラーが発生しました");
        modelAndView.addObject("errorDescription", "予期しないエラーが発生しました");
        return modelAndView;
    }
}