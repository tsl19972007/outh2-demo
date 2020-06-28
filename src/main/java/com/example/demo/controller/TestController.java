package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/17 17:28
 * @description：
 */

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

    @PostMapping("/testPost")
    public String testPost() {
        return "ok";
    }

    @GetMapping("/principal")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }

}
