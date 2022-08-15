package com.bolsedeideasspringboot.jpa.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsedeideasspringboot.jpa.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	// select u from Usuario u whereu.username=?1
	public Usuario findByUsername(String username);
}
