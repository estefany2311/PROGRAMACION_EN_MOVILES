# PROMPTS · Fase 2 (con IA)

Registro de las consultas a la IA usadas en la rama `con-ia` .

## Consulta 1 · Tema visual de la app

- **Prompt:** Mejorar el diseño de la app con la paleta de los pósters de Valorant: login con el póster de fondo, lista, detalle y panel Admin, que no se vea aburrido ni muy llamativo.
- **Qué respondió (resumen):** Propuso un tema oscuro con colores coral, magenta y lavanda, un color para cada rol, formas con esquinas cortadas y el póster de fondo en el login con un degradado.
- **Qué usamos / qué cambiamos y por qué:** Usamos el tema y los diseños. Corregimos que el logo del póster se recortaba y que el texto del login salía en negro. Nota: este cambio quedó en la rama `sin-ia`.
- **Qué aprendimos:** Centralizar los colores en `ui/theme` hace que un solo cambio afecte a toda la app. Usamos `CutCornerShape` para las esquinas, `Brush` para los degradados y `Surface` para definir el color del texto.

## Consulta 2 · Pruebas unitarias del ListaViewModel

- **Prompt:** Hacer 3 pruebas unitarias del ListaViewModel con un repositorio falso, como pide el laboratorio.
- **Qué respondió (resumen):** Creó un `RepositorioFalso` y un `NotificadorFalso` en memoria y 3 pruebas: con internet, sin internet sin datos y sin internet con datos guardados. Explicó que hay que reemplazar el hilo Main con `Dispatchers.setMain`.
- **Qué usamos / qué cambiamos y por qué:** Usamos las 3 pruebas y agregamos la dependencia `kotlinx-coroutines-test`. Las 3 pasaron en verde.
- **Qué aprendimos:** Como `ItemRepository` es una interface, el ViewModel se puede probar sin internet ni Room. Las pruebas unitarias corren en la computadora, sin emulador.

## Consulta 3 · Filtro por rol y buscador por nombre

- **Prompt:** Hacer una mejora para que en la lista se pueda filtrar por especialidad: Duelista, Centinela, Controlador e Iniciador.
- **Qué respondió (resumen):** Propuso poner el filtro en el ListaViewModel con un `MutableStateFlow` para el rol elegido y `combine` para juntarlo con la lista de Room, y una fila de chips en la pantalla con el color de cada rol.
- **Qué usamos / qué cambiamos y por qué:** Usamos la idea de `combine` en el ViewModel. Karla lo reorganizó en un componente aparte (`FiltroRoles.kt`) y agregó funciones puras `filtrarPorRol` y `filtrarPorNombre` para que sea más fácil de probar.
- **Qué aprendimos:** `combine` junta varios Flow y recalcula la lista cada vez que cambia uno; así la pantalla no filtra nada por su cuenta.
