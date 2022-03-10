package com.gestor.controllers;

import com.gestor.domains.auth.UserSession;
import com.gestor.services.IUserSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(
        origins = { "http://localhost:8080/", "http://localhost:4200/"},
        allowCredentials = "true"
)
@RestController
@RequestMapping(value = "/api/session")
public class SessionController {

    @Autowired
    private IUserSessionService userSessionService;

    @PostMapping(value = "refresh")
    public ResponseEntity<?> refreshSession(
            @CookieValue("session") String sessionHash
    ){

        try {

            UserSession userSession = userSessionService.refreshSession(sessionHash);

            return new ResponseEntity<>(userSession, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
