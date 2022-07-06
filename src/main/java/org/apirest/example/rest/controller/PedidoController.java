package org.apirest.example.rest.controller;


import org.apirest.example.domain.entity.ItemPedido;
import org.apirest.example.domain.entity.Pedido;
import org.apirest.example.domain.enums.StatusPedido;
import org.apirest.example.domain.service.PedidoService;
import org.apirest.example.rest.dto.AtualizacaoStatusPedidoDTO;
import org.apirest.example.rest.dto.InformacoesItemPedidoDTO;
import org.apirest.example.rest.dto.InformacoesPedidoDTO;
import org.apirest.example.rest.dto.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save (@RequestBody @Valid PedidoDTO pedidoDTO){

        Pedido pedido = this.service.save(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacoesPedidoDTO findById(@PathVariable Integer id){

        return service.obterPedidoCompleto(id)
                .map(pedido -> converterPedido(pedido))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));

    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){

        String novosStatus = dto.getNovoStatus();
        service.atualizarSatusPedido(id, StatusPedido.valueOf(novosStatus));
    }


    private InformacoesPedidoDTO converterPedido(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .itens( converterListaItens(pedido.getItens()))
                .build();
    }

    private List<InformacoesItemPedidoDTO> converterListaItens(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map( item ->
            InformacoesItemPedidoDTO
                    .builder()
                    .descricaoProduto(item.getProduto().getDescricao())
                    .precoUnitario(item.getProduto().getPreco())
                    .quantidade(item.getQuantidade())
                    .build()
        ).collect(Collectors.toList());
    }



}
