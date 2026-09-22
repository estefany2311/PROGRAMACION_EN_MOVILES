# Lab 04: Carrito de Compras en Jetpack Compose

**Estudiante:** Karla Estefany Chavez Lazo

## Descripción
Aplicación móvil desarrollada en Android con Jetpack Compose que implementa un carrito de compras dinámico con formulario de registro de productos, lista interactiva (LazyColumn), tarjeta de producto personalizada con opción de eliminación y cálculo automático de Subtotal, IGV y Total.

## Capturas de Pantalla

| Estado Vacío | Con Productos |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/02bfb1d1-83bb-4d20-a816-78f9cb0357e2" width="280" alt="VACIO" /> | <img src="https://github.com/user-attachments/assets/a717a238-90c2-4342-a2d5-0779b45088ae" width="280" alt="CONPRODUCTOS" /> |

## Respuestas del Lab

###  ¿Por qué `mutableStateListOf` y no una `MutableList` normal?
Porque `mutableStateListOf` es una estructura observable por el runtime de Jetpack Compose. Cuando se añaden o remueven elementos, Compose detecta automáticamente la modificación y desencadena la recomposición de la interfaz de usuario en tiempo real. Una `MutableList` normal modificaría la memoria, pero Compose no se enteraría del cambio y la pantalla no se actualizaría.

###  ¿Por qué la lista es `val`?
La referencia de la lista observable en sí no cambia (sigue apuntando al mismo contenedor `snapshots.SnapshotStateList`), lo que cambia es su contenido interno (los elementos guardados). Como no estamos reasignando la variable a un objeto completamente nuevo, debe ser declarada como `val`.

###  ¿Qué hace `weight(1f)` en la `LazyColumn`?
El modificador `weight(1f)` dentro de un contenedor flexible (`Column`) le indica a la `LazyColumn` que debe absorber todo el espacio vertical disponible que dejen libre los demás elementos (el formulario superior y el panel de totales inferior). Esto evita que el panel de totales sea empujado fuera de la pantalla cuando la lista crece.
