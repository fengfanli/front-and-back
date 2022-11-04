package com.feng.user_manager.controller;

import com.feng.user_manager.vo.resp.ResultData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @GetMapping("/login")
    public ResultData login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (username.equals("admin") && password.equals("123456")) {
            return new ResultData(200, "登录成功");
        } else {
            return new ResultData(100, "登录失败");
        }
    }

    @GetMapping("/login/home/page")
    public ResultData loginHomePage(){
        return new ResultData(200, "欢迎；来到首页");
    }

    @GetMapping("/login/error")
    public ResultData loginErrorHomePage(){
        return new ResultData(100, "请求失败，无 token");
    }
}
