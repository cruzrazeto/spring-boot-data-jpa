package com.bolsedeideasspringboot.jpa.app.controllers;

import java.lang.ProcessBuilder.Redirect;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsedeideasspringboot.jpa.app.models.entity.Cliente;
import com.bolsedeideasspringboot.jpa.app.models.entity.Factura;
import com.bolsedeideasspringboot.jpa.app.models.entity.ItemFactura;
import com.bolsedeideasspringboot.jpa.app.models.entity.Producto;
import com.bolsedeideasspringboot.jpa.app.service.IClienteService;

@Controller
@RequestMapping("/Factura")
@SessionAttributes("factura")
public class FacturaController {
	@Autowired
	private IClienteService clienteService;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable("id") Long id,
			Map<String, Object> model,
			RedirectAttributes flash) {
		// Factura factura = clienteService.findFacturaById(id);
		Factura factura = clienteService.findFacturaByIdWithClienteWithItemFacturaWithProducto(id);

		if (factura == null) {
			model.put("error","La factura no existe en la base de datos");
			model.put("titulo","Ver Factura");
			return "factura/message";
		}
		model.put("factura", factura);
		model.put("titulo", "Ver Factura " + id);
		return "factura/ver";
	}


	@Secured({"ROLE_ADMIN"})
	@GetMapping("/form/{clientId}")
	public String crear(@PathVariable("clientId") Long clientId,
			Map<String, Object> model,
			RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(clientId);
		if (cliente == null) {
			model.put("error","El cliente no existe en la base de datos");
			model.put("titulo","Crear Factura");
			return "factura/message";
		}
        Factura factura = new Factura();
        factura.setCliente(cliente);

		model.put("factura", factura);
		model.put("titulo", "Crear Factura");
		return "factura/form";
	}


	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/cargar-productos/{term}" , produces = {"application/json"} )
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping("/form")
	public String guardar(@Valid Factura factura,
			BindingResult result,
			Model ui,
			@RequestParam(name = "itemId[]", required = false) Long itemId[],
			@RequestParam(name = "cantidad[]", required = false) Long cantidad[],
			RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			ui.addAttribute("titulo", "Crear factura");
			return "factura/form";
		}
		if (itemId == null || itemId.length == 0) {
			ui.addAttribute("titulo", "Crear factura");
			ui.addAttribute("error", "La factura no puede estar vacia");
			return "factura/form";
		}
		for(int i=0;i < itemId.length && i < cantidad.length;i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);
			logger.info("PRODUCTO:" + ((producto!=null)?producto.getNombre():("{" + itemId[i].toString() + "}")) + ", CANTIDAD: " + cantidad[i].toString());
		}
		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success", "Factura creada con éxito!!");
		return "redirect:/ver/" + factura.getCliente().getId();
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/del/{id}")
	public String deleteFactura(@PathVariable("id") Long id,
			Map<String, Object> model,
			RedirectAttributes flash) {

		Factura factura = clienteService.findFacturaById(id);
		if (factura == null) {
			model.put("error","El factura no existe en la base de datos");
			model.put("titulo","Eliminar factura");
			return "factura/message";
		}
		clienteService.deleteFactura(id);
		flash.addAttribute("success", "Factura eliminada con exito!");
		model.put("titulo", "Ver Cliente ");
		return "redirect:/ver/" + factura.getCliente().getId();
	}
}
