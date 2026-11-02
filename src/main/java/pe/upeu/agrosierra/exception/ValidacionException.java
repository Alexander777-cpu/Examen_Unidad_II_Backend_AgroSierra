package pe.upeu.agrosierra.exception;

import lombok.Getter;

@Getter
public class ValidacionException extends RuntimeException {

    private final String campo;

    public ValidacionException(String campo, String mensaje) {
        super(mensaje);
        this.campo = campo;
    }
}