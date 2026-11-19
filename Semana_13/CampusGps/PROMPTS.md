# PROMPTS.md - Fase 2 (con IA)

Prompts principales que usé con Claude en la Fase 2. La IA propone y yo decido: probé cada mejora y revisé que respetara MVVM + Repository.

| Prompt que escribí | Qué respondió | Qué cambié o descarté | Por qué |
|---|---|---|---|
| **Mejora 1:** chips para filtrar por categoría; el ViewModel guarda la categoría y expone la lista filtrada. | Creó `FiltroCategorias` y la lista filtrada con `combine` en el ViewModel. | Revisé que el filtro viva en el ViewModel. No necesité cambios. | La View no debe filtrar datos. |
| **Mejora 2:** selector de radio (100, 200, 500 m) con círculo en el mapa y chip "Dentro del radio". | Creó `util/Radio.kt`, `SelectorRadio` y el `Circle` en el mapa. | Probé con 500 m en el celular. No cambié el código. | El cálculo está en `util/`, no en un Composable. |
| **Mejora 3:** activar el punto azul y el botón de mi ubicación solo con permiso. | Agregó `permisoConcedido` en la View, sin tocar el ViewModel. | Probé que aparecen al conceder el permiso. | Sin permiso puede fallar, así que solo se activa si fue concedido. |
| **Mejora 4:** línea punteada al lugar elegido, "X min a pie" y botón "Cómo llegar". | Creó `Caminata.kt`, `RutaGoogleMaps.kt` y la `Polyline`. Usó `material-icons-core`. | Acepté `material-icons-core` y dejé la línea recta, no ruta por calles. | Evité una librería pesada. El Intent va en la View porque necesita `Context`. |
| **Mejora 5:** favoritos con estrella, DataStore y chip "Mis lugares". | Creó `FavoritosRepository` con DataStore y pidió confirmar la versión. | Aprobé DataStore 1.2.1 antes de agregarla. | Los favoritos persisten y el ViewModel no recibe `Context`. |
| **Pulido final:** componentes Material 3, progreso en el botón y errores con `errorContainer`. | Creó `BarraAcciones`, `EstadoUbicacion` y dejó `PantallaMapa` corta. | Cambié el color primario a azul en el tema. | El morado de la plantilla no coincidía con el diseño. |
| **Rediseño:** mapa a pantalla completa con barra, filtros y panel flotantes (mockup). | Creó `BarraSuperiorFlotante` y `PanelInferior`, con `contentPadding` en el mapa. | Descarté el marcador con brillo del mockup. Corregí el botón que se cortaba. | El marcador era solo estética. El texto no cabía en pantallas angostas. |

## Notas

- La View solo habla con su ViewModel; el ViewModel solo con interfaces de Repository y sin `Context`.
- Distancia, radio y minutos a pie se calculan en `util/` y en el ViewModel, no en Composables.
- Dependencias agregadas: `material-icons-core` y `datastore-preferences:1.2.1`.