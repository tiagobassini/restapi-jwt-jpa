package org.apirest.example.exception;

public class RegraNegocioException extends  RuntimeException{


    public RegraNegocioException(String msg){
        super(msg);
    }
}
