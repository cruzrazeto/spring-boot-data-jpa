package com.bolsedeideasspringboot.jpa.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CLIENTES",
    indexes = {@Index(name = "IDX2_CLIENTE", columnList = "USER_NAME,USER_LNAME",unique = false)})
public class Cliente implements Serializable {
	private static final long serialVersionUID = 4327690299451102763L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;
	@NotEmpty
	@Column(name = "USER_NAME")
	private String nombre;
	@NotEmpty
	@Column(name = "USER_LNAME")
	private String apellido;
	@NotEmpty
	@Email
	@Column(name = "USER_EMAIL")
	private String email;
	@NotNull
	@Column(name = "USER_CREATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createAt;

	@Column(name = "USER_FOTO")
	private String foto;
	//@PrePersist
	//public void prePersist() {
	//	createAt = new Date();
	//}

	@OneToMany(mappedBy ="cliente",fetch=FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Factura> facturas;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Cliente(Long id, String nombre, String apellido, String email, Date createAt) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.createAt = createAt;
		this.facturas = new ArrayList<>();
	}
	public Cliente() {
		super();
		this.facturas = new ArrayList<>();
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public List<Factura> getFacturas() {
		return facturas;
	}
	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	public void addFacturas(Factura factura) {
		this.facturas.add(factura);
	}

}
