package com.company.test.model.dtos;

import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Datos de un cliente")
public class ClientDto {
	@Schema(description = "Identificador del cliente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
	int id;
	@Schema(description = "Nombres del cliente", example = "Ana")
	String firstName;
	@Schema(description = "Apellidos del cliente", example = "Gómez")
	String lastName;
	@Schema(description = "Segundo apellido del cliente", example = "Pérez")
	String surname;
	@Schema(description = "Número telefónico del cliente", example = "+57 3001234567")
	String phone;
	
	public ClientDto() {
	}

	 public ClientDto(int id, String firstName, String lastName, String surname, String phone){
		super();
		this.id = id; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.surname = surname; 
		this.phone = phone;
	 }
}
