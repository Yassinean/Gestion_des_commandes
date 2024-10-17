package com.oms.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  
public abstract class User { 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String motDePasse;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private role userType;
	
	public User(int id, String nom, String prenom, String email, String motDePasse ,role userType) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		setMotDePasse(motDePasse);
		this.userType =userType;
	}
	
	public User(String nom, String prenom, String email, String motDePasse, role userType) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.userType =userType;
	}

	public User() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public role getUserType() {
		return userType;
	}

	public void setUserType(role userType) {
		this.userType = userType;
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = hashPassword(motDePasse);
	}
	private String hashPassword (String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	public boolean checkPassword (String password) {
		return BCrypt.checkpw(password, this.motDePasse);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", motDePasse="
				+ motDePasse + "]";
	}

}