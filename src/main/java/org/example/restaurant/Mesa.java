package org.example.restaurant;

import java.util.ArrayList;
import java.util.List;

import org.example.order.Pedido;
import org.example.registro.RegistroDeCosto;

/**
 * Clase que representa una mesa del restaurante.
 */
public class Mesa {
    private int numero;
    private int capacidad;
    private List<String> comensales;
    private Pedido pedidoActual;


    public Mesa(int numero, int capacidad) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El número de mesa debe ser mayor a 0");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        this.numero = numero;
        this.capacidad = capacidad;
        this.comensales = new ArrayList<>();
        this.pedidoActual = null;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public List<String> getComensales() {
        return new ArrayList<>(comensales);
    }

    /**
     * Agrega un comensal a la mesa si hay espacio.
     */
    public void agregarComensal(String nombre) {
        if (comensales.size() >= capacidad) {
            throw new IllegalStateException("La mesa está llena");
        }
        comensales.add(nombre);
    }

    /**
     * Obtiene o crea un nuevo pedido para la mesa.
     */
    public Pedido crearPedido() {
        if (pedidoActual != null && pedidoActual.isConfirmado()) {
            throw new IllegalStateException("Debe crear una nueva mesa para un nuevo pedido");
        }
        this.pedidoActual = new Pedido();
        return pedidoActual;
    }

    public Pedido getPedidoActual() {
        return pedidoActual;
    }

    public boolean tienePedidoConfirmado() {
        return pedidoActual != null && pedidoActual.isConfirmado();
    }

    /**
     * Limpia la mesa después de que se fue el grupo.
     */
    public void limpiar() {
        comensales.clear();
        pedidoActual = null;
    }
}

