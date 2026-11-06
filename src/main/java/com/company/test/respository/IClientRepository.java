package com.company.test.respository;

import java.util.List;

import com.company.test.model.Client;

public interface IClientRepository {
	
  public List<Client> findAll(); 
  public int  save(Client client);
  public int deleteById(int id);
  
}
