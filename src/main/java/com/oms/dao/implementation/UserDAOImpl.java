package com.oms.dao.implementation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.mindrot.jbcrypt.BCrypt;

import com.oms.dao.Interface.UserDAO;
import com.oms.model.Admin;
import com.oms.model.Client;


public class UserDAOImpl implements UserDAO  {
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public UserDAOImpl() {
		emf = Persistence.createEntityManagerFactory(null);
		em = emf.createEntityManager();
	}
	
    @Override
	public boolean createAdmin(Admin admin) {
        try {
            em.getTransaction().begin();
            em.persist(admin);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }
    @Override
    public boolean createClient(Client client) {
        try {
            em.getTransaction().begin();
            em.persist(client);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }
    
    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();       
        admins.addAll(em.createQuery("SELECT a FROM Admin a ORDER BY a.id ASC", Admin.class).getResultList());                
        return admins;
    }
    
    @Override
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();       
        clients.addAll(em.createQuery("SELECT a FROM Client a ORDER BY a.id ASC", Client.class).getResultList());                
        return clients;
    }
    
    @Override
    public boolean updateAdmin(Admin admin) {
        try {
            Optional<Admin> existingAdminOpt = getAdminById(admin.getId());
            if (existingAdminOpt.isPresent()) {
                Admin existingAdmin = existingAdminOpt.get();
                em.getTransaction().begin();
                existingAdmin.setNom(admin.getNom());
                existingAdmin.setPrenom(admin.getPrenom());
                existingAdmin.setEmail(admin.getEmail());
                existingAdmin.setMotDePasse(admin.getMotDePasse());
                
                existingAdmin.setAdminType(admin.getAdminType());
                
                em.merge(existingAdmin);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("Admin not found with ID: " + admin.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean updateClient(Client client) {
        try {
            Optional<Client> existingClientOpt = getClientById(client.getId());
            if (existingClientOpt.isPresent()) {
                Client existingClient = existingClientOpt.get();
                em.getTransaction().begin();
                
                existingClient.setNom(client.getNom());
                existingClient.setPrenom(client.getPrenom());
                existingClient.setEmail(client.getEmail());
                existingClient.setMotDePasse(client.getMotDePasse());
                
                existingClient.setAdresse(client.getAdresse());
                existingClient.setMoyenPaiment(client.getMoyenPaiment());
                
                em.merge(existingClient);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("Client not found with ID: " + client.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean deleteAdmin(int id) {
        try {
            Optional<Admin> adminOpt = getAdminById(id);
            if (adminOpt.isPresent()) {
                em.getTransaction().begin();
                Admin admin = adminOpt.get();
                em.remove(admin);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("Admin not found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean deleteClient(int id) {
        try {
            Optional<Client> clientOpt = getClientById(id);
            if (clientOpt.isPresent()) {
                em.getTransaction().begin();
                Client client = clientOpt.get();
                em.remove(client);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("Client not found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }   
    
    @Override
    public List<Admin> searchAdmin(String searchTerm) {
            String searchPattern = "%" + searchTerm + "%";
            return em.createQuery(
                "SELECT a FROM Admin a WHERE " +
               "LOWER(a.nom) LIKE LOWER(:searchTerm) OR " +
               "LOWER(a.prenom) LIKE LOWER(:searchTerm) " ,
                Admin.class)
            .setParameter("searchTerm", searchPattern)
            .getResultList();
    } 
    
    @Override
    public List<Client> searchClient(String searchTerm) {
            String searchPattern = "%" + searchTerm + "%";
            return em.createQuery(
                "SELECT a FROM Client a WHERE " +
                "LOWER(a.nom) LIKE LOWER(:searchTerm) OR " +
                "LOWER(a.prenom) LIKE LOWER(:searchTerm) " ,
                Client.class)
            .setParameter("searchTerm", searchPattern)
            .getResultList();
    }
    @Override
    public Optional<Admin> authenticateAdmin(String email, String password) {
        try {            
            Admin admin = em.createQuery(
                "SELECT a FROM Admin a WHERE a.email = :email", 
                Admin.class)
                .setParameter("email", email)
                .getSingleResult();
            
            System.out.println("Admin trouvé: " + admin);
            
            if (admin != null && BCrypt.checkpw(password, admin.getMotDePasse())) {
                return Optional.of(admin);
            }
            
            return Optional.empty();
        } catch (NoResultException e) {
            System.out.println("Admin introuvable avec cet email.");
            return Optional.empty();
        }
    }


    @Override
    public Optional<Client> authenticateClient(String email, String password) {
        try {            
            Client client = em.createQuery(
                "SELECT c FROM Client c WHERE c.email = :email", 
                Client.class)
                .setParameter("email", email)
                .getSingleResult();
                
            System.out.println("Client trouvé: " + client);
            
            if(client != null && BCrypt.checkpw(password, client.getMotDePasse())) {
                return Optional.of(client);
            }
            
            return Optional.empty();
        } catch (NoResultException e) {
            System.out.println("Client introuvable avec cet email.");
            return Optional.empty();
        } 
    }
    @Override
    public Optional<Admin> getAdminById(int id) {
        return Optional.ofNullable(em.find(Admin.class, id));
    }
    @Override
    public Optional<Client> getClientById(int id) {
        return Optional.ofNullable(em.find(Client.class, id));
    }
    	
		
}