package com.springboot.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.training.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
}
