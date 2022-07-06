package org.apirest.example.domain.service;


import org.apirest.example.domain.entity.Pedido;
import org.apirest.example.domain.enums.StatusPedido;
import org.apirest.example.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido save(PedidoDTO pedidoDTO);

    Optional<Pedido> obterPedidoCompleto(Integer pedido);

    void atualizarSatusPedido(Integer id, StatusPedido statusPedido);
}
