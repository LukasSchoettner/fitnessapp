package de.othr.fitnessapp.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.othr.fitnessapp.model.Role;
import de.othr.fitnessapp.model.User;



public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	
	
	public MyUserDetails(User user) {
		
		this.userName= user.getLogin();
		this.password= user.getPassword();
		System.out.println("password of the user is="+password);
		System.out.println("userName of the user is="+this.userName);
		this.active = user.isActive();
				
		List<Role> myRoles = (List<Role>) user.getRoles();
				
		System.out.println("the user "+  user.getLogin() +" has "+ myRoles.size() +" roles");
		
		
		authorities = new ArrayList<>();
		
		for (Role role : myRoles) {
	        authorities.add(new SimpleGrantedAuthority(role.getDescription().toUpperCase()));
	        System.out.println("the authority of the user " + user.getLogin() + " is " + role.getDescription());
	    }
		
	}
		
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.authorities;
	}

	@Override
	public String getPassword() {
		
		return this.password;
	}

	@Override
	public String getUsername() {
		
		return this.userName;
	}
	
	
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {	
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return this.active;
	}

}
