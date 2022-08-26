package com.bolsedeideasspringboot.jpa.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PRODUCTOS",
indexes = {@Index(name = "IDX2_PRODUCTOS", columnList = "PROD_NOMBRE",unique = false)})
public class Producto implements Serializable {

	@Id
    @SequenceGenerator(name="seqProducto", sequenceName="SEQ_PRODUCTO", allocationSize=1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seqProducto")
	@Column(name = "PRODUCTO_ID")
	private Long id;

	@Column(name = "PROD_NOMBRE")
	private String nombre;

	@Column(name = "PROD_PRECIO")
	private Double precio;

	@NotNull
	@Column(name = "PROD_CREATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date createAt;

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

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

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Producto() {
		super();
	}

	public Producto(String nombre, Double precio) {
		super();
		this.nombre = nombre;
		this.precio = precio;
	}

	private static final long serialVersionUID = -7020426545557287352L;

}
