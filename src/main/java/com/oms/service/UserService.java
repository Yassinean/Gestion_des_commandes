package com.oms.service;

import java.util.List;
import java.util.Optional;

import com.oms.dao.Interface.UserDAO;
import com.oms.dao.implementation.UserDAOImpl;
import com.oms.model.Admin;
import com.oms.model.Client;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl(); 
    }

    public boolean createClient(Client client) {
        return userDAO.createClient(client);
    }

    public boolean updateClient(Client client) {
        return userDAO.updateClient(client);
    }

    public boolean deleteClient(int id) {
        return userDAO.deleteClient(id);
    }

    public Optional<Client> getClientById(int id) {
        return userDAO.getClientById(id);
    }

    public List<Client> getAllClients() {
        return userDAO.getAllClients();
    }
    public boolean createAdmin(Admin admin) {
        return userDAO.createAdmin(admin);
    }

    public boolean updateAdmin(Admin admin) {
        return userDAO.updateAdmin(admin);
    }

    public boolean deleteAdmin(int id) {
        return userDAO.deleteAdmin(id);
    }

    public Optional<Admin> getAdminById(int id) {
        return userDAO.getAdminById(id);
    }

    public List<Admin> getAllAdmins() {
        return userDAO.getAllAdmins();
    }
    public List<Admin> searchAdmin(String terme) {
    	return userDAO.searchAdmin(terme);
    }
    public List<Client> searchClient(String terme) {
    	return userDAO.searchClient(terme);
    }
    
    public Optional<Admin> authenticateAdmin(String email, String password) {
         return userDAO.authenticateAdmin(email, password); 
    }

    public Optional<Client> authenticateClient(String email, String password) {
        return userDAO.authenticateClient(email, password);
    }
    
}
