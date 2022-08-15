package com.bolsedeideasspringboot.jpa.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bolsedeideasspringboot.jpa.app.models.dao.IUsuarioDao;
import com.bolsedeideasspringboot.jpa.app.models.entity.Roles;
import com.bolsedeideasspringboot.jpa.app.models.entity.Usuario;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class.toString());

	@Override
	@Transactional()
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario= usuarioDao.findByUsername(username);
		
		if (usuario==null) {
			logger.error("Error login, no existe usuario '".concat(username).concat("'"));
			throw new UsernameNotFoundException("Username: '".concat(username).concat("' no existe en el sistema"));
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (Roles rol: usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}
		
		if (authorities.isEmpty()) {
			logger.error("Error login, usuario '".concat(username).concat("' no tiene rol asignado"));
			throw new UsernameNotFoundException("Username: '".concat(username).concat("' no tiene rol en el sistema"));
		}
		
		return new User(usuario.getUsername(),usuario.getPassword(),usuario.getEnable(),true,true,true,authorities);
	}
	
	
}