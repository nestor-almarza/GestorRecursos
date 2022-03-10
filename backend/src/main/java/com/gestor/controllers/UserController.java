package com.gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.NoSuchElementException;

import com.gestor.domains.User;
import com.gestor.domains.UserReset;
import com.gestor.domains.auth.UserSession;
import com.gestor.domains.presenter.UserPresenter;
import com.gestor.exceptions.UserServiceException;
import com.gestor.services.IUserService;
import com.gestor.services.IUserSessionService;
import com.gestor.utils.encryptor.impl.HashServiceImpl;
import com.gestor.utils.rolesEnum.Role;
import com.gestor.utils.sanitizer.IUserSanitizer;
import com.gestor.utils.validators.IUserValidator;
import com.gestor.utils.validators.errors.ValidationError;

@CrossOrigin(
        origins = { "http://localhost:8080/", "http://localhost:4200/"},
        allowCredentials = "true"
)
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    IUserService userServiceImpl;
    
    @Autowired
    IUserValidator userValidator;

    @Autowired
    IUserSessionService userSessionService;
    
    @Autowired
    IUserSanitizer userSanitizerImpl;


    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        try {
            UserSession userSession = userServiceImpl.checkEmailAndPassword(user);

            return new ResponseEntity<>(userSession, HttpStatus.OK);

        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        catch(UserServiceException use) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(
            @CookieValue("session") String sessionId,
            @PathVariable(value = "id") String userId
    ) {
        try {
            // Permission check //
            Role[] requiredRoles = {Role.ROLE_CANDIDATE, Role.ROLE_EMPLOYER};
            if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

            UserPresenter userPresenter = userServiceImpl.getUserById(userId);
            return new ResponseEntity<>(userPresenter, HttpStatus.OK);

        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/settings/password")
    public ResponseEntity<?> changePassword(
            @CookieValue("session") String sessionId,
            @RequestBody UserReset userReset) {

        // Permission check //
        Role[] requiredRoles = {Role.ROLE_CANDIDATE, Role.ROLE_EMPLOYER};
        if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            UserPresenter userPresenter = userServiceImpl.changePassword(userReset);

            return new ResponseEntity<>(userPresenter, HttpStatus.OK);

        } 
        catch(UserServiceException use) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (NoSuchAlgorithmException nsae) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InvalidKeySpecException ikse) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/new")
    public ResponseEntity<?> registerUser(
            @CookieValue("session") String sessionId,
            @RequestBody User user
    ) {

        // Permission check //
        Role[] requiredRoles = {Role.ROLE_EMPLOYER};
        if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    	ValidationError errors = this.userValidator.validateGenerateUser(user);
    	
    	if(!errors.getErrors().isEmpty())
    		return new ResponseEntity<>(errors, HttpStatus.PRECONDITION_FAILED);

        user = this.userSanitizerImpl.sanitize(user);

        try {
            UserPresenter userPresenter = userServiceImpl.registerUser(user);
            return new ResponseEntity<>(userPresenter, HttpStatus.OK);

        }catch (UserServiceException use){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (NoSuchAlgorithmException nsae) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InvalidKeySpecException ikse) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(value = "/edit")
    public ResponseEntity<?> editUser(
            @CookieValue("session") String sessionId,
            @RequestBody User user){

        // Permission check //
        Role[] requiredRoles = {Role.ROLE_CANDIDATE, Role.ROLE_EMPLOYER};
        if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	
    	ValidationError errors = this.userValidator.validateEditUser(user);
    	
    	if(!errors.getErrors().isEmpty())
    		return new ResponseEntity<>(errors, HttpStatus.PRECONDITION_FAILED);

        user = this.userSanitizerImpl.sanitize(user);

    	try {
    		UserPresenter userPresenter = this.userServiceImpl.updateUser(user);
    		
    		return new ResponseEntity<>(userPresenter, HttpStatus.OK);
    	}
    	catch(NoSuchElementException nse) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	catch (UserServiceException use){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (NoSuchAlgorithmException nsae) {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InvalidKeySpecException ikse) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/list/candidates")
    public ResponseEntity<?> listUserCandidates(
            @CookieValue("session") String sessionId
    ){

        // Permission check //
        Role[] requiredRoles = { Role.ROLE_EMPLOYER};
        if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	try {
    		List<UserPresenter> listUserCandidates = this.userServiceImpl.listUserCandidates();
        	
        	if(listUserCandidates.isEmpty())
        		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        	
        	return new ResponseEntity<>(listUserCandidates, HttpStatus.OK);
    	}
    	catch(Exception e) {
    		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
    }

    @GetMapping(value = "what")
    public ResponseEntity<?> getPass() throws NoSuchAlgorithmException, InvalidKeySpecException {

        HashServiceImpl hs = new HashServiceImpl();

        String pass = hs.generateHash("madrid");
    
        return new ResponseEntity<>( pass, HttpStatus.OK);
    }

}
