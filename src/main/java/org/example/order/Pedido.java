package org.example.order;

import java.util.ArrayList;
import java.util.List;

import org.example.menu.MenuItem;
import org.example.menu.Bebida;
import org.example.menu.Plato;
import org.example.payment.TarjetaCredito;
import org.example.registro.ArchivoRegistroCosto;

/**
 * Clase que representa un pedido realizado en una mesa.
 */
public class Pedido {
    private List<LineaPedido> lineas;
    private boolean confirmado;
    private TarjetaCredito tarjetaCredito;
    private double porcentajePropina;
    private ArchivoRegistroCosto registro;

    public Pedido() {
        this.lineas = new ArrayList<>();
        this.confirmado = false;
        this.tarjetaCredito = null;
        this.porcentajePropina = 0;
        this.registro = new ArchivoRegistroCosto("registro_costos.txt");
    }

    /**
     * Agrega una línea al pedido antes de confirmarlo.
     */
    public void agregarLinea(MenuItem item, int cantidad) {
        if (confirmado) {
            throw new IllegalStateException("No se puede modificar un pedido confirmado");
        }
        lineas.add(new LineaPedido(item, cantidad));
    }

    /**
     * Confirma el pedido. Una vez confirmado, no puede cambiarse.
     */
    public void confirmar(TarjetaCredito tarjeta, double porcentajePropina) {
        if (confirmado) {
            throw new IllegalStateException("El pedido ya está confirmado");
        }
        if (lineas.isEmpty()) {
            throw new IllegalStateException("El pedido no puede estar vacío");
        }
        if (porcentajePropina != 2 && porcentajePropina != 3 && porcentajePropina != 5) {
            throw new IllegalArgumentException("La propina debe ser 2%, 3% o 5%");
        }
        this.tarjetaCredito = tarjeta;
        this.porcentajePropina = porcentajePropina;
        this.confirmado = true;

        //Registrar el costo:
        this.registro.registrarCosto(getTotal());
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public List<LineaPedido> getLineas() {
        return new ArrayList<>(lineas);
    }

    public TarjetaCredito getTarjetaCredito() {
        return tarjetaCredito;
    }

    public double getPorcentajePropina() {
        return porcentajePropina;
    }

    /**
     * Calcula el subtotal sin descuentos ni propina.
     */
    public double getSubtotal() {
        return lineas.stream().mapToDouble(LineaPedido::getSubtotal).sum();
    }

    /**
     * Calcula el subtotal de bebidas.
     */
    public double getSubtotalBebidas() {
        return lineas.stream()
                .filter(linea -> linea.getItem() instanceof Bebida)
                .mapToDouble(LineaPedido::getSubtotal)
                .sum();
    }

    /**
     * Calcula el subtotal de platos principales.
     */
    public double getSubtotalPlatos() {
        return lineas.stream()
                .filter(linea -> linea.getItem() instanceof Plato)
                .mapToDouble(LineaPedido::getSubtotal)
                .sum();
    }

    /**
     * Calcula el descuento según el tipo de tarjeta.
     */
    public double getDescuento() {
        if (!confirmado || tarjetaCredito == null) {
            return 0;
        }

        switch (tarjetaCredito.getTipo()) {
            case VISA:
                // 3% descuento en bebidas
                return getSubtotalBebidas() * 0.03;
            case MASTERCARD:
                // 2% descuento en platos principales
                return getSubtotalPlatos() * 0.02;
            case COMARCA_PLUS:
                // 2% descuento en total
                return getSubtotal() * 0.02;
            default:
                // Otras tarjetas no tienen descuento
                return 0;
        }
    }

    /**
     * Calcula el subtotal después del descuento.
     */
    public double getSubtotalConDescuento() {
        return getSubtotal() - getDescuento();
    }

    /**
     * Calcula el monto de propina.
     */
    public double getPropina() {
        if (!confirmado) {
            return 0;
        }
        return getSubtotalConDescuento() * (porcentajePropina / 100.0);
    }

    /**
     * Calcula el total a pagar (subtotal - descuento + propina).
     */
    public double getTotal() {
        return getSubtotalConDescuento() + getPropina();
    }
}

