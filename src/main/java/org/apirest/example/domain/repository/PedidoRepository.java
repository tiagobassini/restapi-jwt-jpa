package org.apirest.example.domain.repository;

import org.apirest.example.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository  extends JpaRepository<Pedido, Integer> {
}
