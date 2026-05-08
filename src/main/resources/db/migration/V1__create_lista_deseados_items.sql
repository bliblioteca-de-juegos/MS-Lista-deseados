CREATE TABLE lista_deseados_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    juego_id BIGINT NOT NULL,
    agregado_en DATETIME NOT NULL,
    CONSTRAINT uk_lista_deseados_usuario_juego UNIQUE (usuario_id, juego_id)
);
