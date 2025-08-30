package org.jinwenxin.lifewishlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 表明这是一个提供REST API的控制器
@RequestMapping("/api/test") // 所有这个控制器中的API路径都以/api/test开头
public class TestController {

    @GetMapping("/hello") // 处理 GET /api/test/hello 请求
    public String sayHello() {
        return "Hello from Spring Boot! Your backend is running successfully!";
    }
}