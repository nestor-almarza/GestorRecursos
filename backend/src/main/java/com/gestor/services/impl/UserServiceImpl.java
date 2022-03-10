package com.gestor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.gestor.domains.User;
import com.gestor.domains.UserReset;
import com.gestor.domains.auth.UserSession;
import com.gestor.domains.presenter.UserPresenter;
import com.gestor.exceptions.UserServiceException;
import com.gestor.model.UserModel;
import com.gestor.model.UserSessionModel;
import com.gestor.repository.IUserRepository;
import com.gestor.services.IUserService;
import com.gestor.services.IUserSessionService;
import com.gestor.utils.converter.IUserConverter;
import com.gestor.utils.encryptor.IHashService;
import com.gestor.utils.rolesEnum.Role;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserSessionService userSessionService;

    @Autowired
    IUserConverter userConverter;

    @Autowired
    IHashService hashService;

    public UserServiceImpl(){}

    @Override
    public UserSession checkEmailAndPassword(User user)
            throws NoSuchElementException, NoSuchAlgorithmException, InvalidKeySpecException, UserServiceException {

        String email = user.getEmail();
        UserModel userModel  =
                userRepository.findByEmail(email).orElseThrow();

        boolean hasCorrectCredentials = hashService.validatePassword(user.getPassword(),userModel.getPassword());

        if(!hasCorrectCredentials)
            throw new UserServiceException("Email or password incorrect.");

        // saves user session
        UserSessionModel userSessionModel = userSessionService.refreshSessionByUserId(userModel.getId());

        UserPresenter userPresenter = userConverter.convert(userModel);

        UserSession userSession = new UserSession(userSessionModel.getHash(), userPresenter);

        return userSession;
    }

    // On stand-by
    @Override
    public UserPresenter registerUser(User user) throws UserServiceException, NoSuchElementException,
            NoSuchAlgorithmException, InvalidKeySpecException {

        String email = user.getEmail();
        boolean emailExists = userRepository.existsByEmail(email);
        if(emailExists)
            throw new UserServiceException("Email already in use.");
        ;
        UserModel userModel  = userConverter.convert(user);

        String encryptedPassword = hashService.generatePasswordHash(user.getPassword());
        userModel.setPassword(encryptedPassword);
        userModel  = userRepository.save(userModel);

        return userConverter.convert(userModel);
    }


    @Override
    public String createCookieServelet(HttpServletResponse response) {

        Cookie jwtTokenCookie = new Cookie("user-id", "c2FtLnNtaXRoQGV4YW1wbGUuY29t");

        jwtTokenCookie.setMaxAge(86400);
        jwtTokenCookie.setSecure(false);

        response.addCookie(jwtTokenCookie);


        return null;
    }

    @Override
    public UserPresenter getUserById(String userId) {
        UserModel userModel = userRepository.findById(userId).orElseThrow();
        UserPresenter userPresenter = userConverter.convert(userModel);
        return userPresenter;
    }

    @Override
    public UserPresenter changePassword(UserReset userReset)
            throws NoSuchElementException, NoSuchAlgorithmException,
            InvalidKeySpecException, UserServiceException {

        String email = userReset.getEmail();
        UserModel userModel  =
                userRepository.findByEmail(email).orElseThrow();

        boolean authentication =
                hashService.validatePassword(userReset.getPassword() ,userModel.getPassword() );

        if(!authentication)
            throw new UserServiceException("Incorrect password.");
        
        String newPassword = hashService.generatePasswordHash(userReset.getNewPassword());

        userModel.setPassword(newPassword);

        userModel = userRepository.save(userModel);

        return userConverter.convert(userModel);
    }

	@Override
	public UserPresenter updateUser(User user) throws NoSuchElementException, NoSuchAlgorithmException, InvalidKeySpecException, UserServiceException {
		
		UserModel userModel = this.userConverter.convert(user);
		
		Optional<UserModel> repoUserOpt = this.userRepository.findById(userModel.getId());
		
		if(repoUserOpt.isEmpty())
			throw new NoSuchElementException("User doesn't exist.");
		
		UserModel repoUserModel = repoUserOpt.get();
		
		boolean auth = this.hashService.validatePassword(userModel.getPassword(), repoUserModel.getPassword());
		
		if(!auth)
			throw new UserServiceException("Passwords don't match");
		
		repoUserModel.setFirstName(userModel.getFirstName());
		repoUserModel.setLastName(userModel.getLastName());
		
		userModel = this.userRepository.save(repoUserModel);
		
		UserPresenter userPresenter = this.userConverter.convert(userModel);
		
		return userPresenter;
			
	}

	@Override
	public List<UserPresenter> listUserCandidates() {
		List<UserModel> listCandidatesModel = this.userRepository.findAllByRole(Role.ROLE_CANDIDATE);
		
		List<UserPresenter> listCandidatesPresenter = listCandidatesModel.stream().map(
				candidateUser -> this.userConverter.convert(candidateUser)
				).collect(Collectors.toList());
		
		return listCandidatesPresenter;
	}

}
