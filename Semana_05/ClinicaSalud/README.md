# Clínica Salud+ — Fase 2

**Estudiante:** Karla Chavez Lazo
**Curso:** Programación en Móviles
**Proyecto:** Clínica Salud+
**Rama:** `CON-IA`

---

## Descripción

Clínica Salud+ es una aplicación móvil desarrollada con **Kotlin**, **Jetpack Compose** y **Material Design 3**.

En esta segunda fase se implementaron nuevos requerimientos funcionales relacionados con la gestión de citas, el perfil del usuario y el historial médico.

Las funcionalidades fueron desarrolladas sobre el proyecto existente, manteniendo la estructura y navegación de la aplicación.

---

#Requerimientos Funcionales — Fase 2

| ID       | Requerimiento                 | Descripción                                                                                   |
| -------- | ----------------------------- | --------------------------------------------------------------------------------------------- |
| **RF09** | Cancelación de Citas          | Permite cancelar una cita desde la sección "Mis Citas" mediante un cuadro de confirmación.    |
| **RF10** | Perfil de Usuario y Edición   | Permite visualizar los datos del paciente y modificar el número de teléfono.                  |
| **RF11** | Historial Médico y Navegación | Permite consultar información médica y visualizar consultas anteriores desde el menú lateral. |


---

#  Capturas de Pantalla

## RF09, RF10 y RF11

|              **RF09 — Cancelación de Cita**             |             **RF10 — Perfil y Edición**             |               **RF11 — Historial Médico**              |
| :-----------------------------------------------------: | :-------------------------------------------------: | :----------------------------------------------------: |
| <img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/7809725b-556b-44ba-b1b5-67d4282736cc" />| <img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/8dd586ec-e096-40de-b6b6-a4d7ec263e3e" />| <img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/67f30e95-ce97-43ea-b4ba-c5fda6f9dada" />|



## Prompts utilizados

Durante la implementación de la Fase 2 se utilizaron los siguientes prompts con Gemini AI:

### RF09 — Cancelación de citas

> Trabaja exclusivamente en este archivo (MyAppointmentsScreen.kt). Necesito agregar la funcionalidad de cancelar citas. Si el usuario presiona una cita con estado "Confirmada", muestra un AlertDialog de Material 3 con el título "¿Cancelar cita?" y los botones "Sí, cancelar" y "No". Al confirmar, cambia su estado a "Cancelada". Ajusta el diseño de la tarjeta (Card) agregando bordes redondeados (RoundedCornerShape(16.dp)) y un AssistChip en tono rojo suave para la cita cancelada.

> RESTRICCIONES:
>
> * No crees nuevos archivos ni clases externas.
> * Utiliza únicamente imports nativos de androidx.compose.material3 y androidx.compose.ui.
> * Conserva las llamadas a DatosPrueba.citasIniciales y la estructura original del archivo sin modificar otros paquetes.
> * No uses ViewModels, solo remember y mutableStateListOf.

### RF10 — Perfil del paciente

> Implementa el contenido de ProfileScreen.kt de forma limpia y ordenada dentro del paquete com.chavez.clinicasalud.screens. En la parte superior debe mostrar un avatar circular con las iniciales "KC", el nombre "Karla Chavez" y el rol "Paciente". Agrega tarjetas M3 (Card) estilizadas con bordes de 16.dp para la información personal (Correo, Teléfono, Tipo de Sangre) y un Switch para "Notificaciones de recordatorio". Incluye un botón "Editar Datos" que al presionar abra un diálogo sencillo para modificar el teléfono.

> RESTRICCIONES:
>
> * Mantén todo el código encapsulado dentro de ProfileScreen.kt.
> * No agregues librerías de terceros ni modifiques la navegación global.
> * Usa remember y mutableStateOf para los estados locales.

### RF11 — Historial médico

> Diseña e implementa la interfaz de Historial Médico dentro de este archivo. En la parte superior incluye una tarjeta resumen de salud del paciente (Alergias: Ninguna, Tipo de Sangre: O+). Debajo, muestra una lista de consultas médicas pasadas usando tarjetas M3 con bordes redondeados. Cada registro debe incluir: Fecha de atención, Nombre del médico, Especialidad, Diagnóstico y Medicamento recetado.

> RESTRICCIONES:
>
> * Genera datos de prueba simulados dentro del mismo archivo con remember { mutableStateListOf(...) }.
> * No importes paquetes inexistentes ni crees arquitecturas complejas fuera de esta pantalla.
> * Utiliza la paleta de colores M3 coherente con el resto del proyecto.


#  Correcciones y problemas encontrados

## 1. Conflicto durante Git Merge

Durante la sincronización de la rama `CON-IA` se presentó un conflicto en el proceso de merge.

Se solucionó cancelando el merge y posteriormente sincronizando nuevamente la rama con el repositorio remoto.

Comando utilizado:

```bash
git merge --abort
```

---

## 2. Problema de navegación del historial médico

Al implementar el RF11 se presentó un problema debido a la duplicación de la opción del historial médico dentro del menú lateral.

Se revisaron los siguientes archivos:

```text
MainActivity.kt
Screen.kt
AppNavigation.kt
```

Finalmente, se configuró correctamente la navegación hacia:

```text
Screen.MedicalHistory.route
```

utilizando el `NavController`.


---

# Estructura general del proyecto

```text
ClínicaSalud/
│
├── app/
│   └── src/
│       └── main/
│           └── java/
│               └── ...
│                   ├── navigation/
│                   ├── screens/
│                   │   ├── MisCitasScreen.kt
│                   │   ├── ProfileScreen.kt
│                   │   └── MedicalHistoryScreen.kt
│                   │
│                   ├── MainActivity.kt
│                   └── ...
│
├── docs/
│   └── images/
│       ├── rf9_cancelacion.png
│       ├── rf10_perfil.png
│       ├── rf11_historial.png
│       └── rf11_historial_detalle.png
│
└── README.md
```


Las funcionalidades fueron desarrolladas manteniendo la estructura existente del proyecto y utilizando componentes de **Jetpack Compose** y **Material Design 3**.
