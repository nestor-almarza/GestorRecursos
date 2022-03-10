package com.gestor.controllers;

import com.gestor.domains.stats.GeneralStats;
import com.gestor.services.IStatsService;
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
@RequestMapping(value = "/api/stats")
public class StatsController {

    @Autowired
    IStatsService statsService;

    @Autowired
    private IUserSessionService userSessionService;

    @GetMapping
    public ResponseEntity<?> getGeneralStats(
            @CookieValue("session") String sessionId
    ){

        // Permission check //
        Role[] requiredRoles = {Role.ROLE_EMPLOYER};
        if(!userSessionService.checkSessionByHash(sessionId, requiredRoles))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        try {

            GeneralStats generalStats = statsService.getGeneralStats();

            return new ResponseEntity<>(generalStats, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // test
    @GetMapping(value = "test")
    public ResponseEntity<?> getCandidateById() {
        return new ResponseEntity<>("Deployment v.1.0.0", HttpStatus.OK);
    }
}
