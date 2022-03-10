package com.gestor.services;

import javax.servlet.http.HttpServletResponse;

import com.gestor.domains.User;
import com.gestor.domains.UserReset;
import com.gestor.domains.auth.UserSession;
import com.gestor.domains.presenter.UserPresenter;
import com.gestor.exceptions.UserServiceException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.NoSuchElementException;

public interface IUserService {

    public UserSession checkEmailAndPassword(User user) throws NoSuchAlgorithmException, InvalidKeySpecException, UserServiceException;

    public UserPresenter getUserById(String userId);

    public UserPresenter registerUser(User user) throws UserServiceException, NoSuchAlgorithmException, InvalidKeySpecException;

    public String createCookieServelet(HttpServletResponse response);

    public UserPresenter changePassword(UserReset userReset) throws NoSuchAlgorithmException, InvalidKeySpecException, UserServiceException;
    
    public UserPresenter updateUser(User user) throws NoSuchElementException, NoSuchAlgorithmException, InvalidKeySpecException, UserServiceException;
    
    public List<UserPresenter> listUserCandidates();

}
