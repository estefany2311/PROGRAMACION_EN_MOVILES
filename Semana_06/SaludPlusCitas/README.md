# Clínica SaludPlus · App Paciente (Fase 1)

**Curso:** Programación en Móviles · 4to ciclo  
**Alumna:** Karla Estefany Chavez Lazo  
**Docente:** Juan José León Suiyon  
**Rama:** `SIN-IA` · **Paquete:** `com.saludplus.citas`

## Descripción
App móvil (Jetpack Compose) para agendar citas médicas: registro e inicio de sesión, elección de especialidad y médico, selección de fecha y hora, confirmación y consulta de citas. No usa base de datos: los datos viven en memoria dentro del objeto `Repositorio`.

## Punto de partida
El archivo `SaludPlusCitas.zip` no estuvo disponible. La **base** (modelos, rutas, navegación, tema y esqueletos) se reconstruyó desde cero.

## Estructura del Proyecto

```text
com.saludplus.citas
├── MainActivity.kt
├── data
│   ├── model         → Usuario, Especialidad, Medico, Cita
│   └── repository    → Repositorio (object con mutableStateListOf)
├── navigation        → Rutas.kt, AppNavigation.kt
└── ui
    ├── theme         → Color.kt, Theme.kt, Type.kt
    ├── components    → Componentes reutilizables (Botones, Campos, Barras)
    └── screens       → auth, home, agendamiento, citas, perfil, resultados, notificaciones
```
## Lo implementado

**Repositorio** con colecciones en memoria (`any`, `find`, `filter`, `sortedByDescending`, `take`, `removeIf`).
**Registro, login y sesión** con validaciones.
**Inicio** con saludo, tarjetas, `LazyRow` de especialidades y `NavigationBar` (Inicio, Citas, Resultados, Perfil).
**Flujo de agendamiento:** Especialidades (búsqueda en tiempo real), Médicos, Fecha y hora (`LazyVerticalGrid`), Confirmar cita y Cita agendada (`popUpTo`).
**Mis citas** (con lista vacía) y **Perfil** (cerrar sesión).
**Retos extra:** Detalle de cita, Resultados, Notificaciones y Términos.


## Capturas
## Capturas

| Splash | Registro |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/ce8632f5-0e90-4d78-82f0-2b4f689a4edd" width="220" /> | <img src="https://github.com/user-attachments/assets/4dff628c-e50b-4d23-be74-299abb1913de" width="220" /> |

| Inicio- validación | Especialidades |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/5bf41f89-306f-4a27-9c68-e58dc43e9ee0" width="220" /> | <img src="https://github.com/user-attachments/assets/456ed902-9c2d-4b71-af12-315c01718a51" width="220" /> |

| Fecha y Hora | Confirmar Cita |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/7e8f0f78-3d12-4310-9296-29bb0bde7789" width="220" /> | <img src="https://github.com/user-attachments/assets/9adf18f9-3b0f-4c38-845c-a23da4a1efe2" width="220" /> |

| Mis Citas | Perfil |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/73f59cce-8334-44a1-b0ec-bba1094bb3d0" width="220" /> | <img src="https://github.com/user-attachments/assets/f3e4cbe3-550f-44d1-8122-dc41f40e7ee1" width="220" /> |

## Retos Extra

| Términos | Resultados |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/645fc39b-0381-4d75-8f0a-1d0a64187758" width="220" /> | <img src="https://github.com/user-attachments/assets/a0a71163-b371-4bdf-85ee-c55b9f6f5471" width="220" /> |

| Notificaciones | Cancelar alert |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/572e0e1e-cff1-4cd6-8116-39fe78efa597" width="220" /> | <img src="https://github.com/user-attachments/assets/1eb2f97c-2e6d-41a0-8eef-c59b012b07fd" width="220" /> |



## Commits
6373946 Implementar MisCitasScreen y PerfilScreen con cierre de sesion
f678bda implementar confirmacion, persistencia y pantalla de exito de la cita reservada
002dc0e Implementar seleccion de fechas y horarios disponibles de cada Doctor
d2f832b implementar listados, busqueda de especialidades y medicos
d67ebf4 NavigationBar con 4 destinos (Inicio, Citas, Resultados, Perfil)
2e9ae6a implementar componentes base y pantallas auth con validaciones
6073840 Repositorio: usuarios, especialidades, médicos y citas
577cc3a Agregar base del proyecto (modelos, rutas, navegación)



## Preguntas de reflexión

¿Por qué el Repositorio es un object?
Garantiza una única instancia compartida por toda la app (Singleton). Si fuera una clase normal, cada pantalla tendría listas distintas y no se compartirían las citas ni el bloqueo de horarios.

2. ¿Cómo se actualizan solas las búsquedas y horarios?

Gracias al estado de Jetpack Compose (mutableStateOf y mutableStateListOf). Cuando el estado cambia, Compose re-ejecuta el bloque correspondiente y redibuja la lista automáticamente.

3. ¿Diferencia entre navigate() normal y popUpTo?

El navigate() normal apila pantallas. El uso de popUpTo elimina pantallas del historial para evitar regresar a formularios ya completados o evitar volver al Inicio estando deslogueado.

4. NavigationDrawer vs NavigationBar:

NavigationDrawer sirve para menús extensos u ocultos. NavigationBar es ideal para navegación frecuente de 3 a 5 secciones principales siempre visibles.

## Observaciones

La lista de usuarios empieza vacía: hay que registrarse antes de iniciar sesión. Los datos se pierden al cerrar la app.

## Conclusiones
1. Jetpack Compose simplifica el desarrollo UI mediante el manejo de estados reactivos sin manipular las vistas manualmente.

2. Centralizar la lógica en un Repositorio único mantuvo un flujo de datos limpio y coherente entre todas las pantallas de la aplicación. has que solo la estrutura tenga el fromato lo demas normal 
