package com.company.test.service;

import java.util.List;
import com.company.test.model.dtos.ClientDto;

public interface IClientService {
	public List<ClientDto> findAll();
	public int save(ClientDto client);
	public int deleteById(int id);
}
