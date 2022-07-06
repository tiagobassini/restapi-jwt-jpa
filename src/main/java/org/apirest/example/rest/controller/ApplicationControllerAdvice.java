package org.apirest.example.rest.controller;


import org.apirest.example.exception.PedidoNaoEncontradoException;
import org.apirest.example.exception.RegraNegocioException;
import org.apirest.example.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerRegraNegocioException(RegraNegocioException ex){
        String mensagemError = ex.getMessage();
        return new ApiErrors(mensagemError);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex){
        String mensagemError = ex.getMessage();
        return new ApiErrors(mensagemError);
    }
}
