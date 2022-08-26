package com.bolsedeideasspringboot.jpa.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	private MessageSource messageSource;

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

	@Secured({"ROLE_USER"})
	@GetMapping(value = "/Cliente/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model,RedirectAttributes flash , Locale locale) {
		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
			return "redirect:/Cliente/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.detalle.titulo", null, locale).concat(": ").concat(cliente.getNombre()));
		return "/cliente/ver";

	}

	
	@RequestMapping(value = { "/Cliente/listar"}, method = RequestMethod.GET )
	public String listar(@RequestParam(name = "page",defaultValue = "0") int page,Model model, Authentication authentication, HttpServletRequest request, Locale locale) {
		
		if (authentication != null) {
			logger.info("usuario autenticado:".concat(authentication.getName()));
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			logger.info("usuario autenticado usando forma estática:".concat(auth.getName()));
		}
		
//		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
//		
//		if (securityContext.isUserInRole("ADMIN")) {
//			logger.info("Tienes acceso ADMIN");
//		}
		
		if (request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Tienes acceso ADMIN");
		}
		

		Pageable pageRequest = PageRequest.of(page,5);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<>("/Cliente/listar", clientes);

		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listado.titulo", null, locale));
		model.addAttribute("clientes",clientes);
		model.addAttribute("page",pageRender);
		return "/cliente/listar";
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/Cliente/form")
	public String crear(Model model, Locale locale) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo",  messageSource.getMessage("text.cliente.form.titulo.crear", null, locale));
		return "/cliente/form";
	}

	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/Cliente/form")
	public String guardar(@Valid Cliente cliente,@RequestParam("file") MultipartFile file, BindingResult result,Model model
			,RedirectAttributes flash,SessionStatus status, Locale locale) {
		String mensaje;
		if (result.hasErrors()) {
			if (cliente == null || cliente.getId() == null) {
				model.addAttribute("titulo", messageSource.getMessage("text.cliente.form.titulo.crear", null, locale));
			} else {
				model.addAttribute("titulo", messageSource.getMessage("text.cliente.form.titulo.editar", null, locale));
			}
			return "/cliente/form";
		}
		if (!file.isEmpty()) {

			if (cliente.getId()!= null
				&& cliente.getId() > 0
				&& cliente.getFoto() != null
				&& cliente.getFoto().length() >0 ) {

				uploadFileService.delete(cliente.getFoto());
			}

			try {
				String uniqueFilename = uploadFileService.copy(file);

				flash.addFlashAttribute("info",
				messageSource.getMessage("text.cliente.flash.foto.subir.success", null, locale) + "'" + uniqueFilename + "'");
				cliente.setFoto(uniqueFilename);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		String id_mensaje;
		if (cliente == null) {
			id_mensaje = "text.cliente.flash.crear.success";
		} else {
			id_mensaje = "text.cliente.flash.editar.success";
		}
		String mensajeFlash = messageSource.getMessage(id_mensaje, null, locale);


		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);

	    return "redirect:/Cliente/listar";
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping(value = "/Cliente/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Model model,RedirectAttributes flash, Locale locale) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.db.error", null, locale));
				return "redirect:/Cliente/listar";
			}
		} else {
			flash.addFlashAttribute("error", messageSource.getMessage("text.cliente.flash.id.error", null, locale));
			model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
			return "redirect:/Cliente/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.form.titulo.editar", null, locale));
		return "/cliente/form";
	}

	@GetMapping(value = "/Cliente/del/{id}")
	public String borrar(@PathVariable(value="id") Long id, Model model,RedirectAttributes flash, Locale locale) {
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);
			String filename = cliente.getFoto();
			clienteService.delete(id);
			if (filename != null && filename.length() > 0
					&& uploadFileService.delete(filename)) {
						String mensajeFotoEliminar = String.format(messageSource.getMessage("text.cliente.flash.foto.eliminar.success", null, locale), cliente.getFoto());
						flash.addFlashAttribute("info", mensajeFotoEliminar);
			}
		}
		flash.addFlashAttribute("success", messageSource.getMessage("text.cliente.flash.eliminar.success", null, locale));
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		return "redirect:/Cliente/listar";
	}
	
	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return false;
		}
		Authentication auth = context.getAuthentication();
		if (auth == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
		/* for (GrantedAuthority authority : authorities ) {
			if (role.equals(authority.getAuthority())) {
				logger.info("Usuario ".concat(auth.getName()).concat(" , rol: ").concat(authority.getAuthority()));
				return true;
			}
		}
		return false; */
	}
}
