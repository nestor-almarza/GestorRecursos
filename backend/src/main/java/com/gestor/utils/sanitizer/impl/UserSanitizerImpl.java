package com.gestor.utils.sanitizer.impl;

import com.gestor.domains.User;
import com.gestor.utils.sanitizer.IUserSanitizer;
import com.gestor.utils.sanitizer.customFormatter.CustomFormatter;

import org.springframework.stereotype.Service;

@Service("userSanitizerImpl")
public class UserSanitizerImpl implements IUserSanitizer {

    @Override
    public User sanitize(User user) {

        user.setFirstName(CustomFormatter.capitalizeNames(user.getFirstName()));
        user.setLastName(CustomFormatter.capitalizeNames(user.getLastName()));
        user.setEmail(user.getEmail().trim());
        return user;
    }
}
