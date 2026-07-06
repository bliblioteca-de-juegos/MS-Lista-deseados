package com.biblioteca.listadeseados.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.biblioteca.listadeseados.client.JuegoClient;
import com.biblioteca.listadeseados.client.UsuarioClient;
import com.biblioteca.listadeseados.dto.JuegoDTO;
import com.biblioteca.listadeseados.dto.ListaDeseadosRequestDTO;
import com.biblioteca.listadeseados.dto.ListaDeseadosResponseDTO;
import com.biblioteca.listadeseados.model.ListaDeseadosItem;
import com.biblioteca.listadeseados.repository.ListaDeseadosRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class ListaDeseadosService {
    @Autowired
    private ListaDeseadosRepository listaDeseadosRepository;
    @Autowired
    private JuegoClient juegoClient;
    @Autowired
    private UsuarioClient usuarioClient;
    public List<ListaDeseadosResponseDTO> obtenerTodas() {
        return listaDeseadosRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }
    public Optional<ListaDeseadosResponseDTO> obtenerPorId(Long id) {
        return listaDeseadosRepository.findById(id).map(this::mapToDTO);
    }
    public List<ListaDeseadosResponseDTO> obtenerPorUsuario(Long usuarioId) {
        validarUsuario(usuarioId);
        return listaDeseadosRepository.findByUsuarioId(usuarioId).stream()
                .map(this::mapToDTO)
                .toList();
    }
    @Transactional
    public ListaDeseadosResponseDTO agregar(ListaDeseadosRequestDTO dto) {
        validarUsuario(dto.getUsuarioId());
        validarJuego(dto.getJuegoId());
        if (listaDeseadosRepository.existsByUsuarioIdAndJuegoId(dto.getUsuarioId(), dto.getJuegoId())) {
            throw new IllegalArgumentException("El juego ya esta en la lista de deseados del usuario");
        }
        ListaDeseadosItem item = new ListaDeseadosItem(
                null,
                dto.getUsuarioId(),
                dto.getJuegoId(),
                LocalDateTime.now()
        );
        return mapToDTO(listaDeseadosRepository.save(item));
    }
    @Transactional
    public void eliminar(Long id) {
        if (!listaDeseadosRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe un item de lista de deseados con id " + id);
        }
        listaDeseadosRepository.deleteById(id);
    }
    @Transactional
    public void eliminarPorUsuarioYJuego(Long usuarioId, Long juegoId) {
        ListaDeseadosItem item = listaDeseadosRepository.findByUsuarioIdAndJuegoId(usuarioId, juegoId)
                .orElseThrow(() -> new IllegalArgumentException("El juego no esta en la lista de deseados"));
        listaDeseadosRepository.delete(item);
    }
    @Transactional
    public void vaciarPorUsuario(Long usuarioId) {
        validarUsuario(usuarioId);
        listaDeseadosRepository.deleteByUsuarioId(usuarioId);
    }
    private ListaDeseadosResponseDTO mapToDTO(ListaDeseadosItem item) {
        return new ListaDeseadosResponseDTO(
                item.getId(),
                item.getUsuarioId(),
                item.getJuegoId(),
                item.getAgregadoEn(),
                obtenerJuegoSeguro(item.getJuegoId())
        );
    }
    private void validarUsuario(Long usuarioId) {
        try {
            usuarioClient.obtenerUsuario(usuarioId);
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException("No existe un usuario con id " + usuarioId);
        }
    }
    private void validarJuego(Long juegoId) {
        try {
            juegoClient.obtenerJuego(juegoId);
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("No existe un juego con id " + juegoId);
        }
    }
    private JuegoDTO obtenerJuegoSeguro(Long juegoId) {
        try {
            return juegoClient.obtenerJuego(juegoId);
        } catch (FeignException e) {
            return null;
        }
    }
}
