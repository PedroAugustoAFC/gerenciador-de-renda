package com.nassau.gerenciador_de_renda.exceptions;

public class EmailException extends RuntimeException{
    public EmailException(){
        super("Email já cadastrado");
    }
    public EmailException(String message){
        super(message);
    }
}
