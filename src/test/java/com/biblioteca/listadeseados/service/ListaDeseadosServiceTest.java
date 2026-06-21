package com.biblioteca.listadeseados.service;

import com.biblioteca.listadeseados.client.JuegoClient;
import com.biblioteca.listadeseados.client.UsuarioClient;
import com.biblioteca.listadeseados.dto.ListaDeseadosResponseDTO;
import com.biblioteca.listadeseados.model.ListaDeseadosItem;
import com.biblioteca.listadeseados.repository.ListaDeseadosRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListaDeseadosServiceTest {

    @Mock
    private ListaDeseadosRepository listaDeseadosRepository;
    @Mock
    private JuegoClient juegoClient;
    @Mock
    private UsuarioClient usuarioClient;
    @InjectMocks
    private ListaDeseadosService listaDeseadosService;

    private final Faker faker = new Faker();

    @Test
    void obtenerPorIdRetornaElJuegoDeseado() {
        Long id = faker.number().numberBetween(1L, 1000L);
        Long juegoId = faker.number().numberBetween(1L, 1000L);
        ListaDeseadosItem item = new ListaDeseadosItem(id, 10L, juegoId, LocalDateTime.now());
        when(listaDeseadosRepository.findById(id)).thenReturn(Optional.of(item));

        Optional<ListaDeseadosResponseDTO> resultado = listaDeseadosService.obtenerPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(juegoId, resultado.get().juegoId());
    }

    @Test
    void eliminarLanzaExcepcionCuandoElItemNoExiste() {
        Long id = faker.number().numberBetween(1L, 1000L);
        when(listaDeseadosRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> listaDeseadosService.eliminar(id));
        verify(listaDeseadosRepository, never()).deleteById(id);
    }
}
