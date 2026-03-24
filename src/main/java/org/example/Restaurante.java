package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa el restaurante con sus mesas y menú.
 */
public class Restaurante {
    private String nombre;
    private List<Mesa> mesas;
    private List<Bebida> bebidas;
    private List<Plato> platos;

    public Restaurante(String nombre) {
        this.nombre = nombre;
        this.mesas = new ArrayList<>();
        this.bebidas = new ArrayList<>();
        this.platos = new ArrayList<>();
        
        // Crear 10 mesas con diferentes capacidades
        crearMesasDefault();
    }

    private void crearMesasDefault() {
        int[] capacidades = {2, 2, 4, 4, 4, 6, 6, 8, 8, 10};
        for (int i = 0; i < 10; i++) {
            mesas.add(new Mesa(i + 1, capacidades[i]));
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Mesa> getMesas() {
        return new ArrayList<>(mesas);
    }

    public Mesa getMesa(int numero) {
        return mesas.stream()
                .filter(m -> m.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada"));
    }

    public void agregarBebida(Bebida bebida) {
        bebidas.add(bebida);
    }

    public void agregarPlato(Plato plato) {
        platos.add(plato);
    }

    public List<Bebida> getBebidas() {
        return new ArrayList<>(bebidas);
    }

    public List<Plato> getPlatos() {
        return new ArrayList<>(platos);
    }

    /**
     * Obtiene el menú completo.
     */
    public Map<String, List<? extends MenuItem>> getMenu() {
        Map<String, List<? extends MenuItem>> menu = new HashMap<>();
        menu.put("Bebidas", getBebidas());
        menu.put("Platos", getPlatos());
        return menu;
    }
}

