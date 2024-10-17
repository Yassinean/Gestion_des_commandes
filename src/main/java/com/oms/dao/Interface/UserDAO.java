package com.oms.dao.Interface;


import java.util.List;
import java.util.Optional;

import com.oms.model.User;

public interface UserDAO{
	
	 boolean create(User user);
	 boolean update(User user);
	 boolean delete(int id);
	 Optional<User> getById(int id);
	 List<User>getAll();

}
	
