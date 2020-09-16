package com.bolsedeideasspringboot.jpa.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsedeideasspringboot.jpa.app.models.entity.Cliente;
import com.bolsedeideasspringboot.jpa.app.pager.PageRender;
import com.bolsedeideasspringboot.jpa.app.service.IClienteService;
import com.bolsedeideasspringboot.jpa.app.service.IUploadFileService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping("/")
	public String home() {
		// forward y no redirect
		return "forward:/Cliente/listar";
	}

	@GetMapping(value = "/fotos/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() +"\"")
				.body(recurso);
	}

	@GetMapping(value = "/Cliente/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model,RedirectAttributes flash ) {
		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/Cliente/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre() + " " + cliente.getApellido());
		return "/cliente/ver";

	}

	@GetMapping(value = "/Cliente/listar")
	public String listar(@RequestParam(name = "page",defaultValue = "0") int page,Model model) {

		Pageable pageRequest = PageRequest.of(page,5);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<>("/Cliente/listar", clientes);

		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes",clientes);
		model.addAttribute("page",pageRender);
		return "/cliente/listar";
	}

	@GetMapping(value = "/Cliente/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Nuevo cliente");
		return "/cliente/form";
	}

	@PostMapping(value = "/Cliente/form")
	public String guardar(@Valid Cliente cliente,@RequestParam("file") MultipartFile file, BindingResult result,Model model
			,RedirectAttributes flash,SessionStatus status) {
		String mensaje;
		if (result.hasErrors()) {
			if (cliente == null || cliente.getId() == null) {
				model.addAttribute("titulo", "Nuevo cliente");
			} else {
				model.addAttribute("titulo", "Editar cliente");
			}
			return "/cliente/form";
		}
		if (!file.isEmpty()) {

			if (cliente.getId()!= null
				&& cliente.getId() > 0
				&& cliente.getFoto() != null
				&& cliente.getFoto().length() >0 ) {

				if (uploadFileService.delete(cliente.getFoto())) {
					flash.addFlashAttribute("info", "Foto '" + cliente.getFoto() + "' eliminado con exito!");
				}
			}

			try {
				String uniqueFilename = uploadFileService.copy(file);
				flash.addFlashAttribute("info", "Has subido correctamente : '" + file.getOriginalFilename() + "'");
				cliente.setFoto(uniqueFilename);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (cliente == null) {
			mensaje = "Cliente creado con éxito";
		} else {
			mensaje = "Cliente modificado con éxito";
		}
		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		model.addAttribute("titulo", "Listado de clientes");
		return "redirect:/Cliente/listar";
	}

	@GetMapping(value = "/Cliente/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Model model,RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "Cliente no existe!");
				return "redirect:/Cliente/listar";
			}
		} else {
			flash.addFlashAttribute("error", "Id del cliente no puede ser 0");
			model.addAttribute("titulo", "Listado de clientes");
			return "redirect:/Cliente/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Editar cliente");
		return "/cliente/form";
	}

	@GetMapping(value = "/Cliente/del/{id}")
	public String borrar(@PathVariable(value="id") Long id, Model model,RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);
			String filename = cliente.getFoto();
			clienteService.delete(id);
			if (filename != null && filename.length() > 0
					&& uploadFileService.delete(filename)) {
				flash.addFlashAttribute("info", "Foto '" + filename + "' eliminado con exito!");
			}
		}
		flash.addFlashAttribute("success", "Cliente eliminado con éxito!");
		model.addAttribute("titulo", "Listado de clientes");
		return "redirect:/Cliente/listar";
	}

}
