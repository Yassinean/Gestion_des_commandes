package com.oms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client extends User {
    
    @Column(name = "adresse")
    private String adresse;
    
    @Column(name = "moyen_paiement")
    private double moyenPaiment;
	
	public Client(int id, String nom, String prenom, String email, String motDePasse, String adresse,
			double moyenPaiment) {
		super(id, nom, prenom, email, motDePasse,role.client);
		this.adresse = adresse;
		this.moyenPaiment = moyenPaiment;
	}

	public Client() {
		super();
		setUserType(role.client);
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public double getMoyenPaiment() {
		return moyenPaiment;
	}

	public void setMoyenPaiment(double moyenPaiment) {
		this.moyenPaiment = moyenPaiment;
	}
		
	
}