package com.bolsedeideasspringboot.jpa.app.models.dao;

import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsedeideasspringboot.jpa.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{

	@Query("select c from Cliente c where nombre like ?1% and apellido like ?2%")
	List<Cliente> findByNameAndApellido(String nombre,String apellido);

	@Query("select c from Cliente c left join fetch c.facturas f where c.id = ?1")
	Cliente fetchByIdWithFactura(Long id);
}