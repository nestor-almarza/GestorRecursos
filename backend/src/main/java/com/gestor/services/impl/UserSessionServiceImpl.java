package com.gestor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.Period;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.gestor.domains.auth.UserSession;
import com.gestor.model.UserModel;
import com.gestor.model.UserSessionModel;
import com.gestor.repository.IUserSessionRepository;
import com.gestor.services.IUserSessionService;
import com.gestor.utils.converter.IUserConverter;
import com.gestor.utils.encryptor.IHashService;
import com.gestor.utils.rolesEnum.Role;

@Service("userSessionService")
public class UserSessionServiceImpl implements IUserSessionService {

    @Autowired
    private IUserSessionRepository userSessionRepository;

    @Autowired
    private IHashService hashService;

    @Autowired
    private IUserConverter userConverter;

    @Override
    public UserSessionModel refreshSessionByUserId(String userId) throws NoSuchAlgorithmException, InvalidKeySpecException {

        UserSessionModel userSessionModel = userSessionRepository.findByUserId(userId);

        if(userSessionModel == null)
            userSessionModel = new UserSessionModel(userId);

            userSessionModel.setIat(new Date());
            userSessionModel.setHash(hashService.generateHash(
                    userSessionModel.getHash() + new Date()
            ));
            
        return userSessionRepository.save(userSessionModel);
    }

    @Override
    public Boolean checkSessionByHash(String sessionHash, Role[] requiredRoles) {
        List<Role> requiredRolesList = Arrays.asList(requiredRoles);


        UserSessionModel userSessionModel = userSessionRepository.findByHash(sessionHash).get();
        UserModel userModel = userSessionModel.getUser();

     
        if(!requiredRolesList.contains(userModel.getRole()))
            return false;

        if( userSessionModel.getIat().toInstant()
                .plus(Period.ofDays(1))
                .isBefore(Instant.now())
        ){
            return false;
        }

        return true;
    }

    @Override
    public Boolean checkSessionByHash(String sessionId, Role[] requiredRoles, String userId){
        List<Role> requiredRolesList = Arrays.asList(requiredRoles);

        UserSessionModel userSessionModel = userSessionRepository.findById(sessionId).get();
        UserModel userModel = userSessionModel.getUser();

        if(!requiredRolesList.contains(userModel.getRole()))
            return false;

        if(userModel.getRole().hasSameUserPolicy() && userModel.getId() != userId)
            return false;

        return true;
    }

    @Override
    public UserSession refreshSession(String sessionHash) throws NoSuchAlgorithmException, InvalidKeySpecException {

        UserSessionModel userSessionModel = userSessionRepository.findByHash(sessionHash).get();

        userSessionModel.setIat(new Date());
        userSessionModel.setHash(hashService.generateHash(
                userSessionModel.getHash() + new Date()
        ));

        userSessionModel = userSessionRepository.save(userSessionModel);


        UserSession userSession = new UserSession(
                    userSessionModel.getHash(),
                    userConverter.convert(userSessionModel.getUser())
                );

        return userSession;
    }
}
