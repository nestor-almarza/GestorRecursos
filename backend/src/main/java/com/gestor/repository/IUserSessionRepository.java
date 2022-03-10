package com.gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.gestor.model.UserSessionModel;

public interface IUserSessionRepository extends JpaRepository<UserSessionModel, String> {


    UserSessionModel findByUserId(String userId);

    Optional<UserSessionModel> findByHash(String sessionHash);
}
