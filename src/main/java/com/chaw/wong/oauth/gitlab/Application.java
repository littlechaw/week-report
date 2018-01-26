package com.chaw.wong.oauth.gitlab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Application {

    private String url = "http://gitlab.ngarihealth.com";
    private String oauthUrl = url + "/oauth/authorize";
    private String tokenUrl = url + "/oauth/token";
    private String userUrl = url + "/api/v3/user?access_token=%s";
    @Value("${gitlab.applicationId}")
    private String applicationId;
    @Value("${gitlab.secret}")
    private String secret;
    @Value("${gitlab.callbackUrl}")
    private String callbackUrl;

    public Application() {
    }

    public Application(String applicationId, String secret, String callbackUrl) {
        this.applicationId = applicationId;
        this.secret = secret;
        this.callbackUrl = callbackUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOauthUrl() {
        return oauthUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
