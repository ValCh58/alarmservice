package eis.com.alarmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eis.com.alarmservice.modeladmin.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
	Users findByEmail(String email);
    Users findByUserName(String userName);

}
