package com.bolsedeideasspringboot.jpa.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id","rol"})})
public class Roles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8253392393441984291L;
	@Id
    @SequenceGenerator(name="seqRol", sequenceName="SEQ_ROL", allocationSize=1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seqRol")
	private Long id;
	
	@Column(length = 45,name = "rol")
	private String authority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	

}
