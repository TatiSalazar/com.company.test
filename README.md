# API de clientes

API REST desarrollada con Spring Boot para consultar, registrar y eliminar clientes. Incluye una base H2 persistente en modo archivo, datos iniciales de prueba y documentación interactiva con OpenAPI.

## Requisitos

- Java JDK 17.
- Maven 3.8 o superior, o usar el Maven Wrapper incluido (`./mvnw`).
- Git, si se desea clonar el proyecto.
- Puerto `8080` disponible.

No es necesario instalar H2 ni un servidor de base de datos para ejecutar la configuración local.

## Versiones principales

- Java: `17`.
- Spring Boot: `3.0.6`.
- SpringDoc OpenAPI WebMVC UI: `2.1.0`.
- H2: dependencia administrada por Spring Boot en alcance runtime.

## Clonar el proyecto

```bash
git clone https://github.com/TatiSalazar/com.company.test.git
cd com.company.test
```

## Ejecutar en local

En macOS o Linux:

```bash
./mvnw spring-boot:run
```

En Windows:

```bat
mvnw.cmd spring-boot:run
```

Alternativamente, si Maven está instalado:

```bash
mvn spring-boot:run
```

La aplicación inicia en:

`http://localhost:8080`

Al arrancar, Spring ejecuta `schema.sql` y `data.sql`. La base de datos se guarda en `./data/testdb`, por lo que los datos permanecen después de detener y volver a iniciar la aplicación.

## Ejecutar pruebas

```bash
./mvnw test
```

Las pruebas actuales cubren:

- Consulta exitosa de clientes.
- Registro exitoso de un cliente.
- Eliminación exitosa con respuesta `204`.
- Eliminación de un identificador inexistente con respuesta `404`.
- Carga del contexto de Spring.

## Swagger y OpenAPI

Con la aplicación ejecutándose, abrir:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Especificación OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Desde Swagger UI se pueden consultar los endpoints, sus parámetros, cuerpos de solicitud y códigos de respuesta.

## Endpoints disponibles

### Consultar clientes

```http
GET http://localhost:8080/api/clients
```

Respuesta esperada: `200 OK` con una lista JSON.

### Registrar cliente

```http
POST http://localhost:8080/api/client
Content-Type: application/json
```

Body de ejemplo:

```json
{
  "firstName": "Ana",
  "lastName": "Gómez",
  "surname": "Pérez",
  "phone": "+57 3001234567"
}
```

Respuesta esperada: `200 OK` con el resultado de la operación.

### Eliminar cliente existente

```http
DELETE http://localhost:8080/api/clientes/1
```

Respuesta esperada: `204 No Content`.

### Eliminar cliente inexistente

```http
DELETE http://localhost:8080/api/clientes/999
```

Respuesta esperada: `404 Not Found`.

## Datos de prueba

Los datos iniciales están en `src/main/resources/data.sql` y se cargan automáticamente al iniciar la aplicación. Para consultar o reutilizar el script manualmente está disponible:

```text
scripts/seed-test-data.sql
```

La base local se excluye del repositorio mediante `.gitignore`.

## Configuración de base de datos

La configuración activa usa H2 en modo archivo:

```properties
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:file:./data/testdb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
```

La configuración de SQL Server permanece comentada en `src/main/resources/application.properties` como plantilla para futuras pruebas. Antes de usarla se deben completar los marcadores con valores del ambiente correspondiente y reactivar también la dependencia del driver en `pom.xml`.

## Solución de problemas frecuentes

Si aparece un error indicando que la tabla `CLIENTS` no existe, confirmar que la aplicación se está iniciando desde la raíz del proyecto y que `spring.sql.init.mode=always` está presente. También se puede detener la aplicación, revisar la carpeta `data/` y volver a ejecutar las pruebas.

Si el puerto `8080` está ocupado, liberar el proceso que lo utiliza o cambiar `server.port` en `src/main/resources/application.properties`.

