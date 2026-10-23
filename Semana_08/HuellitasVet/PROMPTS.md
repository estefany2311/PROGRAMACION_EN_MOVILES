# Prompts utilizados

IA usada en la Fase 2: Claude, trabajando sobre el proyecto de la rama `CON-IA`. 

Instrucción general que se dio antes de las tres mejoras (se aplica a los tres prompts):

> Luego haz los commits A, B y C con los mensajes exactos de la guía, implementando las mejoras que esos mensajes indican. Para cada una respeta las "capas que cambian" de la tabla de la guía y la condición obligatoria: toda función nueva del Repository se declara primero en la interface; si cambias o agregas una entidad, sube la version de la base con una Migration para no perder datos. Si una mejora no se puede ver con los datos iniciales, agrega datos de prueba al final, sin cambiar los ids de los registros que ya existen.

## Prompt 1

**Texto enviado a Claude:** "sigue con el commit A" (commit `ia: agrega grafico de peso en la ficha de la mascota`), junto con la instrucción general de arriba y la fila de la guía: "La ficha muestra una línea con el peso registrado en cada atención . Capas: DAO + Repository + ViewModel + View".

**Que genero la IA:**
- `PuntoPeso` (fecha y peso) en `model/` y la consulta `CitaDao.observarPesos(mascotaId)` que devuelve `Flow` con los pesos de las citas ATENDIDAS, de la más antigua a la más reciente.
- `observarPesos` declarado primero en `VetRepository` y luego implementado en `VetRepositoryRoom` (solo delega en el DAO).
- `MascotaViewModel.pesos(mascotaId)`: convierte los datos de Room a `List<DatoGrafico>` con `stateIn`.
- `GraficoLineas` dibujado con Canvas (línea, área, puntos, valor y fecha) y una tarjeta "Evolución del peso" en la pestaña Historial clínico de la ficha.

**Que tuve que corregir yo:**
- La primera versión de la ficha anidaba dos `if (pestana == 0)` con una rama vacía; se reordenó en un solo `if / else`.
- Con un solo peso la línea no tiene sentido (Rocky): se agregó el aviso "Se necesitan al menos 2 atenciones con peso".
- El celular se bloqueó a mitad de la prueba; se volvió a probar después de desbloquearlo.

**Captura de la mejora:**

| Gráfico de peso en la ficha |
|---|
| ![Gráfico de peso en la ficha de Luna](capturas/fase2-01-grafico-peso.png) |

**Respeta MVVM?:** Sí. La consulta está en el DAO, la función se declaró primero en la interface, el ViewModel convierte los datos y la View solo dibuja. No se cambió ninguna entidad, por eso la base sigue en la versión 1.

## Prompt 2

**Texto enviado a Claude:** "sigue con el commit B" (commit `ia: agrega reprogramacion de citas`), junto con la instrucción general y la fila de la guía: "El dueño cambia fecha y hora de una cita PENDIENTE o CONFIRMADA respetando horarios libres y la regla de 2 horas. Capas: DAO + Repository + ViewModel + View".

**Que genero la IA:**
- `CitaDao.reprogramar(id, fecha, hora)` (`UPDATE` con `suspend`) y `reprogramarCita` declarado primero en `VetRepository` y luego en `VetRepositoryRoom`.
- `ReprogramarCitaViewModel` nuevo: fechas de hoy a 7 días, horarios con `flatMapLatest` (ocupados y pasados en gris), regla de PENDIENTE o CONFIRMADA y más de 2 horas, y una nueva consulta del horario antes de guardar.
- `ReprogramarCitaScreen` nueva y el botón "Reprogramar" en Mis citas; nueva ruta `reprogramar/{citaId}`.

**Que tuve que corregir yo:**
- Se decidió que una cita reprogramada vuelve a PENDIENTE (el veterinario debe confirmar el nuevo horario) y que conserva su precio.
- En Mis citas, la frase "Solo se puede cancelar hasta 2 horas antes" estaba armada en la View; se movió al ViewModel para que la View no tenga textos de reglas y para usar el mismo diálogo con reprogramar.
- Se reutilizó `HorarioUi` de `PedirCitaViewModel` en lugar de crear otra clase igual.
- Para probar la regla de 2 horas se insertó una cita de prueba solo en la base del celular (no en el código).

**Captura de la mejora:**

| Mis citas con reprogramar | Reprogramar cita |
|---|---|
| ![Mis citas con el botón Reprogramar](capturas/fase2-02-mis-citas-reprogramar.png) | ![Pantalla para reprogramar la cita](capturas/fase2-03-reprogramar-cita.png) |

**Respeta MVVM?:** Sí. La View solo avisa los clics; las reglas (estado, 2 horas, horario libre) viven en el ViewModel; la escritura pasa por la interface `VetRepository`. Sin cambios de entidad ni migración.

## Prompt 3

**Texto enviado a Claude:** "sigue con el commit C" (commit `ia: agrega busqueda de pacientes`). Como la búsqueda por mascota o dueño con filtro por especie ya se había hecho en la Fase 1 , Claude propuso ampliarla y se respondió: "Sí, sigue con el commit C con la búsqueda ampliada que propones." La propuesta fue: buscar también por raza, filtro "Con cita próxima", ordenar por nombre o por última visita, y un contador con "Limpiar filtros". Capas de la guía: ViewModel + View.

**Que genero la IA:**
- `PacientesViewModel` con `FiltrosPacientes` (texto, especie, solo con cita próxima, orden) combinado con las mascotas y un resumen de citas (última atención y próxima cita de cada mascota).
- `PacientesScreen` con el buscador "Buscar por mascota, dueño o raza", chips de especie, "Con cita próxima", "A-Z" y "Última visita", contador de pacientes, "Limpiar filtros" y la próxima cita en cada tarjeta.

**Que tuve que corregir yo:**
- La mejora pedida ya existía desde la Fase 1, así que se acordó qué ampliar antes de escribir código.
- Con todos los filtros como flujos separados el `combine` crecía demasiado; se juntaron en un solo `data class FiltrosPacientes`, que además indica si hay filtros activos.
- Al ordenar por última visita, las mascotas sin visitas quedan al final.

**Captura de la mejora:**

| Búsqueda de pacientes |
|---|
| ![Búsqueda de pacientes con filtros](capturas/fase2-04-busqueda-pacientes.png) |

**Respeta MVVM?:** Sí. Solo cambian el ViewModel y la View; se usan funciones del Repository que ya existían (`observarTodasLasMascotas` y `observarTodasLasCitas`). No cambia el DAO ni la base.

## Reflexion 

- La IA ayudó a avanzar rápido en el código repetitivo (consultas, StateFlow, pantallas) y a mantener las capas ordenadas.
- No reemplaza las decisiones: elegir qué ampliar en una búsqueda que ya existía y revisar cada regla nueva (como que una cita reprogramada vuelva a PENDIENTE) me tocó a mí.
- Aprendí que en MVVM la View solo muestra y avisa, el ViewModel decide las reglas y el Repository es un contrato que se declara primero en la interface.
- Room avisa solo con Flow cuando cambian los datos, y si una mejora no cambia entidades no hace falta subir la versión ni escribir una migración.
