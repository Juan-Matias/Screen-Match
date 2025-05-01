package com.alura.screenmatch.excpecion;

public class ErrorEnConversionException extends RuntimeException {
    private String mensaje;

    public ErrorEnConversionException(String mensaje) {
     this.mensaje = mensaje;

    }

    public String getMensaje() {
        return this.mensaje;
    }
}
