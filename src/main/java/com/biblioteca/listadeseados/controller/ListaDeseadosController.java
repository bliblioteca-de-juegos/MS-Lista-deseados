package com.biblioteca.listadeseados.controller;

import com.biblioteca.listadeseados.dto.ListaDeseadosRequestDTO;
import com.biblioteca.listadeseados.dto.ListaDeseadosResponseDTO;
import com.biblioteca.listadeseados.service.ListaDeseadosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ListaDeseadosController {

    private final ListaDeseadosService listaDeseadosService;

    @GetMapping
    public List<ListaDeseadosResponseDTO> obtenerTodas() {
        return listaDeseadosService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaDeseadosResponseDTO> obtenerPorId(@PathVariable Long id) {
        return listaDeseadosService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<ListaDeseadosResponseDTO> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return listaDeseadosService.obtenerPorUsuario(usuarioId);
    }

    @PostMapping
    public ResponseEntity<ListaDeseadosResponseDTO> agregar(
            @Valid @RequestBody ListaDeseadosRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(listaDeseadosService.agregar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        listaDeseadosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{usuarioId}/juego/{juegoId}")
    public ResponseEntity<Void> eliminarPorUsuarioYJuego(
            @PathVariable Long usuarioId,
            @PathVariable Long juegoId) {
        listaDeseadosService.eliminarPorUsuarioYJuego(usuarioId, juegoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> vaciarPorUsuario(@PathVariable Long usuarioId) {
        listaDeseadosService.vaciarPorUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
