package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {//发起post请求获取token
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token?grant_type=authorization_code&code="+accessTokenDTO.getCode()+ "&client_id="+accessTokenDTO.getClient_id()+"&redirect_uri="+accessTokenDTO.getRedirect_uri()+"&client_secret="+accessTokenDTO.getClient_secret())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String str1 = string.split(":")[1];
            String str2 = str1.split("\"")[1];//拆分便于
            return str2;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    } //发起get请求返回GitUser对象，


    public GiteeUser getGiteeUser(String token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token="+token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}





