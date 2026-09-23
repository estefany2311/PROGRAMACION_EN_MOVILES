# Clínica Salud+ - Aplicación Móvil (Fase 1)

##  Descripción del Proyecto
**Clínica Salud+** es una aplicación móvil desarrollada en Android utilizando **Jetpack Compose** y **Material Design 3**. La aplicación permite a los pacientes explorar la lista de médicos disponibles, filtrar por especialidades, ver detalles del perfil médico, agendar citas seleccionando fecha y hora, y consultar su historial con estados diferenciados.

El proyecto está construido bajo el paquete `com.chavez.clinicasalud` con una estructura modular, limpia.

---

##  Información del Estudiante y Curso
* **Curso:** Desarrollo de Aplicaciones Móviles
* **Alumno:** Karla Chavez Lazo

---

##  Estructura del Proyecto

* **com.chavez.clinicasalud**
  * **data/**
    * `Models.kt`: Modelos de datos (Medico, Cita) y fuente DatosPrueba.
  * **navigation/**
    * `AppNavigation.kt`: NavHost y declaración de rutas del flujo.
    * `Screen.kt`: Definición de pantallas y rutas parametrizadas.
  * **screens/**
    * `HomeScreen.kt`: Pantalla principal con filtros y lista de médicos.
    * `DoctorDetailScreen.kt`: Detalle y biografía del médico.
    * `BookAppointmentScreen.kt`: Selección única de fecha y hora para la cita.
    * `ConfirmationScreen.kt`: Confirmación de reserva y acceso a citas.
    * `MyAppointmentsScreen.kt`: Lista de citas con chips de estado.
  * **ui/theme/**: Colores, tipografía y tema visual M3.
  * `MainActivity.kt`: Punto de entrada y contenedor del ModalNavigationDrawer.

---

##  Requerimientos Funcionales (RF)

| ID | Requerimiento | Archivo / Componente | Descripción Directa |
| :--- | :--- | :--- | :--- |
| **RF1** | Filtrar médicos | `screens/HomeScreen.kt` | Presionar un chip de especialidad filtra la lista en pantalla. |
| **RF2** | Listar médicos | `screens/HomeScreen.kt` | Muestra TopBar morada  y lista de médicos con avatar y calificación . |
| **RF3** | Ver perfil | `screens/DoctorDetailScreen.kt` | Muestra datos del médico (biografía, experiencia) y el botón "Agendar cita". |
| **RF4** | Agendar cita | `screens/BookAppointmentScreen.kt` | Permite seleccionar solo una fecha y una hora en chips morados. |
| **RF5** | Confirmar cita | `screens/ConfirmationScreen.kt` | Muestra el check verde ✓ con la confirmación de la fecha, hora y médico. |
| **RF6** | Menú lateral (Drawer) | `navigation/AppNavigation.kt`<br>`MainActivity.kt` | Despliega el menú con la cabecera y accesos a la app. |
| **RF7** | Listar mis citas | `screens/MyAppointmentsScreen.kt` | Muestra las citas con chip verde ("Confirmada") o gris ("Completada"). |
| **RF8** | Ir a citas desde confirmación | `screens/ConfirmationScreen.kt` | El botón gris "Ver mis citas" lleva a la lista de citas agendadas. |

---

##  Capturas de Pantalla

| Menú Lateral (Drawer) | Inicio (HomeScreen) | Perfil del Médico |
| :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/327fbc35-7c1e-4ca7-90bc-e608f51145b2" width="220" /> | <img src="https://github.com/user-attachments/assets/c563ca1b-f2ff-44b2-8d97-e792ecce5c0e" width="220" /> | <img src="https://github.com/user-attachments/assets/425854af-a5a3-43c9-a2e0-e819d3df599c" width="220" /> |

| Agendar Cita | Confirmación de Cita | Mis Citas |
| :---: | :---: | :---: |
| <img src="https://github.com/user-attachments/assets/a70e6d13-3295-418e-bdc8-6142b4b80eee" width="220" /> | <img src="https://github.com/user-attachments/assets/e88bf478-c02b-4618-86d3-ccb76276ba92" width="220" /> | <img src="https://github.com/user-attachments/assets/8130e25b-840d-4827-8778-a37bcf3cac9e" width="220" /> |
