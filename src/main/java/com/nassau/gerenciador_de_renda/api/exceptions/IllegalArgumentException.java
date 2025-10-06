package com.nassau.gerenciador_de_renda.api.exceptions;

public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(){
        super("Argumento ilegal");
    }
    public IllegalArgumentException(String message){
        super(message);
    }
}
