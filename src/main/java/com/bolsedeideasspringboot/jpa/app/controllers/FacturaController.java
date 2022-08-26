package com.bolsedeideasspringboot.jpa.app.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/Factura")
@SessionAttributes("factura")
public class FacturaController {
	@Autowired
	private IClienteService clienteService;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable("id") Long id,
			Map<String, Object> model,
			RedirectAttributes flash,
			Locale locale) {
		// Factura factura = clienteService.findFacturaById(id);
		Factura factura = clienteService.findFacturaByIdWithClienteWithItemFacturaWithProducto(id);

		if (factura == null) {
			model.put("error",messageSource.getMessage("text.factura.flash.db.error", null, locale));
			model.put("titulo", String.format(messageSource.getMessage("text.factura.ver.titulo", null, locale), factura.getDescripcion()));
			return "factura/message";
		}
		model.put("factura", factura);
		model.put("titulo", String.format(messageSource.getMessage("text.factura.ver.titulo", null, locale), factura.getDescripcion()));
		return "factura/ver";
	}


	@Secured({"ROLE_ADMIN"})
	@GetMapping("/form/{clientId}")
	public String crear(@PathVariable("clientId") Long clientId,
			Map<String, Object> model,
			RedirectAttributes flash,
			Locale locale) {
		Cliente cliente = clienteService.findOne(clientId);
		if (cliente == null) {
			model.put("error",messageSource.getMessage("text.factura.flash.crear.success", null, locale));
			model.put("titulo",messageSource.getMessage("text.factura.form.titulo", null, locale));
			return "factura/message";
		}
        Factura factura = new Factura();
        factura.setCliente(cliente);

		model.put("factura", factura);
		model.put("titulo", messageSource.getMessage("text.factura.form.titulo", null, locale));
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
			SessionStatus status,
			Locale locale) {
		if (result.hasErrors()) {
			ui.addAttribute("titulo", messageSource.getMessage("text.factura.form.titulo", null, locale));
			return "factura/form";
		}
		if (itemId == null || itemId.length == 0) {
			ui.addAttribute("titulo", messageSource.getMessage("text.factura.form.titulo", null, locale));
			ui.addAttribute("error", messageSource.getMessage("text.factura.flash.lineas.error", null, locale));
			return "factura/form";
		}
		Producto producto;
		for(int i=0;i < itemId.length && i < cantidad.length;i++) {
			producto = clienteService.findProductoById(itemId[i]);
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
//			linea.setFactura(factura);
			factura.addItemFactura(linea);
			logger.info("ID:" + ((producto!=null)?producto.getId():("{" + itemId[i].toString() + "}")) + ",PRODUCTO:" + ((producto!=null)?producto.getNombre():("{" + itemId[i].toString() + "}")) + ", CANTIDAD: " + cantidad[i].toString());
		}
		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success", messageSource.getMessage("text.factura.flash.crear.success", null, locale));
		return "redirect:/Cliente/ver/" + factura.getCliente().getId();
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/del/{id}")
	public String deleteFactura(@PathVariable("id") Long id,
			Map<String, Object> model,
			RedirectAttributes flash,
			Locale locale) {

//		Factura factura = clienteService.findFacturaByIdWithClienteWithItemFacturaWithProducto(id);
//
//		if (factura == null) {
//			model.put("error",messageSource.getMessage("text.factura.flash.db.error", null, locale));
//			model.put("titulo", String.format(messageSource.getMessage("text.factura.ver.titulo", null, locale), factura.getDescripcion()));
//			return "factura/message";
//		}
//		for (ItemFactura item : factura.getItems()) {
//			logger.info("ITEM > " + ((item == null)? "NUL": item.toString()));
//		}
		Factura factura = clienteService.findFacturaById(id);
		if (factura == null) {
			model.put("error",messageSource.getMessage("text.factura.flash.db.error", null, locale));
			model.put("titulo","Eliminar factura");
			return "factura/message";
		}
		try {
			logger.info("factura 2 Delete" + (new ObjectMapper()).writeValueAsString(factura));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		clienteService.deleteFactura(id);
		flash.addAttribute("success", messageSource.getMessage("text.factura.flash.eliminar.success", null, locale));
		model.put("titulo", messageSource.getMessage("text.cliente.detalle.titulo", null, locale).concat(": ").concat(factura.getCliente().getNombre()));
		return "redirect:/Cliente/ver/" + factura.getCliente().getId();
	}
}
