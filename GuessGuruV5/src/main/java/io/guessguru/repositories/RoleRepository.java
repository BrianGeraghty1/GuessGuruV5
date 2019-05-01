package io.guessguru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.guessguru.entities.Role;

public interface RoleRepository  extends JpaRepository<Role, String>{
 
}
