package org.apirest.example.domain.service.impl;


import lombok.RequiredArgsConstructor;
import org.apirest.example.domain.entity.Cliente;
import org.apirest.example.domain.entity.ItemPedido;
import org.apirest.example.domain.entity.Pedido;
import org.apirest.example.domain.entity.Produto;
import org.apirest.example.domain.enums.StatusPedido;
import org.apirest.example.domain.repository.ClienteRepository;
import org.apirest.example.domain.repository.ItemPedidoRepository;
import org.apirest.example.domain.repository.PedidoRepository;
import org.apirest.example.domain.repository.ProdutoRepository;
import org.apirest.example.domain.service.PedidoService;
import org.apirest.example.exception.PedidoNaoEncontradoException;
import org.apirest.example.exception.RegraNegocioException;
import org.apirest.example.rest.dto.ItemPedidoDTO;
import org.apirest.example.rest.dto.PedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido save(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clienteRepository
                                .findById(idCliente)
                                .orElseThrow( () -> new RegraNegocioException("Codigo Cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItens(pedido, pedidoDTO.getItens());

        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itensPedido);


        pedido.setItens(itensPedido);

        return pedido;
    }



    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizarSatusPedido(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow( () -> new PedidoNaoEncontradoException());
    }


    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){

        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possivel realizar um pedido sem itens.");
        }

        return itens
                .stream()
                .map( dto -> {

                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoRepository.findById((idProduto))
                            .orElseThrow(() -> new RegraNegocioException("Condigo e Produto inválido: "+idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    itemPedido.setQuantidade(dto.getQuantidade());

                    return itemPedido;
                }).collect(Collectors.toList());

    }

}
