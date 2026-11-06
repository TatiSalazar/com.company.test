package com.company.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.company.test.model.dtos.ClientDto;
import com.company.test.model.dtos.ServiceResponse;
import com.company.test.service.IClientService;

@RestController
@CrossOrigin(origins = { "http://localhost:8080" })
@RequestMapping("/api")
@Tag(name = "Clientes", description = "Operaciones para consultar y registrar clientes")
public class ClientController {
	@Autowired
	private IClientService clientService;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/clients")
	@Operation(
			summary = "Listar clientes",
			description = "Obtiene todos los clientes registrados en la base de datos.")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Clientes consultados correctamente",
					content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClientDto.class)))),
			@ApiResponse(responseCode = "500", description = "Error interno al consultar los clientes", content = @Content)
	})
	public ResponseEntity<List<ClientDto>> getClients() {
		return ResponseEntity.ok(clientService.findAll());
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/client")
	@Operation(
			summary = "Registrar cliente",
			description = "Registra un nuevo cliente y devuelve el resultado de la operación.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Datos del cliente que se desea registrar",
					required = true,
					content = @Content(schema = @Schema(implementation = ClientDto.class))))
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Cliente procesado correctamente",
					content = @Content(schema = @Schema(implementation = ServiceResponse.class))),
			@ApiResponse(responseCode = "400", description = "Datos del cliente inválidos", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno al registrar el cliente", content = @Content)
	})
	public ResponseEntity<ServiceResponse> saveClient(@RequestBody ClientDto client) {
		ServiceResponse serviceResponse = new ServiceResponse();
		int result = clientService.save(client);
		if (result == 1) {
			serviceResponse.setSuccess(true);
			serviceResponse.setMessage("Client saved with success");
		}

		return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/clientes/{id}")
	@Operation(
			summary = "Eliminar cliente",
			description = "Elimina un cliente existente usando su identificador.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente"),
			@ApiResponse(responseCode = "404", description = "No existe un cliente con el identificador indicado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno al eliminar el cliente", content = @Content)
	})
	public ResponseEntity<Void> deleteClient(
			@Parameter(description = "Identificador del cliente que se desea eliminar", example = "1", required = true)
			@PathVariable int id) {
		if (clientService.deleteById(id) == 0) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.noContent().build();
	}

}
