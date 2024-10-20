package com.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "admin")
public class Admin extends User {
    
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "admin_type", nullable = false)
    private AdminType adminType;

	public Admin(int id, String nom, String prenom, String email, String motDePasse, AdminType adminType) {
		super(id, nom, prenom, email, motDePasse, role.admin);
		this.adminType = adminType;
	}
	public Admin(String nom, String prenom, String email, String motDePasse, AdminType adminType) {
		super(nom, prenom, email, motDePasse,role.admin);
		this.adminType = adminType;
	}

	public Admin() {
		super();
		setUserType(role.admin);
	}

	public AdminType getAdminType() {
		return adminType;
	}

	public void setAdminType(AdminType adminType) {
		this.adminType = adminType;
	}
	
	@Override
	public String toString() {
	    return "Admin [" + super.toString() + ", adminType=" + adminType + "]";
	}
	
	
}