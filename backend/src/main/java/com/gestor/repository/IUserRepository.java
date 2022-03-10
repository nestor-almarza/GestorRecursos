package com.gestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.gestor.model.UserModel;
import com.gestor.utils.rolesEnum.Role;

public interface IUserRepository extends JpaRepository<UserModel, String> {
   public Optional<UserModel> findByEmail(String email);

   public boolean existsByEmail(String email);
   
   public List<UserModel> findAllByRole(Role role);
}
