package org.apirest.example;

import org.apirest.example.domain.entity.Cliente;
import org.apirest.example.domain.entity.Pedido;
import org.apirest.example.domain.repository.ClienteRepository;
import org.apirest.example.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(
            @Autowired ClienteRepository clienteRepository,
            @Autowired PedidoRepository pedidoRepository){
        return args -> {
            System.out.println("--------------------------------------------------------Salvando clientes");
            Cliente fulano = new Cliente("Fulano");
            clienteRepository.save(fulano);

            System.out.println("--------------------------------------------------------Cliente: ");
            System.out.println(fulano);
            System.out.println("--------------------------------------------------------");


            Pedido pedido = new Pedido();
            pedido.setCliente(fulano);
            pedido.setDataPedido(LocalDate.now());
            pedido.setTotal(BigDecimal.valueOf(100));

            System.out.println("---------------------------------------------------------Salvando Pedido");
            pedidoRepository.save(pedido);

            System.out.println("--------------------------------------------------------Pedido: ");
            System.out.println(pedido);
            System.out.println("--------------------------------------------------------");


            System.out.println("-------------------------------------------------------- buscando por fatch: ");
            fulano = clienteRepository.findClienteFetchPedidos(fulano.getId());
            System.out.println(fulano.getPedidos());


        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
