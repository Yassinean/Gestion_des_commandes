package com.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends User{
	@Column(name = "adminType" , nullable = false)
	@Enumerated(EnumType.STRING)
	private AdminType adminType;

	public Admin(int id, String nom, String prenom, String email, String motDePasse, AdminType adminType) {
		super(id, nom, prenom, email, motDePasse);
		this.adminType = adminType;
	}

	public Admin() {
		super();
	}

	public AdminType getAdminType() {
		return adminType;
	}

	public void setAdminType(AdminType adminType) {
		this.adminType = adminType;
	}
	
}