package com.bolsedeideasspringboot.jpa.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsedeideasspringboot.jpa.app.models.entity.Cliente;
import com.bolsedeideasspringboot.jpa.app.models.entity.Factura;
import com.bolsedeideasspringboot.jpa.app.models.entity.Producto;

public interface IClienteService {
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	public void save(Cliente cliente);
	public Cliente findOne(Long id);
	public Cliente findClienteByIdWithFactura(Long id);
	public void delete(Long id);
	public List<Producto> findByNombre(String name);
	public void saveFactura(Factura factura);
	public Producto findProductoById(Long producto);
	public Factura findFacturaById(Long id);
	public void deleteFactura(Long id);
	public Factura findFacturaByIdWithClienteWithItemFacturaWithProducto(Long id);
}
