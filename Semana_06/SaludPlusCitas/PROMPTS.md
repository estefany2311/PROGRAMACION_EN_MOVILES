# Registro de Prompts - Fase 2 (CON-IA)

Este documento registra la interacción con Asistentes de Inteligencia Artificial para el desarrollo de la Fase 2 de la aplicación **SaludPlus Citas**.

---

## 1. Módulo de Utilidades de Calendario (`CalendarioUtils.kt`)
* **Objetivo:** Sustituir la lista estática de fechas por un cálculo dinámico de días hábiles omitiendo fines de semana.
* **Prompt utilizado:**
  > "Genera un objeto o archivo de utilidades en Kotlin para Android (Jetpack Compose) utilizando `java.time.LocalDate`. Necesito funciones para:
  > 1. Obtener una lista de 5 días hábiles a partir de la fecha actual o desplazada por semanas (omitir sábados y domingos).
  > 2. Obtener el nombre del día abreviado (ej. LUN, MAR) y el título del mes/año en español.
  > 3. Formatear una cadena de fecha ISO (YYYY-MM-DD) a un formato largo amigable en español (ej. Lunes, 12 de Octubre)."

---

## 2. Refactorización de la Pantalla de Selección (`FechaHoraScreen.kt`)
* **Objetivo:** Conectar el estado de la semana actual con las utilidades de fecha y permitir la navegación entre semanas.
* **Prompt utilizado:**
  > "Tengo la pantalla `FechaHoraScreen` en Jetpack Compose. Ayúdame a refactorizarla para:
  > 1. Agregar un estado `semanaActual` para navegar hacia adelante y atrás mediante botones de flecha en el encabezado.
  > 2. Mostrar una `LazyRow` con las tarjetas de los días hábiles de la semana seleccionada.
  > 3. Al seleccionar un día, actualizar la vista para mostrar la fecha formateada en español usando `formatearFechaLarga` y habilitar los horarios disponibles."

---

## 3. Integración de Formato de Fecha en Español
* **Objetivo:** Mejorar la experiencia de usuario mostrando la fecha seleccionada en formato amigable antes del listado de horas.
* **Prompt utilizado:**
  > "Integra en `FechaHoraScreen.kt` una vista condicional que muestre el texto 'Fecha seleccionada: [Fecha en español]' utilizando `DateTimeFormatter` con localización en español justo antes de los horarios disponibles."
