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
@Table(name = "Employees_customer")
//@EntityListeners(AuditLogEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "first_name")
	private String firstName;
	 
	@Column(name = "last_name")
	private String lastName;
	  
	@Column(name = "email")
	private String email;
	 
	@Column(name = "phone_number")
	private String phoneNumber;
	  
	@Column(name = "address")
	private String address;
	
	@Column(name = "password")
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private UserRole userRole;
	 
}
