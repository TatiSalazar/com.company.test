package com.company.api.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.company.test.TestApplication;
import com.company.test.controller.ClientController;
import com.company.test.model.dtos.ClientDto;
import com.company.test.service.IClientService;

@WebMvcTest(ClientController.class)
@ContextConfiguration(classes = TestApplication.class)
class ClientControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IClientService clientService;

	@Test
	void getClientsReturnsClientsSuccessfully() throws Exception {
		when(clientService.findAll()).thenReturn(List.of(
				new ClientDto(1, "Ana", "Gómez", "Pérez", "+57 3001234567")));

		mockMvc.perform(get("/api/clients"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].firstName").value("Ana"))
				.andExpect(jsonPath("$[0].lastName").value("Gómez"));

		verify(clientService).findAll();
	}

	@Test
	void saveClientReturnsSuccessResponse() throws Exception {
		when(clientService.save(org.mockito.ArgumentMatchers.any(ClientDto.class))).thenReturn(1);

		mockMvc.perform(post("/api/client")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"firstName\":\"Ana\",\"lastName\":\"Gómez\",\"surname\":\"Pérez\",\"phone\":\"+57 3001234567\"}"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("Client saved with success"));

		verify(clientService).save(org.mockito.ArgumentMatchers.any(ClientDto.class));
	}

	@Test
	void deleteClientReturns204WhenClientExists() throws Exception {
		when(clientService.deleteById(1)).thenReturn(1);

		mockMvc.perform(delete("/api/clientes/1"))
				.andExpect(status().isNoContent())
				.andExpect(content().string(""));

		verify(clientService).deleteById(1);
	}

	@Test
	void deleteClientReturns404WhenClientDoesNotExist() throws Exception {
		when(clientService.deleteById(999)).thenReturn(0);

		mockMvc.perform(delete("/api/clientes/999"))
				.andExpect(status().isNotFound())
				.andExpect(content().string(""));

		verify(clientService).deleteById(999);
	}
}
