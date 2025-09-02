package com.nassau.gerenciador_de_renda.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(){
        super("Token inválido ou não fornecido");
    }
    public UnauthorizedException(String message){
        super(message);
    }
}
