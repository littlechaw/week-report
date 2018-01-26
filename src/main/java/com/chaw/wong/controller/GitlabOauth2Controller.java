package com.chaw.wong.controller;

import com.chaw.wong.oauth.gitlab.Application;
import com.chaw.wong.utils.HttpClientUtils;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/oauth")
public class GitlabOauth2Controller {

    @Autowired
    private Application application;
    private OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
    private Pattern p = Pattern.compile("/([\\w-_]+)/oauth/callback");

    private String getProjectName () {
        String r = "monitor";
        Matcher m = p.matcher(application.getCallbackUrl());
        if (m.find()) {
            r = m.group(1);
        }
        return r;
    }

    @RequestMapping(value = "/validate")
    public void validate(HttpServletRequest request, HttpServletResponse response){
        try {
            OAuthClientRequest oAuthClientRequest = OAuthClientRequest
                    .authorizationLocation(application.getOauthUrl())
                    .setClientId(application.getApplicationId())
                    .setRedirectURI(application.getCallbackUrl())
                    .setResponseType("code")
                    .buildQueryMessage();
            response.sendRedirect(oAuthClientRequest.getLocationUri());
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String code,
                         @RequestParam(required = false) String error, @RequestParam(value = "error_description", required = false) String errorDescription){
        if(!StringUtils.isEmpty(code)){
            try {
                OAuthClientRequest oAuthClientRequest = OAuthClientRequest
                        .tokenLocation(application.getTokenUrl())
                        .setGrantType(GrantType.AUTHORIZATION_CODE)
                        .setClientId(application.getApplicationId())
                        .setClientSecret(application.getSecret())
                        .setRedirectURI(application.getCallbackUrl())
                        .setGrantType(GrantType.AUTHORIZATION_CODE)
                        .setCode(code)
                        .buildQueryMessage();
                OAuthJSONAccessTokenResponse oAuthJSONAccessTokenResponse = oAuthClient.accessToken(oAuthClientRequest);
                String accessToken = oAuthJSONAccessTokenResponse.getAccessToken();
                System.out.println(accessToken);

                Map user = HttpClientUtils.get(String.format(application.getUserUrl(),accessToken), Map.class);
                System.out.println(user);
//                WebUtils.setSessionAttribute(request, "username", user.get("username"));
//                WebUtils.setSessionAttribute(request, "email", user.get("email"));
                try {
                    response.sendRedirect("/" + getProjectName() + "/");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    response.sendRedirect("/oauth/validate");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }else {
            System.out.println(error + ": " + errorDescription);
            try {
                response.sendRedirect("/oauth/validate");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PreDestroy
    public void cleanUp() {
        oAuthClient.shutdown();
    }
}
