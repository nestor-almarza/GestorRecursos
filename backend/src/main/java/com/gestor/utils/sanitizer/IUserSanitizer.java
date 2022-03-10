package com.gestor.utils.sanitizer;

import com.gestor.domains.User;

public interface IUserSanitizer {
    User sanitize(User user);
}
