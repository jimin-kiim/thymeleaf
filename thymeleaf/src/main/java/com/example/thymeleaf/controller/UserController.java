package com.example.thymeleaf.controller;

import com.example.thymeleaf.dto.User;
import com.example.thymeleaf.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
요청이 들어오면 Dispatcher Servlet은 Handler Mapping에게 대응되는 컨트롤러가 있는지 물어본다.
있으면 컨트롤러 정보를 반환해준다.
Dispatcher Servlet은 이를 Handler Adapter에 전달하고 Handler Adapter는 컨트롤러를 호출한다.

호출된 Controller는 필요 시 Model에게 데이터를 요청해 응답받아 사용하며 작업을 수행한다.
수행 후 View Resolver에게 뷰 이름을 반환하면 대응되는 뷰에 모델을 반영해 동적 처리를 완료한다.

 */
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    // 로그인 페이지 (GET)
    @GetMapping("/login")
    public String loginForm() {
        return "login";
        /* 뷰 이름을 반환하면 View Resolver는
           classpath:/templates/뷰이름/.html 파일을 찾아 렌더링한다.
         */
    }

    // 로그인 처리 (POST)
    @PostMapping("/login")
    public String login(@RequestParam String id,
                        @RequestParam String password,
                        Model model) {
        if (userService.validateUser(id, password)) {
            model.addAttribute("users", userService.getAllUsers());
            return "users"; // 성공 시 유저 목록 페이지
        } else {
            model.addAttribute("error", "아이디와 비밀번호를 확인해주세요.");
            return "login"; // 실패 시 로그인 페이지로 돌아감
        }
    }
}
