# NavLab - Aplicación de Navegación en Jetpack Compose (Mejora CON-IA)

**Estudiante:** Karla Chavez Lazo  
**Curso:** Programación en Móviles  
**Rama:** CON-IA  

---

## Descripción General del Proyecto

En este laboratorio se desarrolló e implementó el **Portal Académico**, una aplicación móvil para Android construida con **Jetpack Compose** y **Navigation Compose**. Durante el proceso, se estructuró un flujo de navegación completo que abarca desde la autenticación del usuario hasta la consulta detallada de información académica.

El proyecto fue optimizado mediante asistencia de Inteligencia Artificial (Gemini AI) para integrar la gestión de datos dinámicos mediante `StudentProvider`, un control de sesión seguro con limpieza del historial de navegación (*backstack*) y una interfaz moderna bajo los lineamientos de **Material Design 3**.

---

## Requerimientos Funcionales (RF)

* **Gestión de Estado y Datos Dinámicos:** Se integró `StudentProvider` para centralizar los datos y compartirlos de forma dinámica entre las pantallas, permitiendo personalizar la experiencia del usuario (como saludos de bienvenida y carga de expedientes por ID).
* **Flujo de Navegación Seguro:** Se implementó el control de sesión con limpieza de historial (*backstack*) en la pantalla de perfil, asegurando que el usuario no pueda regresar a rutas protegidas tras cerrar sesión.
* **Componentes Reutilizables:** Se construyó un directorio de alumnos interactivo basado en `LazyColumn` que facilita la transición hacia el expediente individual de cada estudiante (`DetailScreen`).
* **Interfaz de Usuario (UI):** Se refinó el diseño bajo los estándares de **Material Design 3**, logrando una presentación limpia, estructurada y adaptada a las necesidades del proyecto.
---

## Prompt Utilizado con Gemini AI

> 1. **ROL**: Actúa como un Desarrollador Senior en Android especializado en Kotlin, Jetpack Compose y Material 3, con experiencia en refactorización de código y diseño UI/UX.
>
> 2. **CONTEXTO**: Estamos trabajando sobre el proyecto Android existente "NavLab" en la rama `CON-IA` bajo el paquete base `com.chavez.NavLab5`. No deseamos crear una aplicación desde cero. El objetivo es realizar una refactorización incremental sobre el código base para mejorar la presentación visual y el comportamiento de la interfaz, alineándola con los diseños de referencia ("Portal Académico").
>
> 3. **OBJETIVO PRINCIPAL**: Analizar el proyecto existente, reutilizar la lógica y pantallas operativas, y aplicar modificaciones incrementales para reproducir fielmente el diseño de las imágenes de referencia (colores, sombras, bordes redondeados, jerarquía visual, botones, tarjetas e iconografía) garantizando una navegación limpia y cero errores de compilación.
>
> 4. **REGLA FUNDAMENTAL DE TRABAJO**: NO CREAR UNA APLICACIÓN NUEVA DESDE CERO. Trabajar strictly sobre la estructura del proyecto existente en la rama `CON-IA`. Se priorización la modificación de componentes y pantallas sobre la creación de archivos nuevos. No reemplazar un archivo completo si solo se requiere ajustar un bloque de código o estilar un componente.
>
> 5. **ESTRUCTURA Y MÓDULOS DEL PROYECTO**: El proyecto debe mantenerse organizado estrictamente en los siguientes paquetes dentro de `com.chavez.NavLab5`:
>    - `models/`: `Student.kt` (Data class `Student(id, nombre, carrera, correo, facultad, biografia)` y el objeto `StudentProvider` con la lista de datos de prueba).
>    - `components/`: `Components.kt` (Componentes UI reutilizables: `AcademicTextField` con icono, `PrimaryButton` morado, `MenuCardOption` para las tarjetas de acceso y `StudentItemCard` con avatar circular y chevron).
>    - `navigation/`: `Screen.kt` (Sealed class con las rutas: "login", "home", "directory", "detail/{studentId}", "profile") y `AppNavigation.kt` (Grafo NavHost que gestiona los pasos de pantalla y limpia el back stack de forma segura al cerrar sesión).
>    - `screens/`: `LoginScreen.kt` ("Portal Académico"), `HomeScreen.kt` ("Menú Principal"), `DirectoryScreen.kt` ("Directorio de Alumnos"), `DetailScreen.kt` ("Expediente Académico") y `ProfileScreen.kt` ("Configuración de Perfil").
>
> 6. **ESTRATEGIA DE TRABAJO Y ENTREGABLE OBLIGATORIO (FASE 0)**:
>    - **FASE 0**: DIAGNÓSTICO Y ANÁLISIS PREVIO (OBLIGATORIO). Antes de generar cualquier código, debes realizar un análisis inicial del proyecto provisto. Tu PRIMERA RESPUESTA debe ser únicamente un Diagnóstico Breve con los 5 puntos clave (Estado actual, Funcionalidades activas, Aspectos visuales a corregir, Lista de archivos a modificar, Lista de archivos nuevos).
>    - **FASE 1 A FASE 5**: Ejecución posterior con código organizado por fases tras la aprobación del diagnóstico.
>
> 7. **COMPORTAMIENTO FUNCIONAL Y NAVEGACIÓN**:
>    - Login → Home (validando campos).
>    - "Directorio de Alumnos" (Home) → DirectoryScreen.
>    - Clic en Alumno → DetailScreen pasando el `studentId` (manejo seguro si el ID no existe).
>    - "Mi Perfil Académico" (Home) → ProfileScreen.
>    - Botones "Cerrar Sesión" → Redirigir a LoginScreen limpiando la pila de navegación.
>
> 8. **REFERENCIA VISUAL Y ESTILO**: Colores morado primario (`#5A3D7C`), fondo lavanda claro (`#EAE6F8`), tarjetas blancas, esquinas muy redondeadas (`RoundedCornerShape(16.dp)` a `24.dp`) e iconografía de Material Icons.
>
> 9. **FORMATO DE ENTREGA**: Responder PRIMERO con la FASE 0 (Diagnóstico). Tras confirmación, entregar el código organizado archivo por archivo incluyendo `package com.chavez.NavLab5...` e imports completos.---

## Capturas de Pantalla

| Inicio de Sesión | Menú Principal | Directorio de Alumnos | Detalle de Alumno | Perfil de Usuario |
| :---: | :---: | :---: | :---: | :---: |
| <img width="738" height="1600" alt="Pantallalogin" src="https://github.com/user-attachments/assets/b98ecb67-3e3c-421f-b969-1a77c4d36ff0" /> | <img width="738" height="1600" alt="Pantallaini" src="https://github.com/user-attachments/assets/91d46bfe-3373-4fdd-8ae8-be5d8e07dcb5" /> | <img width="738" height="1600" alt="Directorioalum" src="https://github.com/user-attachments/assets/35a63011-005c-44dd-89eb-2f457e1e1f8d" /> | <img width="738" height="1600" alt="Detallealum" src="https://github.com/user-attachments/assets/62919a5b-5bd0-48de-92d9-3dc344738ad5" /> | <img width="738" height="1600" alt="perfil" src="https://github.com/user-attachments/assets/f3413e64-0c9b-45cd-b732-e47392931caa" /> |
