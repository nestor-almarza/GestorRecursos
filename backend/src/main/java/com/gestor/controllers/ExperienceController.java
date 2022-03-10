package com.gestor.controllers;

import com.gestor.services.IExperienceService;
import com.gestor.services.IUserSessionService;
import com.gestor.utils.rolesEnum.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = { "http://localhost:8080/", "http://localhost:4200/"},
        allowCredentials = "true"
)
@RestController
@RequestMapping(value = "/api/experience")
public class ExperienceController {

    @Autowired
    IExperienceService experienceService;

    @Autowired
    private IUserSessionService userSessionService;

    @DeleteMapping(value="/{id}")
    public ResponseEntity<?> deleteById(
            @CookieValue(value="session") String sessionId,
            @PathVariable(value = "id") String experienceId){

        // Permission check //
        Role[] requiredRoles = {Role.ROLE_CANDIDATE, Role.ROLE_EMPLOYER};
        if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        try {
            experienceService.deleteExperienceById(experienceId);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (EmptyResultDataAccessException erdae) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
