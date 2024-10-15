package com.oms.dao.implementation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.oms.dao.Interface.UserDAO;
import com.oms.model.Admin;
import com.oms.model.Client;
import com.oms.model.User;
import com.oms.model.role;

public class UserDAOImpl implements UserDAO  {
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public UserDAOImpl() {
		emf = Persistence.createEntityManagerFactory(null);
		em = emf.createEntityManager();
	}
	@Override
	public boolean create(User user) {
		try {
			em.getTransaction().begin();
			em.persist(user);
			if(user.getUserType() ==role.admin) {
				Admin admin = (Admin) user;
				em.persist(admin);
			}else if (user.getUserType() == role.client) {
				Client client =(Client) user;
				em.persist(client);
			}
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			em.close();
		}
	}
	@Override
	public boolean update(User user) {
		try {
			Optional<User> existingUserOpt = getById(user.getId());
			if (existingUserOpt.isPresent()) {
				User existingUser = existingUserOpt.get();
				existingUser.setNom(user.getNom());
				existingUser.setPrenom(user.getPrenom());
				existingUser.setEmail(user.getEmail());
				existingUser.setMotDePasse(user.getMotDePasse());
				existingUser.setUserType(user.getUserType());
				if (user.getUserType() == role.admin) {
					Admin admin = (Admin) user;
	                ((Admin)existingUser).setAdminType(admin.getAdminType()); 

				}else if (user.getUserType() == role.client) {
					Client client = (Client) user;
					((Client) existingUser).setAdresse(client.getAdresse());
					((Client) existingUser).setMoyenPaiment(client.getMoyenPaiment());

				}
				  em.getTransaction().begin();
		          em.merge(existingUser); 
		          em.getTransaction().commit(); 
		          return true;
			
			}else {
			       System.out.println("User not found with ID: " + user.getId());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Optional<User> getById(int id) {
		return Optional.ofNullable(em.find(User.class, id));
	}
	
	
	@Override
	public boolean delete(int id) {
	    try {
	        em.getTransaction().begin();
	        
	        Optional<User> existingUserOpt = getById(id);
	        
	        if (existingUserOpt.isPresent()) {
	            User existingUser = existingUserOpt.get();
	            
	            em.remove(existingUser);
	            
	            em.getTransaction().commit();
	            return true; 
	        } else {
	            System.out.println("User not found with ID: " + id);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		  try {
		      users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
		  } catch (Exception e) {
		        e.printStackTrace();
		  }
		    return users;
	}
		
}