package com.stepproject.ibatechurlshortener.social.service;

import com.stepproject.ibatechurlshortener.model.User_;
import org.springframework.social.facebook.api.User;

public interface FacebookService {

    String facebookLogin();

    String getFacebookAccessToken(String code);

    User getFacebookUserProfile(String accessToken);

    User_ registerOrLoginUserFromFacebook(User user);
}
