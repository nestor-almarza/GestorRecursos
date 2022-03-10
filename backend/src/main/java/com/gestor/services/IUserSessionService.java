package com.gestor.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import com.gestor.domains.auth.UserSession;
import com.gestor.model.UserSessionModel;
import com.gestor.utils.rolesEnum.Role;

public interface IUserSessionService {
    UserSessionModel refreshSessionByUserId(String userId) throws NoSuchAlgorithmException, InvalidKeySpecException;

    Boolean checkSessionByHash(String sessionId, Role[] requiredRoles);

    Boolean checkSessionByHash(String sessionId, Role[] requiredRoles, String userId);

    UserSession refreshSession(String sessionHash) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
