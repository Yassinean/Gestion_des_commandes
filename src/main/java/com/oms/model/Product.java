package com.oms.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "produits")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Integer stock;

    
    public Product() {}


	public Product(String name, String description, Double price, Integer stock) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}
    
	public Product(Long id, String name, String description, Double price, Integer stock) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Integer getStock() {
		return stock;
	}


	public void setStock(Integer stock) {
		this.stock = stock;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", stock="
				+ stock + "]";
	}
	    
}
