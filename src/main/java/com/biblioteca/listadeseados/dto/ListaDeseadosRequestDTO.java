package com.biblioteca.listadeseados.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ListaDeseadosRequestDTO {

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El juegoId es obligatorio")
    private Long juegoId;
}
