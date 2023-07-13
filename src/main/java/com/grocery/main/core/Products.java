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
@Table(name = "Products")
//@EntityListeners(AuditLogEntityListener.class)
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Categories categories;
	 
	@Column(name = "price")
	private Long price;
		
	@Column(name = "quantity")
	private Long quantity;
		
}
