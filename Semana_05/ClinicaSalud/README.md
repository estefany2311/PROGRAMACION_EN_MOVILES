# Clínica Salud+ — Fase 2 (Fase 2)

## Descripción del Proyecto

**Clínica Salud+** es una aplicación móvil desarrollada en Android con Jetpack Compose y Material Design 3. En esta segunda fase del proyecto, se expandió la funcionalidad del prototipo base mediante la implementación de cuatro nuevos requerimientos funcionales (**RF9 - RF11**), apoyados en herramientas de IA (Gemini Agent) y manteniendo un diseño limpio.

---

##  Nuevos Requerimientos (Fase 2)

| ID | Requerimiento Funcional | Descripción de la Funcionalidad |
| :--- | :--- | :--- |
| **RF9** | **Cancelación de Citas** | Implementación de diálogo de confirmación (`AlertDialog`) en la pantalla *Mis Citas* para confirmar y remover citas agendadas. |
| **RF10** | **Perfil de Usuario y Edición** | Pantalla de perfil con avatar, información del usuario, switch interactivo de notificaciones y diálogo modal para actualizar el teléfono. |
| **RF11** | **Historial Médico y Navegación** | Pantalla con resumen de salud del paciente y detalle de consultas pasadas. Integración completa al `ModalNavigationDrawer` de la app. |


---

## Capturas de Pantalla

| RF9: Cancelación de Cita | RF10: Perfil y Edición | RF11: Historial Médico | 
| :---: | :---: | :---: | 
| <img src="https://github.com/user-attachments/assets/0cc0b4e0-0979-40fd-b388-f63bbbcff82c" width="220" /> | <img src="https://github.com/user-attachments/assets/8e9fef50-8f6d-48bf-91ea-dc0ad211e320" width="220" /> | <img src="https://github.com/user-attachments/assets/581fec6d-c831-4b18-9ac2-644b626a6692" width="220" /> | 

##  Prompts Utilizados (Gemini Agent)

###  RF9 — Cancelación de Citas
> *"Implementa en `MyAppointmentsScreen.kt` la funcionalidad para cancelar citas. Agrega un botón 'Cancelar Cita' a cada tarjeta. Al presionar, muestra un `AlertDialog` de confirmación M3. Al confirmar la acción, remueve la cita de la lista reactiva."*

###  RF10 — Perfil de Usuario con Edición
> *"Implementa la interfaz completa para `ProfileScreen.kt` en Jetpack Compose con Material 3. En la parte superior debe mostrar un avatar circular con las iniciales 'KC', el nombre 'Karla Chavez' y el rol 'Paciente'. Agrega tarjetas (`Card`) M3 estilizadas con bordes redondeados de `16.dp` para la información personal (Correo, Teléfono, Tipo de Sangre) y un `Switch` interactivo para 'Notificaciones de recordatorio'. Incluye un botón 'Editar Datos' que al presionar abra un diálogo sencillo (`AlertDialog`) para modificar el teléfono del usuario."*

###  RF11 — Historial Médico
> *"Diseña e implementa la interfaz completa para la pantalla de Historial Médico en Jetpack Compose con Material 3. En la parte superior incluye una tarjeta principal con el resumen de salud del paciente (Alergias: Ninguna, Tipo de Sangre: O+, Padecimientos: Ninguno). Debajo, muestra una lista de consultas médicas pasadas organizadas en tarjetas (`Card`) con bordes redondeados (`16.dp`). Cada tarjeta de consulta debe incluir: Fecha de atención, Nombre del médico, Especialidad, Diagnóstico y Medicamento recetado."*

---

##  Correcciones y Desafíos Durante el Proceso

Durante la fase de integración surgieron algunos inconvenientes técnicos que fueron resueltos manualmente y documentados:

1. **Conflicto de Estado en Git (`Merging CON-IA`):**
   * **Inconveniente:** Quedó un estado de *merge* inconcluso en la terminal al subir cambios.
   * **Solución:** Se abortó el proceso inconcluso mediante `git merge --abort` y se forzó la actualización sincronizada de la rama remota `CON-IA`.

2. **Ruta y Redirección en el Menú Lateral (`ModalNavigationDrawer`):**
   * **Inconveniente:** El botón "Historial médico" no navegaba a la pantalla y generaba un elemento duplicado en el menú.
   * **Solución:** Se limpiaron los ítems duplicados en `MainActivity.kt`, asegurando la llamada a `navController.navigate(Screen.MedicalHistory.route)` en el evento `onClick`, y se registró correctamente la pantalla en `Screen.kt` y `AppNavigation.kt`.
---

##  Convención de Commits Utilizada

Todos los cambios fueron documentados y subidos a la rama `CON-IA`:

* `feat(ui): implementar cancelacion de citas con AlertDialog (RF9)`
* `feat(ui): implementar pantalla de perfil de usuario con edicion de datos (RF10)`
* `feat(ui): implementar pantalla de historial medico y antecedentes (RF11)`

