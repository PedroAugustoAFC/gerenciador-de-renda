package com.nassau.gerenciador_de_renda.api.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("Recurso não encontrado");
    }
    public ResourceNotFoundException(String message){
        super(message);
    }
}
