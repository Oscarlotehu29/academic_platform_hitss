package com.hitss.academic_platform.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hitss.academic_platform.entities.User;
import com.hitss.academic_platform.repositories.UserRepository;




@Service
public class JpaUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> userOpt = userRepository.findByUserName(username);
		
		if(!userOpt.isPresent()) {
			throw new UsernameNotFoundException(
					String.format("Username %s no existe en el sistema.!!", username)
					);
		}
		User user = userOpt.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRol().getName());
		authorities.add(authority);
		
		
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(), 
				user.getPassword(), 
				user.getIsActive() == 1? true : false, 
				true, 
				true, 
				true, 
				authorities);
	}
}
