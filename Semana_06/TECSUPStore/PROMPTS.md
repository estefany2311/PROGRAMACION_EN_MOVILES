# PROMPTS.md · Laboratorio 06, Fase 2 

Registro de los prompts usados con el asistente de IA, organizado por commit. Cada entrada indica el prompt, un resumen de la respuesta y lo que tuve que corregir.

---

## Commit 1: parámetros de favorito en `TarjetaProducto`
**Prompt:** Pegué `AppDrawer.kt`, `TarjetaProducto.kt`, `Producto.kt`, `AppNavigation.kt` y `HomeScreen.kt` y pedí qué cambiar y dónde. (Es el mismo pedido para los commits 1, 2 y 3; la respuesta venía dividida en tres bloques y aquí uso el primero.)

**Respuesta resumida:** `TarjetaProducto` recibe `esFavorito` y `onToggleFavorito` con valores por defecto. La opción "Favoritos" del menú llama a `onToggleFavorito`, y su texto e ícono cambian según el estado ("Quitar de favoritos" con corazón lleno).

**Qué corregí:** Al reemplazar la firma de la función se borró la línea `var expanded by remember { mutableStateOf(false) }` y todas las apariciones de `expanded` quedaron en rojo. Volví a declararla debajo de la firma, antes del `Card`.

---

## Commit 2: estado elevado en `AppNavigation` y conexión con `HomeScreen`
**Prompt:** El mismo pedido de los 5 archivos; aquí uso el segundo bloque de la respuesta.

**Respuesta resumida:** `AppNavigation` guarda los ids favoritos en `mutableStateListOf<Int>()` y define `alternarFavorito`. `HomeScreen` recibe `favoritos` y `onToggleFavorito` y los pasa a cada `TarjetaProducto`, con `esFavorito = producto.id in favoritos`.

**Qué corregí:** Al reemplazar la llamada a la tarjeta en `HomeScreen` se perdió la línea `items(listaProductosPrueba) { producto -> ... }`, por lo que `producto` aparecía en rojo. Restauré esa línea con su llave de cierre y dejé dentro solo la llamada a `TarjetaProducto` con los parámetros nuevos.

---

## Commit 3: badge de favoritos en el drawer
**Prompt:** El mismo pedido de los 5 archivos; aquí uso el tercer bloque de la respuesta.

**Respuesta resumida:** `AppDrawerContent` recibe `cantidadFavoritos`. Dentro del `NavigationDrawerItem` se usa el parámetro `badge` para mostrar un `Badge` con el número solo en el ítem "favoritos" y solo si el contador es mayor que 0. `AppNavigation` le pasa `favoritos.size`.

**Qué corregí:** Ninguna corrección

---

## Commit 4: mejoras adicionales (corazón animado y Snackbar)
**Prompt:** Pedí agregar algo llamativo pero no complicado, que yo pudiera explicar después. Luego pedí los dos archivos completos (`TarjetaProducto.kt` y `AppNavigation.kt`) porque no sabía dónde colocar cada cambio.

**Respuesta resumida:** Propuso un corazón rojo con `AnimatedVisibility` en las tarjetas favoritas y un `Snackbar` con el mensaje "Agregado a favoritos" o "Quitado de favoritos". Entregó ambos archivos completos con los cambios integrados.

**Qué corregí:** Ninguna corrección
