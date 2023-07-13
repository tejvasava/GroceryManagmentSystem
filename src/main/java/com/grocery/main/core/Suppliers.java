package com.grocery.main.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Suppliers")
//@EntityListeners(AuditLogEntityListener.class)
public class Suppliers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id")
	private Long supplierId;
	
	@Column(name = "supplier_name")
	private String supplierName;
	   
	@Column(name = "contact_person")
	private String contactPerson;
	   
	@Column(name = "email")
	private String email;
	   
	@Column(name = "phone_number")
	private Long phoneNumber;
	   
	@Column(name = "address")
	private String address;
	 
}
