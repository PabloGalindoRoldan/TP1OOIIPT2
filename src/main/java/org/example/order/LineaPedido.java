package org.example.order;

import org.example.menu.MenuItem;
import org.example.menu.Bebida;
import org.example.menu.Plato;

/**
 * Clase que representa una línea de un pedido (item + cantidad).
 */
public class LineaPedido {
    private MenuItem item;
    private int cantidad;

    public LineaPedido(MenuItem item, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        this.item = item;
        this.cantidad = cantidad;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return item.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return item.getNombre() + " x" + cantidad + " = $" + getSubtotal();
    }
}

