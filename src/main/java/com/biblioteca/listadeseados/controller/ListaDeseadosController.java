package com.biblioteca.listadeseados.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.biblioteca.listadeseados.dto.ListaDeseadosRequestDTO;
import com.biblioteca.listadeseados.dto.ListaDeseadosResponseDTO;
import com.biblioteca.listadeseados.service.ListaDeseadosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/api/v2/lista-deseados")
@Tag(name = "Lista de deseados", description = "Operaciones de juegos deseados por usuario")
public class ListaDeseadosController {
    @Autowired
    private ListaDeseadosService listaDeseadosService;
    @GetMapping
    @Operation(summary = "Listar todos los elementos deseados")
    public List<ListaDeseadosResponseDTO> obtenerTodas() {
        return listaDeseadosService.obtenerTodas();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un elemento deseado por ID")
    public ResponseEntity<ListaDeseadosResponseDTO> obtenerPorId(@PathVariable Long id) {
        return listaDeseadosService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar juegos deseados por usuario")
    public List<ListaDeseadosResponseDTO> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return listaDeseadosService.obtenerPorUsuario(usuarioId);
    }
    @PostMapping
    @Operation(summary = "Agregar un juego a la lista de deseados")
    public ResponseEntity<ListaDeseadosResponseDTO> agregar(
            @Valid @RequestBody ListaDeseadosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(listaDeseadosService.agregar(dto));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un elemento de la lista")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        listaDeseadosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/usuario/{usuarioId}/juego/{juegoId}")
    @Operation(summary = "Eliminar un juego deseado por usuario y juego")
    public ResponseEntity<Void> eliminarPorUsuarioYJuego(
            @PathVariable Long usuarioId,
            @PathVariable Long juegoId) {
        listaDeseadosService.eliminarPorUsuarioYJuego(usuarioId, juegoId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/usuario/{usuarioId}")
    @Operation(summary = "Vaciar la lista de deseados de un usuario")
    public ResponseEntity<Void> vaciarPorUsuario(@PathVariable Long usuarioId) {
        listaDeseadosService.vaciarPorUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
