package eis.com.alarmservice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eis.com.alarmservice.exceptions.ResourceNotFoundException;
import eis.com.alarmservice.modeladmin.Role;
import eis.com.alarmservice.modeladmin.Users;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	public MyUserDetailsService() {};
      
    private UserService userService;
   
    @Autowired
	public MyUserDetailsService(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
    @Transactional
    public UserDetails loadUserByUsername(String userName)throws UsernameNotFoundException {
        
        Users user = Optional.ofNullable(userService.findUserByUserName(userName))
        		            .orElseThrow(()->new ResourceNotFoundException("User not found"));
        
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        if(authorities.isEmpty()) 
           throw new ResourceNotFoundException("User Roles not found");
        
        return buildUserForAuthentication(user, authorities);
    }

    @Transactional
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new ArrayList<>(roles);
    }

    @Transactional
    private UserDetails buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                user.getActive(), true, true, true, authorities);
    }
}
