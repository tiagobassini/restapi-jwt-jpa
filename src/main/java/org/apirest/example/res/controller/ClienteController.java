package org.apirest.example.res.controller;


import org.apirest.example.domain.entity.Cliente;
import org.apirest.example.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity findClienteById(@PathVariable Integer id){

        Optional<Cliente> cliente = repository.findById(id);

        if(cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente){

        repository.save(cliente);

        if(cliente.getId()>0)
            return ResponseEntity.ok(cliente);
        else
            return ResponseEntity.badRequest().build();
    }


    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id){

        Optional<Cliente> cliente = repository.findById(id);

        if(cliente.isPresent()){
            repository.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Cliente cliente){

        Optional<Cliente> clienteOptional = repository.findById(id);

        return repository
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    repository.save(cliente);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet( ()-> ResponseEntity.notFound().build() );

    }

    @GetMapping
    @ResponseBody
    public ResponseEntity find( Cliente filtro){

        ExampleMatcher matcher = ExampleMatcher
                                        .matching()
                                        .withIgnoreCase()
                                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        List<Cliente> clientes = repository.findAll(example);

        return ResponseEntity.ok(clientes);

    }
}
