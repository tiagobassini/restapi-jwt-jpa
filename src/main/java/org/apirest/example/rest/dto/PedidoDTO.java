package org.apirest.example.rest.dto;

import lombok.*;
import org.apirest.example.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO
{
    private Integer cliente;
    private BigDecimal total;
    private String status;
    private List<ItemPedidoDTO> itens;
}
