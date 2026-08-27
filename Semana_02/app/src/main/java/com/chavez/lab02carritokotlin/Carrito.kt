package com.chavez.lab02carritokotlin

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

fun main() {
    println("=========================================")
    println("   CARRITO DE COMPRAS - TIENDA TECSUP    ")
    println("=========================================")

    val nombreCliente = "Karla Chavez"
    val carrito = mutableListOf<Producto>()

    println("Cliente: $nombreCliente\n")

    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Funda protectora", 120.0, 1))
    carrito.add(Producto("Audifonos Pro3", 25.0, 3))

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }

    mostrarDetalle(carrito)
    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println()
    println(String.format("Subtotal : S/ %.2f", subtotal))
    println(String.format("IGV (18%%): S/ %.2f", igv))
    println(String.format("TOTAL    : S/ %.2f", total))


    println("\n--- Buscando producto ---")
    val encontrado = buscarProducto(carrito, "Mouse Logitech")
    if (encontrado != null) {
        println("Encontrado: ${encontrado.nombre} a S/ ${encontrado.precio}")
    } else {
        println("No se encontro el producto")
    }


    println("\n--- Eliminando Funda protectora ---")
    carrito.removeIf { it.nombre == "Funda protectora" }


    mostrarDetalle(carrito)
    val nuevoSubtotal = calcularSubtotal(carrito)
    val nuevoIgv = calcularIGV(nuevoSubtotal)
    val nuevoTotal = calcularTotal(nuevoSubtotal, nuevoIgv)

    println()
    println(String.format("Nuevo Subtotal : S/ %.2f", nuevoSubtotal))
    println(String.format("Nuevo IGV (18%%): S/ %.2f", nuevoIgv))
    println(String.format("NUEVO TOTAL    : S/ %.2f", nuevoTotal))
}

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO---------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f",
            i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre == nombre }
}