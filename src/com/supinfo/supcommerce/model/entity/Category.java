package com.supinfo.supcommerce.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Category
 * 
 * Product categories
 * 
 * @author Elka
 * @version 4.2
 * @since SupCommerce 4.1
 */
@Entity
@Table(name = "categories")
public class Category implements Serializable {
	private static final long	serialVersionUID	= 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long				id;
	private String				name;
	@OneToMany(mappedBy = "category")
	private List<Product>		produtcs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProdutcs() {
		return produtcs;
	}

	public void setProdutcs(List<Product> produtcs) {
		this.produtcs = produtcs;
	}

}
