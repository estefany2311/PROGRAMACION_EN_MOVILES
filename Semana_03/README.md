# Laboratorio 03: Registro de Producto

**Estudiante:** Karla  
**Curso:** Programación en Móviles  

## Descripción
Aplicación en Android Studio desarrollada con Jetpack Compose para registrar un producto (Nombre, Precio y Cantidad), calculando el importe total en una tarjeta de resumen.

## Capturas de Pantalla

<p align="center">
  <img src="https://github.com/user-attachments/assets/58ef78bc-faf4-49a5-84f5-da09cf86ca13" width="300" alt="Pantalla Vacía" />
  &nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/e1a4c454-9a16-45ee-954d-c64335fee216" width="300" alt="Producto Registrado" />
</p>

## Pregunta 
**¿Qué pasaría si declaras las variables de los campos SIN remember?**  
Si no uso remember, el valor de las variables se borraría en cuanto la pantalla intente actualizarse. En Jetpack Compose, cada vez que escribo una letra se dispara una recomposición, si no le digo al código que "recuerde" lo que acabo de ingresar, la variable vuelve a su estado inicial vacío instantáneamente. En la práctica, la caja de texto se quedaría congelada y nunca se vería lo que intento escribir.
