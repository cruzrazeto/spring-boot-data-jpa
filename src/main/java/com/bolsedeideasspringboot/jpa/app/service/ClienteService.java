package com.bolsedeideasspringboot.jpa.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsedeideasspringboot.jpa.app.models.dao.IClienteDao;
import com.bolsedeideasspringboot.jpa.app.models.dao.IFacturaDao;
import com.bolsedeideasspringboot.jpa.app.models.dao.IProductoDao;
import com.bolsedeideasspringboot.jpa.app.models.entity.Cliente;
import com.bolsedeideasspringboot.jpa.app.models.entity.Factura;
import com.bolsedeideasspringboot.jpa.app.models.entity.ItemFactura;
import com.bolsedeideasspringboot.jpa.app.models.entity.Producto;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	IClienteDao clienteDao;

	@Autowired
	IProductoDao productoDao;

	@Autowired
	IFacturaDao facturaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public Cliente findClienteByIdWithFactura(Long id) {
		return clienteDao.fetchByIdWithFactura(id);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String nombre) {
		return productoDao.findByNombreLikeIgnoreCase("%" + nombre + "%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long producto) {
		return productoDao.findById(producto).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}


	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaByIdWithClienteWithItemFacturaWithProducto(Long id) {
		Factura f = facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
//		if (f!=null) {
//			for (ItemFactura i : f.getItems()) {
//			    i.setFactura(f);
//		    }
//		}
		return f;
	}


	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id);
	}

}
