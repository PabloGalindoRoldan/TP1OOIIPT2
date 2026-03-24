package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests exhaustivos para el sistema de cálculo de costos del restaurante.
 */
@DisplayName("Tests del Sistema de Restaurante")
class RestauranteTest {
    private Restaurante restaurante;
    private Mesa mesa1;
    private Bebida cerveza;
    private Bebida jugo;
    private Plato asado;
    private Plato pizza;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Mi Restaurante");
        mesa1 = restaurante.getMesa(1);

        // Crear menú
        cerveza = new Bebida("Cerveza", 5.00);
        jugo = new Bebida("Jugo de Naranja", 3.00);
        asado = new Plato("Asado", 25.00);
        pizza = new Plato("Pizza", 15.00);

        restaurante.agregarBebida(cerveza);
        restaurante.agregarBebida(jugo);
        restaurante.agregarPlato(asado);
        restaurante.agregarPlato(pizza);
    }

    // ==================== Tests de Mesa ====================

    @Test
    @DisplayName("Crear una mesa con número y capacidad válidos")
    void testCrearMesaValida() {
        Mesa mesa = new Mesa(5, 4);
        assertEquals(5, mesa.getNumero());
        assertEquals(4, mesa.getCapacidad());
    }

    @Test
    @DisplayName("Lanzar excepción cuando mesa tiene número inválido")
    void testCrearMesaNumeroInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Mesa(-1, 4));
        assertThrows(IllegalArgumentException.class, () -> new Mesa(0, 4));
    }

    @Test
    @DisplayName("Lanzar excepción cuando mesa tiene capacidad inválida")
    void testCrearMesaCapacidadInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Mesa(1, -1));
        assertThrows(IllegalArgumentException.class, () -> new Mesa(1, 0));
    }

    @Test
    @DisplayName("Agregar comensales a una mesa")
    void testAgregarComensalesAMesa() {
        mesa1.agregarComensal("Juan");
        mesa1.agregarComensal("María");
        assertEquals(2, mesa1.getComensales().size());
        assertTrue(mesa1.getComensales().contains("Juan"));
        assertTrue(mesa1.getComensales().contains("María"));
    }

    @Test
    @DisplayName("Lanzar excepción al exceder capacidad de mesa")
    void testExcederCapacidadMesa() {
        assertThrows(IllegalStateException.class, () -> {
            mesa1.agregarComensal("Juan");
            mesa1.agregarComensal("María");
            mesa1.agregarComensal("Pedro"); // Mesa 1 tiene capacidad 2
        });
    }

    @Test
    @DisplayName("Obtener las 10 mesas del restaurante")
    void testObtenerDiezMesas() {
        assertEquals(10, restaurante.getMesas().size());
    }

    @Test
    @DisplayName("Obtener mesa por número")
    void testObtenerMesaPorNumero() {
        Mesa mesa = restaurante.getMesa(3);
        assertEquals(3, mesa.getNumero());
    }

    @Test
    @DisplayName("Lanzar excepción al obtener mesa inexistente")
    void testObtenerMesaInexistente() {
        assertThrows(IllegalArgumentException.class, () -> restaurante.getMesa(999));
    }

    // ==================== Tests de Pedido Básico ====================

    @Test
    @DisplayName("Crear un pedido vacío")
    void testCrearPedidoVacio() {
        Pedido pedido = mesa1.crearPedido();
        assertNotNull(pedido);
        assertFalse(pedido.isConfirmado());
        assertEquals(0, pedido.getSubtotal());
    }

    @Test
    @DisplayName("Agregar items al pedido")
    void testAgregarItemsAlPedido() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 2);
        pedido.agregarLinea(asado, 1);
        assertEquals(2, pedido.getLineas().size());
    }

    @Test
    @DisplayName("Calcular subtotal correctamente")
    void testCalcularSubtotal() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 2);     // 2 * 5 = 10
        pedido.agregarLinea(asado, 1);       // 1 * 25 = 25
        assertEquals(35.0, pedido.getSubtotal());
    }

    @Test
    @DisplayName("Lanzar excepción al agregar cantidad inválida")
    void testAgregarCantidadInvalida() {
        Pedido pedido = mesa1.crearPedido();
        assertThrows(IllegalArgumentException.class, () -> pedido.agregarLinea(cerveza, 0));
        assertThrows(IllegalArgumentException.class, () -> pedido.agregarLinea(cerveza, -1));
    }

    // ==================== Tests de Confirmación ====================

    @Test
    @DisplayName("Confirmar pedido válido")
    void testConfirmarPedido() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 1);
        TarjetaCredito tarjeta = new TarjetaCredito("1234", "Juan", TipoTarjeta.VISA);
        pedido.confirmar(tarjeta, 3);
        assertTrue(pedido.isConfirmado());
    }

    @Test
    @DisplayName("Lanzar excepción al confirmar pedido vacío")
    void testConfirmarPedidoVacio() {
        Pedido pedido = mesa1.crearPedido();
        TarjetaCredito tarjeta = new TarjetaCredito("1234", "Juan", TipoTarjeta.VISA);
        assertThrows(IllegalStateException.class, () -> pedido.confirmar(tarjeta, 3));
    }

    @Test
    @DisplayName("Lanzar excepción al confirmar con propina inválida")
    void testConfirmarConPropinaInvalida() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 1);
        TarjetaCredito tarjeta = new TarjetaCredito("1234", "Juan", TipoTarjeta.VISA);
        assertThrows(IllegalArgumentException.class, () -> pedido.confirmar(tarjeta, 4)); // 4% no permitido
        assertThrows(IllegalArgumentException.class, () -> pedido.confirmar(tarjeta, 1));  // 1% no permitido
    }

    @Test
    @DisplayName("Lanzar excepción al agregar item a pedido confirmado")
    void testAgregarItemAPedidoConfirmado() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 1);
        TarjetaCredito tarjeta = new TarjetaCredito("1234", "Juan", TipoTarjeta.VISA);
        pedido.confirmar(tarjeta, 3);
        assertThrows(IllegalStateException.class, () -> pedido.agregarLinea(asado, 1));
    }

    @Test
    @DisplayName("Lanzar excepción al confirmar dos veces")
    void testConfirmarDosVeces() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 1);
        TarjetaCredito tarjeta1 = new TarjetaCredito("1234", "Juan", TipoTarjeta.VISA);
        pedido.confirmar(tarjeta1, 2);
        TarjetaCredito tarjeta2 = new TarjetaCredito("5678", "María", TipoTarjeta.MASTERCARD);
        assertThrows(IllegalStateException.class, () -> pedido.confirmar(tarjeta2, 3));
    }

    // ==================== Tests Descuento VISA ====================

    @Test
    @DisplayName("1. Cálculo de costo con tarjeta VISA (3% descuento en bebidas)")
    void testDescuentoVisa() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 2);     // 2 * 5 = 10 (bebida)
        pedido.agregarLinea(asado, 1);       // 1 * 25 = 25 (plato)
        // Subtotal: 35
        // Descuento (3% en bebidas): 10 * 0.03 = 0.30
        // Subtotal con descuento: 34.70

        TarjetaCredito visaCard = new TarjetaCredito("1234567890123456", "Juan Pérez", TipoTarjeta.VISA);
        pedido.confirmar(visaCard, 2); // 2% propina

        assertEquals(10.0, pedido.getSubtotalBebidas());
        assertEquals(25.0, pedido.getSubtotalPlatos());
        assertEquals(35.0, pedido.getSubtotal());
        assertEquals(0.30, pedido.getDescuento(), 0.01);
        assertEquals(34.70, pedido.getSubtotalConDescuento(), 0.01);
        assertEquals(0.694, pedido.getPropina(), 0.01); // 2% de 34.70
        assertEquals(35.394, pedido.getTotal(), 0.01);  // 34.70 + 0.694
    }

    @Test
    @DisplayName("VISA descuento solo en bebidas, no en platos")
    void testVisaDescuentoSoloBebidas() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(jugo, 3);        // 3 * 3 = 9 (bebida)
        pedido.agregarLinea(pizza, 2);       // 2 * 15 = 30 (platos)
        // Subtotal: 39
        // Descuento (3% en bebidas): 9 * 0.03 = 0.27

        TarjetaCredito visaCard = new TarjetaCredito("1111", "Cliente", TipoTarjeta.VISA);
        pedido.confirmar(visaCard, 3);

        assertEquals(9.0, pedido.getSubtotalBebidas());
        assertEquals(30.0, pedido.getSubtotalPlatos());
        assertEquals(39.0, pedido.getSubtotal());
        assertEquals(0.27, pedido.getDescuento(), 0.01);
        assertEquals(38.73, pedido.getSubtotalConDescuento(), 0.01);
    }

    @Test
    @DisplayName("VISA sin bebidas: descuento 0")
    void testVisaSinBebidas() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(asado, 1);
        pedido.agregarLinea(pizza, 1);

        TarjetaCredito visaCard = new TarjetaCredito("1111", "Cliente", TipoTarjeta.VISA);
        pedido.confirmar(visaCard, 2);

        assertEquals(0.0, pedido.getSubtotalBebidas());
        assertEquals(40.0, pedido.getSubtotalPlatos());
        assertEquals(40.0, pedido.getSubtotal());
        assertEquals(0.0, pedido.getDescuento());
        assertEquals(40.0, pedido.getSubtotalConDescuento());
    }

    // ==================== Tests Descuento MASTERCARD ====================

    @Test
    @DisplayName("2. Cálculo de costo con tarjeta MASTERCARD (2% descuento en platos)")
    void testDescuentoMastercard() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 2);     // 2 * 5 = 10 (bebida)
        pedido.agregarLinea(asado, 1);       // 1 * 25 = 25 (plato)
        // Subtotal: 35
        // Descuento (2% en platos): 25 * 0.02 = 0.50
        // Subtotal con descuento: 34.50

        TarjetaCredito mastercardCard = new TarjetaCredito("9876543210123456", "María García", TipoTarjeta.MASTERCARD);
        pedido.confirmar(mastercardCard, 3); // 3% propina

        assertEquals(10.0, pedido.getSubtotalBebidas());
        assertEquals(25.0, pedido.getSubtotalPlatos());
        assertEquals(35.0, pedido.getSubtotal());
        assertEquals(0.50, pedido.getDescuento(), 0.01);
        assertEquals(34.50, pedido.getSubtotalConDescuento(), 0.01);
        assertEquals(1.035, pedido.getPropina(), 0.01); // 3% de 34.50
        assertEquals(35.535, pedido.getTotal(), 0.01);  // 34.50 + 1.035
    }

    @Test
    @DisplayName("MASTERCARD descuento solo en platos, no en bebidas")
    void testMastercardDescuentoSoloPlatos() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 4);     // 4 * 5 = 20 (bebida)
        pedido.agregarLinea(pizza, 3);       // 3 * 15 = 45 (platos)
        // Subtotal: 65
        // Descuento (2% en platos): 45 * 0.02 = 0.90

        TarjetaCredito mastercardCard = new TarjetaCredito("2222", "Cliente", TipoTarjeta.MASTERCARD);
        pedido.confirmar(mastercardCard, 2);

        assertEquals(20.0, pedido.getSubtotalBebidas());
        assertEquals(45.0, pedido.getSubtotalPlatos());
        assertEquals(65.0, pedido.getSubtotal());
        assertEquals(0.90, pedido.getDescuento(), 0.01);
        assertEquals(64.10, pedido.getSubtotalConDescuento(), 0.01);
    }

    @Test
    @DisplayName("MASTERCARD sin platos: descuento 0")
    void testMastercardSinPlatos() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 3);
        pedido.agregarLinea(jugo, 2);

        TarjetaCredito mastercardCard = new TarjetaCredito("2222", "Cliente", TipoTarjeta.MASTERCARD);
        pedido.confirmar(mastercardCard, 2);

        assertEquals(21.0, pedido.getSubtotalBebidas());
        assertEquals(0.0, pedido.getSubtotalPlatos());
        assertEquals(21.0, pedido.getSubtotal());
        assertEquals(0.0, pedido.getDescuento());
        assertEquals(21.0, pedido.getSubtotalConDescuento());
    }

    // ==================== Tests Descuento COMARCA PLUS ====================

    @Test
    @DisplayName("3. Cálculo de costo con tarjeta COMARCA PLUS (2% descuento en total)")
    void testDescuentoComarcaPlus() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 2);     // 2 * 5 = 10 (bebida)
        pedido.agregarLinea(asado, 1);       // 1 * 25 = 25 (plato)
        // Subtotal: 35
        // Descuento (2% en total): 35 * 0.02 = 0.70
        // Subtotal con descuento: 34.30

        TarjetaCredito comarcaPlusCard = new TarjetaCredito("1111222233334444", "Carlos López", TipoTarjeta.COMARCA_PLUS);
        pedido.confirmar(comarcaPlusCard, 5); // 5% propina

        assertEquals(10.0, pedido.getSubtotalBebidas());
        assertEquals(25.0, pedido.getSubtotalPlatos());
        assertEquals(35.0, pedido.getSubtotal());
        assertEquals(0.70, pedido.getDescuento(), 0.01);
        assertEquals(34.30, pedido.getSubtotalConDescuento(), 0.01);
        assertEquals(1.715, pedido.getPropina(), 0.01); // 5% de 34.30
        assertEquals(36.015, pedido.getTotal(), 0.01);  // 34.30 + 1.715
    }

    @Test
    @DisplayName("COMARCA PLUS descuento en total bebidas y platos")
    void testComarcaPlusDescuentoEnTotal() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(jugo, 2);        // 2 * 3 = 6 (bebida)
        pedido.agregarLinea(pizza, 2);       // 2 * 15 = 30 (plato)
        // Subtotal: 36
        // Descuento (2% en total): 36 * 0.02 = 0.72

        TarjetaCredito comarcaPlusCard = new TarjetaCredito("3333", "Cliente", TipoTarjeta.COMARCA_PLUS);
        pedido.confirmar(comarcaPlusCard, 3);

        assertEquals(6.0, pedido.getSubtotalBebidas());
        assertEquals(30.0, pedido.getSubtotalPlatos());
        assertEquals(36.0, pedido.getSubtotal());
        assertEquals(0.72, pedido.getDescuento(), 0.01);
        assertEquals(35.28, pedido.getSubtotalConDescuento(), 0.01);
    }

    @Test
    @DisplayName("COMARCA PLUS con solo bebidas: descuento en total")
    void testComarcaPlusSoloBebidas() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 5);     // 5 * 5 = 25

        TarjetaCredito comarcaPlusCard = new TarjetaCredito("3333", "Cliente", TipoTarjeta.COMARCA_PLUS);
        pedido.confirmar(comarcaPlusCard, 2);

        assertEquals(25.0, pedido.getSubtotal());
        assertEquals(0.50, pedido.getDescuento(), 0.01); // 2% de 25
        assertEquals(24.50, pedido.getSubtotalConDescuento(), 0.01);
    }

    // ==================== Tests Descuento OTRA TARJETA ====================

    @Test
    @DisplayName("4. Cálculo de costo con tarjeta VIEDMA (sin descuento)")
    void testDescuentoViedma() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 2);     // 2 * 5 = 10 (bebida)
        pedido.agregarLinea(asado, 1);       // 1 * 25 = 25 (plato)
        // Subtotal: 35
        // Descuento: 0 (no aplica para Viedma)
        // Subtotal con descuento: 35

        TarjetaCredito viedmaCard = new TarjetaCredito("5555555555555555", "Pedro Ramos", TipoTarjeta.OTRA);
        pedido.confirmar(viedmaCard, 2); // 2% propina

        assertEquals(10.0, pedido.getSubtotalBebidas());
        assertEquals(25.0, pedido.getSubtotalPlatos());
        assertEquals(35.0, pedido.getSubtotal());
        assertEquals(0.0, pedido.getDescuento());
        assertEquals(35.0, pedido.getSubtotalConDescuento());
        assertEquals(0.70, pedido.getPropina(), 0.01); // 2% de 35
        assertEquals(35.70, pedido.getTotal(), 0.01);  // 35 + 0.70
    }

    @Test
    @DisplayName("Tarjeta OTRA con múltiples items sin descuento")
    void testOtraTarjetaMultiplesItems() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 3);
        pedido.agregarLinea(jugo, 2);
        pedido.agregarLinea(asado, 1);
        pedido.agregarLinea(pizza, 1);

        TarjetaCredito otraCard = new TarjetaCredito("6666", "Cliente", TipoTarjeta.OTRA);
        pedido.confirmar(otraCard, 5);

        assertEquals(21.0, pedido.getSubtotalBebidas());
        assertEquals(40.0, pedido.getSubtotalPlatos());
        assertEquals(61.0, pedido.getSubtotal());
        assertEquals(0.0, pedido.getDescuento());
        assertEquals(61.0, pedido.getSubtotalConDescuento());
        assertEquals(3.05, pedido.getPropina(), 0.01); // 5% de 61
        assertEquals(64.05, pedido.getTotal(), 0.01);
    }

    // ==================== Tests de Propinas ====================

    @Test
    @DisplayName("Propina del 2%")
    void testPropina2Porciento() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(asado, 1); // 25

        TarjetaCredito tarjeta = new TarjetaCredito("1111", "Cliente", TipoTarjeta.VISA);
        pedido.confirmar(tarjeta, 2);

        assertEquals(25.0, pedido.getSubtotal());
        assertEquals(25.0, pedido.getSubtotalConDescuento());
        assertEquals(0.50, pedido.getPropina(), 0.01); // 2% de 25
        assertEquals(25.50, pedido.getTotal(), 0.01);
    }

    @Test
    @DisplayName("Propina del 3%")
    void testPropina3Porciento() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(asado, 1); // 25

        TarjetaCredito tarjeta = new TarjetaCredito("1111", "Cliente", TipoTarjeta.VISA);
        pedido.confirmar(tarjeta, 3);

        assertEquals(25.0, pedido.getSubtotal());
        assertEquals(25.0, pedido.getSubtotalConDescuento());
        assertEquals(0.75, pedido.getPropina(), 0.01); // 3% de 25
        assertEquals(25.75, pedido.getTotal(), 0.01);
    }

    @Test
    @DisplayName("Propina del 5%")
    void testPropina5Porciento() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(asado, 1); // 25

        TarjetaCredito tarjeta = new TarjetaCredito("1111", "Cliente", TipoTarjeta.VISA);
        pedido.confirmar(tarjeta, 5);

        assertEquals(25.0, pedido.getSubtotal());
        assertEquals(25.0, pedido.getSubtotalConDescuento());
        assertEquals(1.25, pedido.getPropina(), 0.01); // 5% de 25
        assertEquals(26.25, pedido.getTotal(), 0.01);
    }

    // ==================== Tests de Menú ====================

    @Test
    @DisplayName("Obtener bebidas del menú")
    void testObtenerBebidas() {
        assertEquals(2, restaurante.getBebidas().size());
        assertTrue(restaurante.getBebidas().contains(cerveza));
        assertTrue(restaurante.getBebidas().contains(jugo));
    }

    @Test
    @DisplayName("Obtener platos del menú")
    void testObtenerPlatos() {
        assertEquals(2, restaurante.getPlatos().size());
        assertTrue(restaurante.getPlatos().contains(asado));
        assertTrue(restaurante.getPlatos().contains(pizza));
    }

    @Test
    @DisplayName("Obtener menú completo")
    void testObtenerMenuCompleto() {
        var menu = restaurante.getMenu();
        assertEquals(2, menu.keySet().size());
        assertTrue(menu.containsKey("Bebidas"));
        assertTrue(menu.containsKey("Platos"));
    }

    // ==================== Tests de Limpiar Mesa ====================

    @Test
    @DisplayName("Limpiar mesa después de pagar")
    void testLimpiarMesa() {
        mesa1.agregarComensal("Juan");
        mesa1.agregarComensal("María");
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(asado, 1);

        assertEquals(2, mesa1.getComensales().size());

        mesa1.limpiar();

        assertEquals(0, mesa1.getComensales().size());
        assertNull(mesa1.getPedidoActual());
    }

    // ==================== Tests de Casos Especiales ====================

    @Test
    @DisplayName("Múltiples mesas simultáneamente")
    void testMultiplesMesasSimultaneamente() {
        // Mesa 1
        Mesa m1 = restaurante.getMesa(1);
        m1.agregarComensal("Juan");
        Pedido p1 = m1.crearPedido();
        p1.agregarLinea(cerveza, 1);

        // Mesa 2
        Mesa m2 = restaurante.getMesa(2);
        m2.agregarComensal("María");
        Pedido p2 = m2.crearPedido();
        p2.agregarLinea(asado, 1);

        assertNotEquals(p1.getSubtotal(), p2.getSubtotal());
        assertEquals(5.0, p1.getSubtotal());
        assertEquals(25.0, p2.getSubtotal());
    }

    @Test
    @DisplayName("Cálculo complejo con muchos items")
    void testCalculoComplejoConMuchosItems() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 4);     // 4 * 5 = 20
        pedido.agregarLinea(jugo, 3);        // 3 * 3 = 9
        pedido.agregarLinea(asado, 2);       // 2 * 25 = 50
        pedido.agregarLinea(pizza, 1);       // 1 * 15 = 15
        // Total: 94

        TarjetaCredito tarjeta = new TarjetaCredito("1111", "Cliente", TipoTarjeta.COMARCA_PLUS);
        pedido.confirmar(tarjeta, 3);

        assertEquals(29.0, pedido.getSubtotalBebidas()); // 20 + 9
        assertEquals(65.0, pedido.getSubtotalPlatos()); // 50 + 15
        assertEquals(94.0, pedido.getSubtotal());
        assertEquals(1.88, pedido.getDescuento(), 0.01); // 2% de 94
        assertEquals(92.12, pedido.getSubtotalConDescuento(), 0.01);
        assertEquals(2.7636, pedido.getPropina(), 0.01); // 3% de 92.12
        assertEquals(94.8836, pedido.getTotal(), 0.01);
    }

    @Test
    @DisplayName("MenuItem toString")
    void testMenuItemToString() {
        assertTrue(cerveza.toString().contains("Cerveza"));
        assertTrue(asado.toString().contains("Asado"));
    }

    @Test
    @DisplayName("LineaPedido toString")
    void testLineaPedidoToString() {
        LineaPedido linea = new LineaPedido(cerveza, 3);
        assertTrue(linea.toString().contains("Cerveza"));
        assertTrue(linea.toString().contains("3"));
    }

    @Test
    @DisplayName("TarjetaCredito getters")
    void testTarjetaCreditoGetters() {
        TarjetaCredito tarjeta = new TarjetaCredito("1234567890", "Juan", TipoTarjeta.VISA);
        assertEquals("1234567890", tarjeta.getNumero());
        assertEquals("Juan", tarjeta.getTitular());
        assertEquals(TipoTarjeta.VISA, tarjeta.getTipo());
    }

    @Test
    @DisplayName("Pedido getters antes de confirmar")
    void testPedidoGettersAntesDeConfirmar() {
        Pedido pedido = mesa1.crearPedido();
        pedido.agregarLinea(cerveza, 2);
        
        assertFalse(pedido.isConfirmado());
        assertNull(pedido.getTarjetaCredito());
        assertEquals(0, pedido.getPorcentajePropina());
        assertEquals(0, pedido.getDescuento());
        assertEquals(0, pedido.getPropina());
        assertEquals(10.0, pedido.getTotal());
    }

    @Test
    @DisplayName("Mesa getters")
    void testMesaGetters() {
        Mesa mesa = restaurante.getMesa(5);
        assertEquals(5, mesa.getNumero());
        assertTrue(mesa.getCapacidad() > 0);
        assertFalse(mesa.tienePedidoConfirmado());
    }
}



