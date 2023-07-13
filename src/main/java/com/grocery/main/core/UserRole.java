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
@Table(name = "user_role")
//@EntityListeners(AuditLogEntityListener.class)
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "role_name")
	private String roleName;
	
	//@Column(name = "password")
	//private String password;
}
