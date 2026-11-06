package com.company.test.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado de una operación del servicio")
public class ServiceResponse {
	@Schema(description = "Indica si la operación fue exitosa", example = "true")
	private Boolean success;
	@Schema(description = "Mensaje descriptivo del resultado", example = "Client saved with success")
	private String message;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
