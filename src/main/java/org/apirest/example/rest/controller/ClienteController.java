package org.apirest.example.rest.controller;


import io.swagger.annotations.*;
import org.apirest.example.domain.entity.Cliente;
import org.apirest.example.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;

@RestController
@RequestMapping("/api/clientes")
@Api("API Clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @GetMapping("/{id}")
    @ApiOperation("Obter Detalhes de Um Cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado."),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public Cliente findClienteById(@PathVariable  @ApiParam("Id do Cliente") Integer id){

        return  repository.findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException( HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar Novo Cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente Salvo com Sucesso."),
            @ApiResponse(code = 400, message = "Erro de Validação.")
    })
    public Cliente save(@RequestBody @Valid Cliente cliente){

        return repository.save(cliente);

    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){

        repository.findById(id)
                .map(cliente -> {
                    repository.delete(cliente);
                    return cliente;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente){

        repository
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    repository.save(cliente);
                    return cliente;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @GetMapping
    public List<Cliente> find( Cliente filtro){

        ExampleMatcher matcher = ExampleMatcher
                                        .matching()
                                        .withIgnoreCase()
                                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return repository.findAll(example);


    }
}
