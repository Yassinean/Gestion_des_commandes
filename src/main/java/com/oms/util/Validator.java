package com.oms.util;

import java.util.HashMap;
import java.util.Map;

public class Validator {
	
	public static Map<String, String> validateAdmin(String name, String prenom, String email, String password, String adminTypeparam) {
        Map<String, String> errors = new HashMap<>();
        
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Le nom est obligatoire");
        } else if (name.length() > 50) {
            errors.put("name", "Le nom ne doit pas dépasser 50 caractères");
        }

        if (prenom == null || prenom.trim().isEmpty()) {
            errors.put("prenom", "Le prénom est obligatoire");
        } else if (prenom.length() > 50) {
            errors.put("prenom", "Le prénom ne doit pas dépasser 50 caractères");
        }

        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "L'email est obligatoire");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", "Format d'email invalide");
        }

        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "Le mot de passe est obligatoire");
        } else if (password.length() < 8) {
            errors.put("password", "Le mot de passe doit contenir au moins 8 caractères");
        }

        if (adminTypeparam == null || adminTypeparam.trim().isEmpty()) {
            errors.put("adminType", "Le type d'admin est obligatoire");
        }

        return errors;
    }
	public static Map<String, String> validateClient(String name, String prenom, String email, String password, String adresse, double moyenPaiement) {
        Map<String, String> errors = new HashMap<>();
        
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Le nom est obligatoire");
        } else if (name.length() > 50) {
            errors.put("name", "Le nom ne doit pas dépasser 50 caractères");
        }

        if (prenom == null || prenom.trim().isEmpty()) {
            errors.put("prenom", "Le prénom est obligatoire");
        } else if (prenom.length() > 50) {
            errors.put("prenom", "Le prénom ne doit pas dépasser 50 caractères");
        }

        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "L'email est obligatoire");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", "Format d'email invalide");
        }

        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "Le mot de passe est obligatoire");
        } else if (password.length() < 8) {
            errors.put("password", "Le mot de passe doit contenir au moins 8 caractères");
        }

        if (adresse == null || adresse.trim().isEmpty()) {
            errors.put("adresse", "L'adresse est obligatoire pour la liveraison");
        }
        
        if(moyenPaiement == 0 || moyenPaiement<0 ) {
            errors.put("moyen paiement", "Le moyen de paiement est obligatoire");
        }
        return errors;
    }
}
