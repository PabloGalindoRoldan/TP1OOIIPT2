# Sistema de Cálculo de Costos de Restaurante

## Descripción General

Sistema implementado en Java que calcula el costo total consumido por cada mesa en un restaurante. El sistema incluye gestión de mesas, menú de bebidas y platos, pedidos con descuentos según tipo de tarjeta y propina obligatoria.

## Características Principales

### 1. Gestión de Mesas
- 10 mesas con diferentes capacidades (2, 4, 6, 8 y 10 comensales)
- Registro de comensales por mesa
- Control de pedidos por mesa

### 2. Menú
- Bebidas con precio individual
- Platos principales con precio individual
- Extensible para nuevos items

### 3. Pedidos
- Selección de items (bebidas y platos) con cantidad
- Confirmación de pedidos (no se puede modificar después de confirmado)
- Validación de datos

### 4. Sistema de Descuentos por Tarjeta
- **VISA**: 3% descuento en bebidas
- **MASTERCARD**: 2% descuento en platos principales
- **COMARCA_PLUS**: 2% descuento en total (bebidas + platos)
- **OTRA**: Sin descuento

### 5. Propina Obligatoria
- Opciones: 2%, 3% o 5%
- Se calcula sobre el subtotal CON descuento aplicado

## Arquitectura

### Clases Principales

#### MenuItem (abstracta)
Base para los elementos del menú con nombre y precio.

#### Bebida
Extiende MenuItem, representa bebidas del menú.

#### Plato
Extiende MenuItem, representa platos principales.

#### TipoTarjeta (Enum)
Define los tipos de tarjeta soportados: VISA, MASTERCARD, COMARCA_PLUS, OTRA.

#### TarjetaCredito
Representa una tarjeta de crédito con número, titular y tipo.

#### LineaPedido
Representa una línea de pedido (item + cantidad).

#### Pedido
Gestiona el pedido de una mesa:
- Agregar líneas antes de confirmar
- Confirmación con tarjeta y porcentaje de propina
- Cálculo de descuentos y total

#### Mesa
Representa una mesa del restaurante:
- Número y capacidad
- Registro de comensales
- Gestión del pedido actual
- Limpieza después de que se van los comensales

#### Restaurante
Gestiona el restaurante completo:
- 10 mesas
- Menú (bebidas y platos)
- Acceso a mesas por número

## Fórmulas de Cálculo

### Subtotal
```
Subtotal = Suma de (precio_item × cantidad) para todos los items
```

### Descuento según tarjeta
```
VISA: Descuento = SubtotalBebidas × 0.03
MASTERCARD: Descuento = SubtotalPlatos × 0.02
COMARCA_PLUS: Descuento = Subtotal × 0.02
OTRA: Descuento = 0
```

### Subtotal con descuento
```
SubtotalConDescuento = Subtotal - Descuento
```

### Propina
```
Propina = SubtotalConDescuento × (porcentajePropina / 100)
```

### Total a pagar
```
Total = SubtotalConDescuento + Propina
```

## Ejemplos de Uso

### Crear un restaurante y agregar menú
```java
Restaurante restaurante = new Restaurante("Mi Restaurante");
restaurante.agregarBebida(new Bebida("Cerveza", 5.00));
restaurante.agregarPlato(new Plato("Asado", 25.00));
```

### Crear un pedido
```java
Mesa mesa = restaurante.getMesa(1);
mesa.agregarComensal("Juan");
Pedido pedido = mesa.crearPedido();

pedido.agregarLinea(new Bebida("Cerveza", 5.00), 2);
pedido.agregarLinea(new Plato("Asado", 25.00), 1);

TarjetaCredito tarjeta = new TarjetaCredito("1234567890", "Juan", TipoTarjeta.VISA);
pedido.confirmar(tarjeta, 3); // 3% de propina

System.out.println("Total: $" + pedido.getTotal());
```

## Tests

Se incluyen 42 tests exhaustivos que cubren:

### Tests de Descuentos (requisitos principales)
1. **Test VISA**: Verificar 3% descuento en bebidas
2. **Test MASTERCARD**: Verificar 2% descuento en platos
3. **Test COMARCA_PLUS**: Verificar 2% descuento en total
4. **Test VIEDMA (OTRA)**: Verificar sin descuento

### Cobertura de Tests
- Tests de creación y validación de mesas
- Tests de creación y validación de pedidos
- Tests de confirmación de pedidos
- Tests de cálculo de subtotales parciales (bebidas/platos)
- Tests de descuentos por tarjeta
- Tests de propinas (2%, 3%, 5%)
- Tests de casos especiales (múltiples mesas, muchos items)
- Tests de getters y toString

**Cobertura total: >90%** (42 tests exitosos)

## Ejecución

### Compilar
```bash
mvn clean compile
```

### Ejecutar Tests
```bash
mvn test
```

### Ejecutar Ejemplo
```bash
mvn clean compile
java -cp "target\classes" org.example.Main
```

## Estructura de Carpetas

```
TPOOIIPT2/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           ├── Main.java
│   │   │           ├── MenuItem.java
│   │   │           ├── Bebida.java
│   │   │           ├── Plato.java
│   │   │           ├── TipoTarjeta.java
│   │   │           ├── TarjetaCredito.java
│   │   │           ├── LineaPedido.java
│   │   │           ├── Pedido.java
│   │   │           ├── Mesa.java
│   │   │           └── Restaurante.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── org/
│               └── example/
│                   └── RestauranteTest.java
└── target/
```

## Notas de Implementación

- Utiliza colecciones (ArrayList, HashMap) para almacenar datos
- Implementa validaciones en todos los métodos críticos
- Utiliza enums para tipos de tarjeta
- Herencia para MenuItem y sus subclases
- Tests con JUnit 5
- Uso de streams para cálculos de subtotales

## Autor

Sistema desarrollado para trabajo práctico de Orientación a Objetos II - Licenciatura en Sistemas

