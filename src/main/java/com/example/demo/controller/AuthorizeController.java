package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GiteeUser;
import com.example.demo.provider.GiteeProvider;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private AccessTokenDTO accessTokenDTO;
    @Autowired
    private GiteeProvider giteeProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state
    ){
        accessTokenDTO.setClient_id("c3672956e6bacb31f4e5ff029451c6e093f05a8518791b22ccac74519661c645");
        accessTokenDTO.setClient_secret("841ec518f2291fbf6f75d675e8558312098a55e9edef122dfc0f2dd492410621");
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String token = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getGiteeUser(token);
        System.out.println("id  :"+giteeUser.getId());
        System.out.println("name  :"+giteeUser.getName());
        System.out.println("bio  :"+giteeUser.getBio());
        return "index";
    }
}


