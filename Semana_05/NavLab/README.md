# Laboratorio N°05: Navegación en Jetpack Compose

* **Estudiante:** Karla Estefany Chavez Lazo
* **Curso:** Programación en Móviles
* **Rama:** `SIN-IA`

---

##  Descripción del Laboratorio
Este proyecto consiste en crear una aplicación Android con **Jetpack Compose** para aprender a navegar entre diferentes pantallas. Usamos una clase especial llamada SCREEN.KT para guardar las rutas de forma organizada y evitar errores al escribir los nombres de las pantallas. Con esto logramos un flujo completo donde el usuario puede ver una lista de elementos, abrir el detalle de cada uno pasando su número de ID y regresar al inicio limpiando el historial de navegación.

---

##  Requerimientos Funcionales

1. **Pantalla de Inicio :** Permite al usuario navegar hacia la lista de elementos o hacia la vista de perfil mediante dos botones principales.
2. **Lista Interactiva :** Muestra una lista desplazable de ítems que, al ser seleccionados, envían su ID correspondiente hacia la pantalla de detalle.
3. **Detalle con Argumentos :** Recibe el ID enviado desde la lista y lo muestra dentro de una tarjeta informativa con una opción para retroceder.
4. **Perfil y Limpieza de Historial :** Muestra la información del usuario y permite retornar al inicio (*back stack*).
---

##  Capturas de Pantalla 

| Pantalla Inicio | Lista de Elementos | Detalle de Elemento | Pantalla Perfil |
| :---: | :---: | :---: | :---: |
| <img width="220" alt="pantallainicio" src="https://github.com/user-attachments/assets/3c0de519-0ac4-4388-9b36-5fcc41d232f0" /> | <img width="220" alt="listaelementos" src="https://github.com/user-attachments/assets/6c080e66-6c9a-4c12-a3a9-6b9776df7a70" /> | <img width="220" alt="detalleelemento" src="https://github.com/user-attachments/assets/cf8643e3-6dbf-4751-814b-afd2114169e7" /> | <img width="220" alt="pantallaperfil" src="https://github.com/user-attachments/assets/308433b6-6f7d-4643-b3c5-c18f29960e96" /> |
