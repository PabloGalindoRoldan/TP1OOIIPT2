package org.example.menu;

/**
 * Clase abstracta que representa un elemento del menú.
 */
public abstract class MenuItem {
    private String nombre;
    private double precio;

    public MenuItem(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}

