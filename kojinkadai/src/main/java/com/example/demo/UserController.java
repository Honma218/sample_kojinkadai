package com.example.demo;



import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.application.form.UserForm;
import com.example.demo.application.usecase.UserAuthUsecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserAuthUsecase userAuthUsecase;

  /**
   * 登録ページの表示
   * @return
   */
  @GetMapping("/signup")
  public ModelAndView signup(ModelAndView modelAndView) {
    modelAndView.setViewName("user/signup");
    modelAndView.addObject("userForm", new UserForm());

    return modelAndView;
  }

  /**
   * ユーザの登録処理
   * @param userForm
   * @param bindingResult
   * @return
   */
  @PostMapping("/signup")
  public ModelAndView register(
      @Validated @ModelAttribute UserForm userForm,
      BindingResult bindingResult,
      HttpServletRequest request
  ) {
    // パスワード一致チェック
    if (!userForm.isPasswordMatching()) {
      bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", 
          "パスワードが一致しません");
    }

    if(bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("user/signup");
      modelAndView.addObject("userForm", userForm);
      return modelAndView;
    }

    try {
      userAuthUsecase.userCreate(userForm, request);
    } catch (IllegalArgumentException e) {
      ModelAndView modelAndView = new ModelAndView("user/signup");
      modelAndView.addObject("userForm", userForm);
      modelAndView.addObject("errorMessage", e.getMessage());
      return modelAndView;
    } catch (Exception e) {
      log.error("ユーザ作成 or ログイン失敗", e);
      ModelAndView modelAndView = new ModelAndView("user/signup");
      modelAndView.addObject("userForm", userForm);
      modelAndView.addObject("errorMessage", "登録に失敗しました。しばらく時間をおいて再度お試しください。");
      return modelAndView;
    }

    return new ModelAndView("redirect:/user/signup-complete");
  }
  /**
   * ユーザー登録完了画面の表示
   * @return
   */
  @GetMapping("/signup-complete")
  public ModelAndView signupComplete(ModelAndView modelAndView) {
    modelAndView.setViewName("user/signup-complete");
    return modelAndView;
  }

  @GetMapping
  public ModelAndView loginPage(ModelAndView modelAndView) {
    modelAndView.setViewName("user/login");
    modelAndView.addObject("userForm", new UserForm());

    return modelAndView;
  }
}



