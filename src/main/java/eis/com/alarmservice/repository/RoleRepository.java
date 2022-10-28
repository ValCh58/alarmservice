package eis.com.alarmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eis.com.alarmservice.modeladmin.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRole(String role);
}
