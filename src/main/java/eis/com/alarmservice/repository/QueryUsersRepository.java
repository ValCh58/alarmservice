package eis.com.alarmservice.repository;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import eis.com.alarmservice.dto.UsersRolesDTO;

@NoRepositoryBean
public interface QueryUsersRepository{

	List<UsersRolesDTO> queryUsersRoles();
}

