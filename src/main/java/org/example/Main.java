package org.example;

import org.example.restaurant.Restaurante;
import org.example.restaurant.Mesa;
import org.example.order.Pedido;
import org.example.menu.Cerveza;
import org.example.menu.Jugo;
import org.example.menu.Asado;
import org.example.menu.Pizza;
import org.example.menu.Pasta;
import org.example.payment.TarjetaCredito;
import org.example.payment.TipoTarjeta;

/**
 * Ejemplo de uso del sistema de cálculo de costos del restaurante.
 */
public class Main {
    public static void main(String[] args) {
        // Crear el restaurante
        Restaurante restaurante = new Restaurante("La Pizzería del Barrio");
        
        // Agregar bebidas al menú
        restaurante.agregarBebida(new Cerveza("Cerveza", 5.00));
        restaurante.agregarBebida(new Jugo("Jugo de Naranja", 3.00));
        restaurante.agregarBebida(new Cerveza("Vino Tinto", 8.00));
        
        // Agregar platos principales al menú
        restaurante.agregarPlato(new Asado("Asado", 25.00));
        restaurante.agregarPlato(new Pizza("Pizza Margherita", 15.00));
        restaurante.agregarPlato(new Pasta("Pasta Carbonara", 18.00));
        
        System.out.println("=== SIMULACIÓN DE RESTAURANTE ===\n");
        
        // Escenario 1: Cliente con tarjeta VISA
        System.out.println("--- MESA 1: Pago con Tarjeta VISA (3% descuento en bebidas) ---");
        Mesa mesa1 = restaurante.getMesa(1);
        mesa1.agregarComensal("Juan Pérez");
        mesa1.agregarComensal("María García");
        
        Pedido pedido1 = mesa1.crearPedido();
        pedido1.agregarLinea(new Cerveza("Cerveza", 5.00), 2);
        pedido1.agregarLinea(new Asado("Asado", 25.00), 2);
        
        TarjetaCredito visa = new TarjetaCredito("1234-5678-9012-3456", "Juan Pérez", TipoTarjeta.VISA);
        pedido1.confirmar(visa, 3);
        
        System.out.println("Comensales: " + mesa1.getComensales());
        System.out.println("Subtotal bebidas: $" + String.format("%.2f", pedido1.getSubtotalBebidas()));
        System.out.println("Subtotal platos: $" + String.format("%.2f", pedido1.getSubtotalPlatos()));
        System.out.println("Subtotal: $" + String.format("%.2f", pedido1.getSubtotal()));
        System.out.println("Descuento (3% en bebidas): -$" + String.format("%.2f", pedido1.getDescuento()));
        System.out.println("Subtotal con descuento: $" + String.format("%.2f", pedido1.getSubtotalConDescuento()));
        System.out.println("Propina (3%): $" + String.format("%.2f", pedido1.getPropina()));
        System.out.println("TOTAL A PAGAR: $" + String.format("%.2f", pedido1.getTotal()));
        System.out.println();
        
        // Escenario 2: Cliente con tarjeta MASTERCARD
        System.out.println("--- MESA 2: Pago con Tarjeta MASTERCARD (2% descuento en platos) ---");
        Mesa mesa2 = restaurante.getMesa(2);
        mesa2.agregarComensal("Carlos López");
        
        Pedido pedido2 = mesa2.crearPedido();
        pedido2.agregarLinea(new Jugo("Jugo de Naranja", 3.00), 3);
        pedido2.agregarLinea(new Pizza("Pizza Margherita", 15.00), 1);
        
        TarjetaCredito mastercard = new TarjetaCredito("9876-5432-1098-7654", "Carlos López", TipoTarjeta.MASTERCARD);
        pedido2.confirmar(mastercard, 2);
        
        System.out.println("Comensales: " + mesa2.getComensales());
        System.out.println("Subtotal bebidas: $" + String.format("%.2f", pedido2.getSubtotalBebidas()));
        System.out.println("Subtotal platos: $" + String.format("%.2f", pedido2.getSubtotalPlatos()));
        System.out.println("Subtotal: $" + String.format("%.2f", pedido2.getSubtotal()));
        System.out.println("Descuento (2% en platos): -$" + String.format("%.2f", pedido2.getDescuento()));
        System.out.println("Subtotal con descuento: $" + String.format("%.2f", pedido2.getSubtotalConDescuento()));
        System.out.println("Propina (2%): $" + String.format("%.2f", pedido2.getPropina()));
        System.out.println("TOTAL A PAGAR: $" + String.format("%.2f", pedido2.getTotal()));
        System.out.println();
        
        // Escenario 3: Cliente con tarjeta COMARCA PLUS
        System.out.println("--- MESA 3: Pago con Tarjeta COMARCA PLUS (2% descuento en total) ---");
        Mesa mesa3 = restaurante.getMesa(3);
        mesa3.agregarComensal("Laura Martínez");
        mesa3.agregarComensal("Roberto Sánchez");
        mesa3.agregarComensal("Ana Torres");
        mesa3.agregarComensal("Luis Fernández");
        
        Pedido pedido3 = mesa3.crearPedido();
        pedido3.agregarLinea(new Cerveza("Cerveza", 5.00), 4);
        pedido3.agregarLinea(new Pasta("Pasta Carbonara", 18.00), 2);
        pedido3.agregarLinea(new Pizza("Pizza Margherita", 15.00), 2);
        
        TarjetaCredito comarcaPlus = new TarjetaCredito("1111-2222-3333-4444", "Laura Martínez", TipoTarjeta.COMARCA_PLUS);
        pedido3.confirmar(comarcaPlus, 5);
        
        System.out.println("Comensales: " + mesa3.getComensales());
        System.out.println("Subtotal bebidas: $" + String.format("%.2f", pedido3.getSubtotalBebidas()));
        System.out.println("Subtotal platos: $" + String.format("%.2f", pedido3.getSubtotalPlatos()));
        System.out.println("Subtotal: $" + String.format("%.2f", pedido3.getSubtotal()));
        System.out.println("Descuento (2% en total): -$" + String.format("%.2f", pedido3.getDescuento()));
        System.out.println("Subtotal con descuento: $" + String.format("%.2f", pedido3.getSubtotalConDescuento()));
        System.out.println("Propina (5%): $" + String.format("%.2f", pedido3.getPropina()));
        System.out.println("TOTAL A PAGAR: $" + String.format("%.2f", pedido3.getTotal()));
        System.out.println();
        
        // Escenario 4: Cliente con tarjeta VIEDMA (sin descuento)
        System.out.println("--- MESA 4: Pago con Tarjeta VIEDMA (sin descuento) ---");
        Mesa mesa4 = restaurante.getMesa(4);
        mesa4.agregarComensal("Pedro Ramos");
        
        Pedido pedido4 = mesa4.crearPedido();
        pedido4.agregarLinea(new Jugo("Jugo de Naranja", 3.00), 2);
        pedido4.agregarLinea(new Asado("Asado", 25.00), 1);
        
        TarjetaCredito viedma = new TarjetaCredito("5555-6666-7777-8888", "Pedro Ramos", TipoTarjeta.OTRA);
        pedido4.confirmar(viedma, 3);
        
        System.out.println("Comensales: " + mesa4.getComensales());
        System.out.println("Subtotal bebidas: $" + String.format("%.2f", pedido4.getSubtotalBebidas()));
        System.out.println("Subtotal platos: $" + String.format("%.2f", pedido4.getSubtotalPlatos()));
        System.out.println("Subtotal: $" + String.format("%.2f", pedido4.getSubtotal()));
        System.out.println("Descuento (ninguno): $0.00");
        System.out.println("Subtotal con descuento: $" + String.format("%.2f", pedido4.getSubtotalConDescuento()));
        System.out.println("Propina (3%): $" + String.format("%.2f", pedido4.getPropina()));
        System.out.println("TOTAL A PAGAR: $" + String.format("%.2f", pedido4.getTotal()));
        System.out.println();
        
        // Resumen general
        System.out.println("=== RESUMEN DE MESAS ===");
        double totalRecaudado = pedido1.getTotal() + pedido2.getTotal() + pedido3.getTotal() + pedido4.getTotal();
        System.out.println("Total recaudado de mesa 1: $" + String.format("%.2f", pedido1.getTotal()));
        System.out.println("Total recaudado de mesa 2: $" + String.format("%.2f", pedido2.getTotal()));
        System.out.println("Total recaudado de mesa 3: $" + String.format("%.2f", pedido3.getTotal()));
        System.out.println("Total recaudado de mesa 4: $" + String.format("%.2f", pedido4.getTotal()));
        System.out.println("TOTAL RECAUDADO: $" + String.format("%.2f", totalRecaudado));
    }
}

