package com.bolsedeideasspringboot.jpa.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FACTURASITEM")
public class ItemFactura implements Serializable {

	@Id
    @SequenceGenerator(name="seqFacturaItem", sequenceName="SEQ_FACTURAITEM", allocationSize=1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seqFacturaItem")
	@Column(name = "FACTITEM_ID")
	private Long id;

	@Column(name = "FACTITEM_CANT")
	private Long cantidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id", nullable = false, updatable = true)
	private Producto producto;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "FACT_ID", nullable = false, updatable = false)
//    private Factura factura;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}


//	public Factura getFactura() {
//		return factura;
//	}
//
//	public void setFactura(Factura factura) {
//		this.factura = factura;
//	}

	public Double calcularImporte() {
		if (producto == null)
			return 0d;
		return this.cantidad.doubleValue() * producto.getPrecio();
	}

	private static final long serialVersionUID = -7392400486293038214L;

}
