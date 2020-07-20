package com.stepproject.ibatechurlshortener.service.facebook;

import com.stepproject.ibatechurlshortener.model.User_;
import org.springframework.social.facebook.api.User;

public interface FacebookService {

    String facebookLogin();

    String getFacebookAccessToken(String code);

    User getFacebookUserProfile(String accessToken);

    User_ registerOrLoginUserFromFacebook(User user);
}
