package com.example.demo;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.application.usecase.FollowUseCase;
import com.example.demo.application.usecase.UserProfileUseCase;
import com.example.demo.domain.model.User;
import com.example.demo.domain.model.UserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final UserRepository userRepository;
    private final UserProfileUseCase userProfileUseCase;
    private final FollowUseCase followUseCase;

    @GetMapping
    public ModelAndView searchPage(@RequestParam(required = false) String keyword,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        // 認証チェック
        if (userDetails == null) {
            return new ModelAndView("redirect:/user");
        }
        
        ModelAndView modelAndView = new ModelAndView("search/index");
        
        User currentUser = userProfileUseCase.getUserProfileByUsername(userDetails.getUsername());
        
        List<User> users;
        if (StringUtils.hasText(keyword)) {
            users = userRepository.searchByDisplayName(keyword);
            modelAndView.addObject("keyword", keyword);
            modelAndView.addObject("searchPerformed", true);
        } else {
            users = userRepository.findAll();
            modelAndView.addObject("searchPerformed", false);
        }
        
        // 自分を除外
        users = users.stream()
            .filter(user -> !user.getId().asString().equals(currentUser.getId().asString()))
            .toList();
        
        // フォロー状態を含めたユーザー情報を作成
        modelAndView.addObject("users", users);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("followUseCase", followUseCase);
        
        return modelAndView;
    }
}