# Solution

## Que se implementó

Se dejó preparado un API REST de clientes con Spring Boot y se corrigieron los problemas necesarios para ejecutarlo localmente.

Los cambios principales fueron:

- Configuración del proyecto para Java 17 y Spring Boot 3.0.6.
- Migración de la ejecución local a H2 en modo archivo.
- Creación automática de la tabla `clients` mediante `schema.sql`.
- Carga de cinco registros de prueba mediante `data.sql`.
- Endpoint para consultar clientes: `GET /api/clients`.
- Endpoint para registrar clientes: `POST /api/client`.
- Endpoint para eliminar clientes: `DELETE /api/clientes/{id}`.
- Respuesta `204 No Content` cuando la eliminación es exitosa.
- Respuesta `404 Not Found` cuando el identificador no existe.
- Documentación interactiva con SpringDoc OpenAPI.
- Pruebas del controlador para consulta, creación y los dos resultados de eliminación.
- Script reutilizable en `scripts/seed-test-data.sql` para datos de prueba.
- Publicación del proyecto en GitHub.

## Como se implementó

La solución conserva una separación por capas:

- `ClientController`: expone los endpoints HTTP, documenta las operaciones y traduce el resultado de eliminación a los códigos HTTP correspondientes.
- `ClientService`: contiene la lógica de aplicación y transforma entre `Client` y `ClientDto`.
- `ClientRepository`: usa `JdbcTemplate` para ejecutar las consultas SQL.
- `Client` y `ClientDto`: representan el modelo persistido y el contrato de entrada/salida.

Se mantuvo Spring JDBC en lugar de Spring Data JPA. Esta decisión reduce la configuración requerida para este API y evita repetir el problema inicial relacionado con la creación de `entityManagerFactory`, ya que la solución usa consultas SQL explícitas.

SpringDoc se agregó con `springdoc-openapi-starter-webmvc-ui` versión `2.1.0`, compatible con la línea Spring Boot 3 utilizada por el proyecto. La configuración de OpenAPI se centralizó en `OpenApiConfig` y cada operación del controlador tiene resumen, descripción, parámetros y respuestas documentadas.

## Cambios en bases de datos

La conexión local actual es:

```properties
spring.datasource.url=jdbc:h2:file:./data/testdb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
```

El modo archivo permite conservar los datos entre reinicios. La carpeta `data/` queda excluida de Git porque contiene archivos binarios locales de H2.

`schema.sql` crea la tabla `clients` si todavía no existe. `data.sql` usa `MERGE` por identificador, por lo que los datos de ejemplo pueden cargarse repetidamente sin duplicar esos cinco registros.

La configuración y dependencia de SQL Server se dejaron comentadas como plantilla para futuras pruebas. Se reemplazaron los valores sensibles por marcadores (`<SQL_SERVER_HOST>`, `<SQL_SERVER_USERNAME>`, etc.) para evitar publicar credenciales en un repositorio público.

## Configuración

Dependencias relevantes:

- Spring Boot `3.0.6`.
- Java `17`.
- Spring Boot Starter Web.
- Spring Boot Starter JDBC.
- H2 en alcance runtime.
- SpringDoc OpenAPI WebMVC UI `2.1.0`.
- JUnit, Mockito y Spring MockMvc mediante `spring-boot-starter-test`.

La aplicación escucha en el puerto `8080`. La interfaz Swagger queda disponible en:

`http://localhost:8080/swagger-ui.html`

El documento OpenAPI en JSON queda disponible en:

`http://localhost:8080/v3/api-docs`

La aplicación permite solicitudes desde `http://localhost:4200`, de acuerdo con la configuración CORS existente.

## Dificultades encontradas

- La configuración original intentaba inicializar JPA/Hibernate sin una conexión funcional, provocando el error de creación de `entityManagerFactory`.
- Al cambiar a H2 inicialmente la tabla no existía; se resolvió agregando `schema.sql` y habilitando la inicialización SQL al arrancar.
- El controlador usaba `setSuccess` sobre `ServiceResponse`, pero el método no estaba disponible de forma resoluble; se agregaron explícitamente los métodos de acceso del DTO.
- El DTO necesitaba un constructor sin argumentos para facilitar la deserialización del cuerpo JSON en las pruebas y en Spring MVC.
- La configuración de SQL Server contenía valores que no debían publicarse; se conservaron las propiedades comentadas como plantilla, sustituyendo los datos sensibles por marcadores.

## Mejoras que se podrían aplicar con más tiempo

El alcance actual cubre el flujo solicitado, pero todavía hay mejoras razonables:

- Añadir validaciones Bean Validation (`@NotBlank`, tamaños y formato del teléfono) y respuestas consistentes para errores de entrada.
- Cambiar la respuesta de creación a `201 Created` y devolver la ubicación o representación del recurso creado.
- Unificar el idioma de los mensajes y los nombres de rutas; actualmente existe la combinación de `/clients`, `/client` y `/clientes` por compatibilidad con el código solicitado.
- Agregar manejo global de excepciones con `@RestControllerAdvice`.
- Aumentar la cobertura con pruebas de servicio, repositorio, errores de base de datos y documentación OpenAPI.
- Reemplazar la inyección de campos por inyección mediante constructor.
- Usar migraciones versionadas con Flyway o Liquibase en lugar de scripts de inicialización si el proyecto crece.
- Añadir paginación, búsqueda y actualización de clientes.
- Configurar perfiles separados para local, pruebas y ambientes compartidos.
- Incorporar autenticación, autorización, observabilidad y un pipeline de integración continua antes de usar el API en un ambiente productivo.

