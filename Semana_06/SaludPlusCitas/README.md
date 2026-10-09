# Clínica SaludPlus · App Paciente (Fase 2 - CON-IA)


**Curso:** Programación en Móviles · 4to ciclo

**Alumna:** Karla Estefany Chavez Lazo

## Descripción

Versión evolucionada de la aplicación **SaludPlus Citas**, que incorpora mejoras asistidas por Inteligencia Artificial (IA).

En esta fase se mejoró el módulo de agendamiento de citas, reemplazando la selección de fechas estática por un calendario dinámico e interactivo. Para ello, se utilizaron `java.time.LocalDate`, la navegación semanal y `DateTimeFormatter` para mostrar las fechas en español.

## Punto de partida

Se tomó como base la rama `SIN-IA` (Fase 1) y se refactorizó la lógica de selección de fechas en la pantalla `FechaHoraScreen.kt`.

Además, se creó el módulo auxiliar `CalendarioUtils.kt`, encargado de gestionar los días hábiles, la navegación entre semanas y el formato de las fechas.

## Estructura del proyecto

```text
com.saludplus.citas
├── MainActivity.kt
├── data
│   ├── model
│   │   └── Usuario, Especialidad, Medico, Cita
│   └── repository
│       └── Repositorio
├── navigation
│   ├── Rutas.kt
│   └── AppNavigation.kt
├── util
│   └── CalendarioUtils.kt
└── ui
    ├── theme
    │   ├── Color.kt
    │   ├── Theme.kt
    │   └── Type.kt
    ├── components
    │   └── Componentes reutilizables
    └── screens
        ├── auth
        ├── home
        ├── agendamiento
        │   └── FechaHoraScreen.kt
        ├── citas
        ├── perfil
        ├── resultados
        └── notificaciones
```

## Funcionalidades implementadas

| Funcionalidad        | Descripción                                                                                |
| -------------------- | ------------------------------------------------------------------------------------------ |
| Calendario dinámico  | Genera los días hábiles de la semana utilizando `LocalDate`, omitiendo sábados y domingos. |
| Navegación semanal   | Permite avanzar y retroceder entre semanas mediante controles interactivos.                |
| Formato de fechas    | Utiliza `DateTimeFormatter` para mostrar las fechas en español.                            |
| Interfaz dinámica    | Resalta el día seleccionado y actualiza el mes según la semana activa.                     |
| Horarios disponibles | Filtra los horarios de acuerdo con la fecha seleccionada.                                  |
| Documentación de IA  | Incluye el archivo `PROMPTS.md` con el registro de las instrucciones utilizadas.           |

## Capturas de pantalla

<table>
  <tr>
    <td align="center" width="50%">
      <h3>1. Fecha y hora — Semana actual</h3>
      <img src="https://github.com/user-attachments/assets/e5420d45-6965-4384-b1bf-47fccfd49dc7" width="220" alt="Fecha y hora de la semana actual"/>
    </td>
    <td align="center" width="50%">
      <h3>2. Selección de fecha en español</h3>
      <img src="https://github.com/user-attachments/assets/b45ae69b-0ec8-4f38-960f-812cd4d67125" width="220" alt="Selección de fecha y formato en español"/>
    </td>
  </tr>
  <tr>
    <td align="center" width="50%">
      <h3>3. Navegación y reservación — Siguiente mes</h3>
      <img src="https://github.com/user-attachments/assets/ec342f4f-b54c-4483-8ae4-4215695098fd" width="220" alt="Navegación y reservación del siguiente mes"/>
    </td>
    <td align="center" width="50%">
      <h3>4. Horarios disponibles filtrados</h3>
      <img src="https://github.com/user-attachments/assets/eb63dbef-e901-4027-a011-a9f8142680b5" width="220" alt="Horarios disponibles según la fecha seleccionada"/>
    </td>
  </tr>
</table>


## Commits realizados

Los siguientes commits registran los cambios principales de la Fase 2:

1. `mostrar la fecha en espanol con DateTimeFormatter`
2. `navegar por semanas y mostrar el mes dinamico`
3. `generar los dias habiles con LocalDate`
4. `traer SaludPlusCitas de sin-ia como base de la Fase 2`

## Preguntas de reflexión

### 1. ¿Por qué se utilizó `java.time.LocalDate` en lugar de `java.util.Date`?

Se utilizó `LocalDate` porque es una API moderna de Java que permite trabajar con fechas de forma más sencilla. Es inmutable y cuenta con métodos como `plusWeeks()` y `plusDays()`, que facilitan el cálculo de fechas sin necesidad de escribir tanto código.

### 2. ¿Cómo garantiza el sistema que no se agenden citas en fines de semana?

En `CalendarioUtils.kt` se filtran los días de la semana utilizando `DayOfWeek`. De esta manera, se excluyen los sábados y domingos y se muestran únicamente los días de lunes a viernes en el calendario.

### 3. ¿Cómo se logra la reactividad al cambiar de semana en Jetpack Compose?

Se utiliza una variable de estado llamada `semanaActual`, administrada mediante `remember` y `mutableIntStateOf`. Al presionar las flechas de navegación, el estado cambia y Jetpack Compose actualiza automáticamente la interfaz y los días que se muestran en el calendario.

### 4. ¿Cuál es la ventaja de estructurar las respuestas de la IA en un archivo `PROMPTS.md`?

Permite registrar los prompts utilizados durante el desarrollo, mantener un historial de las instrucciones proporcionadas a la IA y comprender mejor cómo se realizaron los cambios. También facilita la revisión del trabajo y la trazabilidad del código implementado.

## Observaciones

Para mostrar correctamente los nombres de los días y los meses en español, se utiliza la configuración regional `Locale("es", "ES")` dentro de `DateTimeFormatter`.

Esto permite presentar las fechas de una forma más clara y familiar para los usuarios de la aplicación.

## Conclusiones

* La integración de `java.time.LocalDate` permitió simplificar el cálculo de fechas y la generación de los días hábiles del calendario.
* La navegación semanal y la actualización dinámica de la interfaz mejoraron el proceso de selección de fechas para el agendamiento de citas.
* El uso de prompts asistidos por IA facilitó el desarrollo del módulo de utilidades, manteniendo la estructura existente del proyecto y documentando los cambios realizados.

---

**Proyecto académico:** Clínica SaludPlus · App Paciente
**Fase:** 2 — CON-IA
**Desarrollo:** Kotlin · Jetpack Compose
