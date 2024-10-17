package com.oms.service;

import java.util.List;
import java.util.Optional;

import com.oms.dao.Interface.UserDAO;
import com.oms.dao.implementation.UserDAOImpl;
import com.oms.model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAOImpl(); 
    }

    public boolean createUser(User user) {
        return userDAO.create(user);
    }

    public boolean updateUser(User user) {
        return userDAO.update(user);
    }

    public boolean deleteUser(int id) {
        return userDAO.delete(id);
    }

    public Optional<User> getUserById(int id) {
        return userDAO.getById(id);
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

  
}
