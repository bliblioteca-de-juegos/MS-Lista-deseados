package com.biblioteca.listadeseados.repository;

import com.biblioteca.listadeseados.model.ListaDeseadosItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListaDeseadosRepository extends JpaRepository<ListaDeseadosItem, Long> {

    List<ListaDeseadosItem> findByUsuarioId(Long usuarioId);

    Optional<ListaDeseadosItem> findByUsuarioIdAndJuegoId(Long usuarioId, Long juegoId);

    boolean existsByUsuarioIdAndJuegoId(Long usuarioId, Long juegoId);

    void deleteByUsuarioId(Long usuarioId);
}
