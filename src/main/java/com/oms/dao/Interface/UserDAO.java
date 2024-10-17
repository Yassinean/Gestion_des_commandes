package com.oms.dao.Interface;


import java.util.List;
import java.util.Optional;

import com.oms.model.Admin;
import com.oms.model.Client;

public interface UserDAO{
	
	 	boolean createAdmin(Admin admin);

	    boolean createClient(Client client);

	    List<Admin> getAllAdmins();

	    List<Client> getAllClients();

	    boolean updateAdmin(Admin admin);

	    boolean updateClient(Client client);

	    boolean deleteAdmin(int id);

	    boolean deleteClient(int id);

	    Optional<Admin> getAdminById(int id);

	    Optional<Client> getClientById(int id);


}
	
