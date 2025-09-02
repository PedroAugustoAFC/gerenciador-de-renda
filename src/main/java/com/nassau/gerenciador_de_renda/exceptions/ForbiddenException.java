package com.nassau.gerenciador_de_renda.exceptions;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(){
        super("Acesso negado");
    }
    public ForbiddenException(String message){
        super(message);
    }
}
