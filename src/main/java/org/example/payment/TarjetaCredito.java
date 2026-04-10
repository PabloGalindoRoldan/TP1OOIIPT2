package org.example.payment;

/**
 * Clase que representa una tarjeta de crédito.
 */
public class TarjetaCredito {
    private String numero;
    private String titular;
    private TipoTarjeta tipo;

    public TarjetaCredito(String numero, String titular, TipoTarjeta tipo) {
        this.numero = numero;
        this.titular = titular;
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public TipoTarjeta getTipo() {
        return tipo;
    }
}

