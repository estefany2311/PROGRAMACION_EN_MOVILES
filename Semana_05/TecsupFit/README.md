# TecsupFit - Aplicación Móvil de Gimnasio

**Estudiante:** Karla Chavez Lazo
**Curso:** Programación en Móviles
**Proyecto:** TecsupFit
**Rama:** `CON-IA`

---

## Descripción General

TecsupFit es una aplicación móvil para Android desarrollada con Kotlin y Jetpack Compose, orientada a la consulta y reserva de clases de gimnasio.

La aplicación utiliza Material Design 3 y una estructura basada en Scaffold, permitiendo organizar la interfaz principal y la navegación inferior mediante una BottomBar. Esto facilita el acceso a las secciones principales de Inicio, Reservas, Rutinas y Perfil, manteniendo una navegación organizada entre las diferentes pantallas.
En la rama `CON-IA` se agregaron tres nuevos requerimientos funcionales utilizando **Gemini AI** como apoyo para el desarrollo, manteniendo la estructura y funcionalidades existentes.

---

## Tecnologías

* Kotlin
* Jetpack Compose
* Material Design 3
* Android Studio
* Navigation Compose
* Gemini AI
* Git / GitHub

---

# Requerimientos Funcionales (RF)

## Proyecto Base — RF01 al RF06

| ID       | Requerimiento                     | Archivo / Componente    | Descripción                                                                                     |
| -------- | --------------------------------- | ----------------------- | ----------------------------------------------------------------------------------------------- |
| **RF01** | Mostrar lista de clases y filtros | `HomeScreen.kt`         | Muestra filtros como "Hoy" y "Esta semana", junto con la lista de clases disponibles.           |
| **RF02** | Ver detalle de una clase          | `ClassDetailScreen.kt`  | Muestra nombre, horario, sala, descripción y cupos disponibles, además del botón para reservar. |
| **RF03** | Confirmar la reserva              | `ConfirmationScreen.kt` | Muestra la confirmación de la reserva y el resumen de la clase seleccionada.                    |
| **RF04** | Listar mis reservas               | `ReservationsScreen.kt` | Muestra las clases reservadas y su estado, como "Confirmadas" o "Completadas".                  |
| **RF05** | Visualizar perfil de usuario      | `ProfileScreen.kt`      | Muestra información del usuario, tipo de plan, clases realizadas y rachas.                      |
| **RF06** | Navegación entre pantallas        | `AppNavigation.kt`      | Permite navegar entre Inicio, Reservas, Rutinas, Perfil y el detalle de las clases.             |

---

## Requerimientos Agregados — Rama CON-IA

| ID       | Requerimiento                     | Archivo / Componente                                | Descripción                                                                              |
| -------- | --------------------------------- | --------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| **RF07** | Banner "Tu Próxima Clase"         | `HomeScreen.kt`                                     | Agrega un banner superior con la próxima clase agendada, horario, sala e ícono de reloj. |
| **RF08** | Buscador dinámico en tiempo real  | `HomeScreen.kt`                                     | Permite buscar clases por nombre o sala mientras el usuario escribe.                     |
| **RF09** | Indicadores de nivel e intensidad | `ClaseGym`, `HomeScreen.kt`, `ClassDetailScreen.kt` | Agrega nivel de dificultad y calorías estimadas mediante chips e indicadores visuales.   |

### Resumen de las mejoras

| Proyecto Base           | Mejoras CON-IA               |
| ----------------------- | ---------------------------- |
| RF01 — Lista y filtros  | **RF07 — Próxima clase**     |
| RF02 — Detalle de clase | **RF08 — Buscador dinámico** |
| RF03 — Confirmación     | **RF09 — Nivel y calorías**  |
| RF04 — Mis reservas     |                              |
| RF05 — Perfil           |                              |
| RF06 — Navegación       |                              |

---

# Prompts Utilizados con Gemini AI

Se utilizaron **3 prompts**, uno por cada nuevo requerimiento funcional.

### Prompt 1 — RF07

> Actúa sobre el proyecto Android existente TecsupFit en la rama `CON-IA`. Implementa únicamente el RF07: Banner "Tu Próxima Clase" en `HomeScreen.kt`. Agrega un Card antes del filtro de listas con degradado de `#00695C` a `#004D40`, bordes redondeados, etiqueta "TU PRÓXIMA CLASE", nombre "Cross Training", horario "Hoy, 6:00 pm · Sala 1" e ícono `Icons.Default.Schedule`. Mantén las funcionalidades existentes y no realices cambios fuera de este requerimiento.

### Prompt 2 — RF08

> Actúa sobre el proyecto Android existente TecsupFit en la rama `CON-IA`. Implementa únicamente el RF08: Buscador Dinámico en Tiempo Real en `HomeScreen.kt`. Agrega un `OutlinedTextField` con `Icons.Default.Search`, placeholder "Buscar clase o disciplina..." e ícono `Icons.Default.Clear`. Utiliza `var searchQuery by remember { mutableStateOf("") }` para filtrar `clasesDisponibles` por nombre o sala en tiempo real. Mantén las funcionalidades existentes y no realices cambios fuera de este requerimiento.

### Prompt 3 — RF09

> Actúa sobre el proyecto Android existente TecsupFit en la rama `CON-IA`. Implementa únicamente el RF09: Indicadores de Nivel e Intensidad. Agrega los campos `nivel` y `calorias` al modelo `ClaseGym`, actualiza los datos de prueba y muestra estos valores en las tarjetas de `HomeScreen.kt` y en `ClassDetailScreen.kt`. Utiliza indicadores de color para Principiante, Intermedio y Avanzado, además de un ícono para las calorías. Mantén RF07, RF08 y las funcionalidades existentes.

---

# Capturas de Pantalla

## RF07, RF08 y RF09

A continuación se muestran las evidencias de las funcionalidades agregadas en la rama `CON-IA`.

|                                         **RF07 — Próxima Clase**                                        |                                           **RF08 — Buscador**                                           |                                       **RF09 — Nivel y Calorías**                                       |
| :-----------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------: |
| <img src="https://github.com/user-attachments/assets/7d5e73a1-bf7f-457c-bf99-3ac1f0deda93" width="250"> | <img src="https://github.com/user-attachments/assets/58a1ce19-962a-4642-9c8e-a72aef24d71a" width="250"> | <img src="https://github.com/user-attachments/assets/156b21f8-84ea-4348-b7dc-9b508f554297" width="250"> |

### Detalle de Clase — RF09

<img src="https://github.com/user-attachments/assets/85b55911-9804-4a65-9cb3-9cd9e443cd2d" width="350">

**RF09:** La pantalla de detalle muestra el nivel de dificultad y las calorías estimadas de la clase.

---

# Commits de la Mejora CON-IA

| Commit       | Requerimiento | Implementación                   |
| ------------ | ------------- | -------------------------------- |
| **Commit 1** | RF07          | Banner "Tu Próxima Clase"        |
| **Commit 2** | RF08          | Buscador dinámico en tiempo real |
| **Commit 3** | RF09          | Indicadores de nivel y calorías  |

---

# Estructura Principal

```text
TecsupFit/
│
├── app/
│   └── src/main/java/com/chavez/tecsupfit/
│       ├── data/
│       │   └── DatosPruebaFit.kt
│       │
│       ├── navigation/
│       │   ├── AppNavigation.kt
│       │   └── Screen.kt
│       │
│       ├── screens/
│       │   ├── HomeScreen.kt
│       │   ├── ClassDetailScreen.kt
│       │   ├── ConfirmationScreen.kt
│       │   ├── ReservationsScreen.kt
│       │   ├── ProfileScreen.kt
│       │   └── RoutinesScreen.kt
│       │
│       └── MainActivity.kt
│
│
└── README.md
```

---
