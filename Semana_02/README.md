# Laboratorio 02: Carrito de Compras en Kotlin

Estudiante: Karla Estefany Chavez  
Curso:  Programación en Móviles

## Descripción del Proyecto
Este programa implementa un sistema de carrito de compras funcional en consola utilizando Kotlin. 
Permite registrar una lista de productos mediante data class, 
calcular automáticamente subtotales, IGV y total general, y mostrar un reporte y detalle de los productos.

### Funciones Implementadas:
Calcular Subtotal: Suma de "precio * cantidad" para cada producto.

Calcular IGV: Aplicación del impuesto sobre el subtotal.

Calcular Total: Monto final a pagar.

Reporte de Detalle: Imprime la lista de los productos con sus totales.

Filtros y Descuentos:El producto más caro recibe el descuento aqui usamos "when".


## Pregunta de la parte 2 : Diferencia entre val y var
val (Inmutable): Define una variable de solo lectura. Una vez asignado su valor, no puede ser modificado. Se recomienda para garantizar seguridad.

var (Mutable): Define una variable cuyo valor puede cambiar durante la ejecución del programa. Se utiliza cuando el estado o dato debe ser reasignado.


## Captura de Pantalla
![Captura de pantalla 2026-08-27 032151.png](../../../Pictures/Screenshots/Captura%20de%20pantalla%202026-08-27%20032151.png)