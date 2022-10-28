package eis.com.alarmservice.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eis.com.alarmservice.exceptions.ResourceNotFoundException;
import eis.com.alarmservice.exceptions.SaveResourceErrorException;
import eis.com.alarmservice.modeladmin.Role;
import eis.com.alarmservice.modeladmin.Users;
import eis.com.alarmservice.repository.RoleRepository;
import eis.com.alarmservice.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public Users findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Users findUserByUserName(String userName) {
    	return userRepository.findByUserName(userName);
    }
    
    @Transactional
    public Users saveUser(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        Role userRole = Optional.ofNullable(roleRepository.findByRole(users.getNameRole()))
        		                .orElseThrow(()->new ResourceNotFoundException("Role not found"));
        users.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return Optional.ofNullable(userRepository.save(users))
        		       .orElseThrow(()->new SaveResourceErrorException("Save resource error User"));
    }
    
    public void deleteUser(Integer id) {
       	this.userRepository.deleteById(id);                            
    }

}