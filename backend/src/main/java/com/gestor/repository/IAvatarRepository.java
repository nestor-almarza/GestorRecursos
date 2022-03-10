package com.gestor.repository;

import com.gestor.model.AvatarModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAvatarRepository extends JpaRepository<AvatarModel, String> {
}
