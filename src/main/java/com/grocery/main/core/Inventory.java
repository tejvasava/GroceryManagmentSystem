package com.grocery.main.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Inventory")
public class Inventory  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long inventoryId;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Products products;
	
	@Column(name = "stock_quantity")
	private Long  stockQuantity;

	 
}
