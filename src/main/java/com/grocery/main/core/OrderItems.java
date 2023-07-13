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
@Table(name = "Order_Items")
//@EntityListeners(AuditLogEntityListener.class)
public class OrderItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long orderItemId;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Orders orders;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Products products;
	
	@Column(name = "quantity")
	private Long quantity;
	
	@Column(name = "price")
	private Long price;
	   
}
