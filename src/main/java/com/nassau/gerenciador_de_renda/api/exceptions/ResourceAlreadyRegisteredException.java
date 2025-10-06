package com.nassau.gerenciador_de_renda.api.exceptions;

public class ResourceAlreadyRegisteredException extends RuntimeException{
    public ResourceAlreadyRegisteredException(){
        super("Informação já cadastrada");
    }
    public ResourceAlreadyRegisteredException(String message){
        super(message);
    }
}
