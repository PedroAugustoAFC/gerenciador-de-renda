package com.nassau.gerenciador_de_renda.exceptions;

public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(){
        super("Argumento ilegal");
    }
    public IllegalArgumentException(String message){
        super(message);
    }
}
