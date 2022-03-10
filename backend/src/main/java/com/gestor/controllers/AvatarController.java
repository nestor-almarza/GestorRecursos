package com.gestor.controllers;

import com.gestor.domains.Avatar;
import com.gestor.domains.presenter.AvatarPresenter;
import com.gestor.services.IAvatarService;
import com.gestor.services.IUserSessionService;
import com.gestor.utils.rolesEnum.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = { "http://localhost:8080/", "http://localhost:4200/"},
        allowCredentials = "true"
)
@RestController
@RequestMapping(value = "/api/avatar")
public class AvatarController {

    @Autowired
    IAvatarService avatarService;

    @Autowired
    private IUserSessionService userSessionService;

    @PutMapping
    public ResponseEntity<?> saveAvatar(
            @CookieValue("session") String sessionId,
            @RequestBody Avatar avatar){

        // Permission check //
        Role[] requiredRoles = {Role.ROLE_CANDIDATE, Role.ROLE_EMPLOYER};
        if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            AvatarPresenter avatarPresenter = avatarService.save(avatar);
            return new ResponseEntity<>(avatarPresenter, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
