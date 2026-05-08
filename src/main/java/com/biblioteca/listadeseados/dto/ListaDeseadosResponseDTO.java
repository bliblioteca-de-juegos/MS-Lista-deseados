package com.biblioteca.listadeseados.dto;

import java.time.LocalDateTime;

public record ListaDeseadosResponseDTO(
        Long id,
        Long usuarioId,
        Long juegoId,
        LocalDateTime agregadoEn,
        JuegoDTO juego
) {
}
