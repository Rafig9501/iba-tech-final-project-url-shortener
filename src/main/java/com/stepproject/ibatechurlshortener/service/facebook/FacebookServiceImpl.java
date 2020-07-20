package com.stepproject.ibatechurlshortener.service.facebook;

import com.stepproject.ibatechurlshortener.dto.UserDto;
import com.stepproject.ibatechurlshortener.model.User_;
import com.stepproject.ibatechurlshortener.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FacebookServiceImpl implements FacebookService {

    @Value("${social.facebook.app-id}")
    private String facebookId;

    @Value("${social.facebook.app-password}")
    private String facebookSecret;

    @Value("${url.shortener.domain}")
    private String domain;

    private final UserService userService;

    public FacebookServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private FacebookConnectionFactory createFacebookConnection() {
        return new FacebookConnectionFactory(facebookId, facebookSecret);
    }


    @Override
    public String facebookLogin() {
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(domain + "/facebook");
        parameters.setScope("public_profile,email");
        return createFacebookConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
    }

    @Override
    public String getFacebookAccessToken(String code) {
        return createFacebookConnection()
                .getOAuthOperations()
                .exchangeForAccess(code, domain + "/facebook", null)
                .getAccessToken();
    }

    @Override
    public User getFacebookUserProfile(String accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken);
        String[] fields = {"first_name", "last_name", "email"};
        return facebook.fetchObject("me", User.class, fields);
    }

    @Override
    public User_ registerOrLoginUserFromFacebook(User user) {
        ResponseEntity<User_> userFromDb = userService.findByEmail(user.getEmail());
        if (userFromDb.getStatusCode().equals(HttpStatus.FOUND)) {
            return userFromDb.getBody();
        } else {
            UserDto userDto = new UserDto(user.getName(), user.getLastName(), user.getEmail(), null);
            ResponseEntity<User_> userResponseEntity = userService.registerUser(userDto);
            return userResponseEntity.getBody();
        }
    }
}
