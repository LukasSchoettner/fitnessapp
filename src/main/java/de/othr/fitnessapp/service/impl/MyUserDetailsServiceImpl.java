package de.othr.fitnessapp.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.othr.fitnessapp.config.MyUserDetails;
import de.othr.fitnessapp.model.Baseuser;
import de.othr.fitnessapp.repository.UserRepository;



@Service
public class MyUserDetailsServiceImpl implements UserDetailsService{

	UserRepository userRepository;
	
	public MyUserDetailsServiceImpl (UserRepository userRepository) {
		this.userRepository= userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Baseuser> oUser= userRepository.findByLoginIgnoreCase(username);
		oUser.orElseThrow(()-> new UsernameNotFoundException("Not found "+username));
		System.out.println("User found at the UserDetailsService="+ oUser.get().getLogin());
		return new MyUserDetails(oUser.get());
	}
}
