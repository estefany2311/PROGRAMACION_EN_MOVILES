# TecsupFit - Aplicación Móvil de Gimnasio(FASE 1)

## Descripción del Proyecto

**TecsupFit** es una aplicación móvil desarrollada en Android utilizando **Jetpack Compose** y **Material Design 3**. La aplicación permite a los usuarios explorar las clases de gimnasio disponibles, filtrar por días, consultar detalles y cupos de cada disciplina, reservar un cupo y revisar el historial de sus reservas y su perfil.

El proyecto está construido bajo el paquete `com.chavez.tecsupfit` con una estructura modular y limpia.

---

## Información del Estudiante y Curso

* **Curso:** Desarrollo de Aplicaciones Móviles
* Alumno: Karla Chavez Lazo 

---

## Estructura del Proyecto

* **com.chavez.tecsupfit**
  * `data/`
    * `DatosPruebaFit.kt` : Modelos de datos (`ClaseGym`) y lista de datos simulados.
  * `navigation/`
    * `AppNavigation.kt` : NavHost, barra de navegación inferior (`NavigationBar`) y gestión de rutas.
    * `Screen.kt` : Definición de pantallas y rutas parametrizadas.
  * `screens/`
    * `HomeScreen.kt` : Pantalla principal con filtros (`LazyRow`) y lista de clases (`LazyColumn`).
    * `ClassDetailScreen.kt` : Detalle de la clase, información de cupos y botón para reservar.
    * `ConfirmationScreen.kt` : Confirmación de la reserva realizada.
    * `ReservationsScreen.kt` : Historial de reservas con estados (Confirmada / Completada).
    * `RoutinesScreen.kt` : Sección de rutinas asignadas.
    * `ProfileScreen.kt` : Perfil del usuario con avatar, plan actual y estadísticas.
  * `ui/theme/` : Configuración de colores, tipografía y tema visual M3.
  * `MainActivity.kt` : Punto de entrada y contenedor principal de la aplicación.

---
## Requerimientos Funcionales (RF)

| ID | Requerimiento | Archivo / Componente | Descripción Directa |
| :---: | :--- | :--- | :--- |
| **RF01** | Mostrar lista de clases y filtros | `HomeScreen.kt` | Muestra los botones de filtro ("Hoy", "Esta semana") en una lista horizontal y la lista de clases de gimnasio en una columna desplegable. |
| **RF02** | Ver detalle de una clase | `ClassDetailScreen.kt` | Muestra la información de la clase seleccionada (nombre, horario, sala, descripción y cupos disponibles) con un botón para reservar. |
| **RF03** | Confirmar la reserva | `ConfirmationScreen.kt` | Muestra un mensaje de confirmación con el check verde y el resumen de la clase reservada. |
| **RF04** | Listar mis reservas | `ReservationsScreen.kt` | Muestra la lista de clases reservadas indicando si están "Confirmadas" o "Completadas". |
| **RF05** | Visualizar perfil de usuario | `ProfileScreen.kt` | Muestra el nombre del usuario, su tipo de plan y el contador de clases y rachas. |
| **RF06** | Navegación entre pantallas | `AppNavigation.kt` | Permite cambiar entre las pantallas del menú inferior (Inicio, Reservas, Rutinas y Perfil) y navegar al detalle de las clases. |

## Capturas de Pantalla

| Inicio (HomeScreen) | Detalle de Clase | Confirmación de Reserva |
| :---: | :---: | :---: |
| <img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/fd45d926-e22b-416f-a6d1-06461aec439d" />|<img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/5e0a6185-5d5a-41bc-8315-1b64d2ae0141" />| <img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/33743cb0-f53a-40c1-a91f-d841625a234e" />
|

| Mis Reservas | Mis Rutinas | Mi Perfil |
| :---: | :---: | :---: |
| <img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/b2f2a961-1463-4f98-aeb1-f1ab7e37d824" />| <img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/8e586b5e-b9ca-44a2-9b5e-9985f72684f5" />| <img width="738" height="1600" alt="image" src="https://github.com/user-attachments/assets/ece14e22-4cc3-4de5-89a7-e3c1c2f746b8" />
