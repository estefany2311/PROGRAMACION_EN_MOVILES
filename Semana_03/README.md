# Laboratorio 03: Registro de Producto

**Estudiante:** Karla  
**Curso:** Programación en Móviles  

## Descripción
Aplicación en Android Studio desarrollada con Jetpack Compose para registrar un producto (Nombre, Precio y Cantidad), calculando el importe total en una tarjeta de resumen.

## Capturas de Pantalla

### Parte A: Registro Base
<p align="center">
  <img src="https://github.com/user-attachments/assets/58ef78bc-faf4-49a5-84f5-da09cf86ca13" width="280" alt="Pantalla Vacía" />
  &nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/e1a4c454-9a16-45ee-954d-c64335fee216" width="280" alt="Producto Registrado" />
</p>

### Parte B: Mejora con IA (Validación y Botón Limpiar)
<p align="center">
  <img src="https://github.com/user-attachments/assets/ff2b3025-8da8-4e0a-94d3-09237c67081f" width="280" alt="Parte B - Validación y Botón Limpiar" />
</p>


## Mejora con IA

| Prompt que usé | Qué generó Gemini | Qué acepté o corregí (y por qué) |
| :--- | :--- | :--- |
| En PantallaRegistro de mi proyecto Jetpack Compose, agrega una validación de campos vacíos para que, al presionar el botón AGREGAR, se muestre un mensaje de error en texto rojo si falta algún dato. Además, agrega un botón Limpiar. | Generó las variables de estado mensajeError, la condición de campos vacíos dentro de AGREGAR y un botón OutlinedButton para vaciar las variables. | Acepté las variables de estado y la estructura básica del botón. Corregí el manejo de validaciones para comprobar que Precio y Cantidad sean valores numéricos reales , y personalicé el texto del mensaje de error y la etiqueta del botón Limpiar para ofrecer mayor claridad al usuario. |
