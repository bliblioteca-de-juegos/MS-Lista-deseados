# MS-Lista-deseados

Microservicio encargado de administrar la lista de juegos que un usuario desea comprar en el futuro.

## Responsabilidad

- Agregar juegos a la lista de deseados.
- Consultar deseados por usuario.
- Eliminar un juego deseado.
- Vaciar la lista de un usuario.
- Validar existencia de usuario y juego.

## Datos tecnicos

| Item | Valor |
| --- | --- |
| Puerto | `8087` |
| Base de datos | `lista_deseados_db` |
| Ruta base | `/api/v2/lista-deseados` |
| Swagger | `http://localhost:8087/doc/swagger-ui.html` |
| Eureka name | `ms-lista-deseados` |

## Endpoints principales

- `GET /api/v2/lista-deseados`
- `GET /api/v2/lista-deseados/{id}`
- `GET /api/v2/lista-deseados/usuario/{usuarioId}`
- `POST /api/v2/lista-deseados`
- `DELETE /api/v2/lista-deseados/{id}`
- `DELETE /api/v2/lista-deseados/usuario/{usuarioId}/juego/{juegoId}`
- `DELETE /api/v2/lista-deseados/usuario/{usuarioId}`

## Comunicacion

- Usa Feign Client para consultar juegos en `ms-juegos`.
- Usa WebClient para consultar usuarios en `ms-usuario`.
- Se registra en Eureka.

## Ejecucion local

```bash
./mvnw spring-boot:run
```

## Ejecucion con Docker

Desde la repo `Infraestructura`:

```bash
docker compose up -d --build ms-lista-deseados
```

