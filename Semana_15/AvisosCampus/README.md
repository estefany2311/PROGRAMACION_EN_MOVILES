# AvisosCampus · Fase 2 

Laboratorio 15 · Programación en Móviles · **Notificaciones en Android** · Rama `CON-IA`

## Descripción

En esta fase se mejoró AvisosCampus con ayuda de IA, sin romper la arquitectura MVVM + Repository. Se eligieron 3 mejoras de la guía:

| # | Mejora | Qué logra | Capas que cambian |
|---|--------|-----------|-------------------|
| 1 | **Dos canales** | Canal **Urgentes** (`IMPORTANCE_HIGH`, aparece como ventana emergente) además de **Avisos**. El recordatorio usa el canal urgente. | Modelo, Repository, ViewModel y Notificador |
| 2 | **Texto largo** | Al expandir la notificación se ve el texto completo con `BigTextStyle`. | Modelo, Repository, ViewModel y Notificador |
| 3 | **Receptor del cargador** | Con el switch *Escuchar el cargador*, avisa cuando se conecta o desconecta el cable (`ACTION_POWER_CONNECTED` / `ACTION_POWER_DISCONNECTED`). | Receiver, Modelo, ViewModel y View |


## Cómo funciona cada mejora

### 1. Dos canales
- `model/Modelos.kt`: nuevo `enum class Prioridad { NORMAL, URGENTE }` y el campo `prioridad` en `Aviso` (por defecto `NORMAL`).
- `viewmodel/AvisosViewModel.kt`: el recordatorio se registra como `Prioridad.URGENTE`. **El ViewModel decide** qué es urgente, sin usar `Context`.
- `notificacion/Notificador.kt`: `crearCanal` crea los canales `avisos` y `urgentes`, y `mostrar()` elige el canal y la prioridad según `aviso.prioridad`.

### 2. Texto largo
- `model/Modelos.kt`: nuevo campo opcional `detalle` en `Aviso`.
- `viewmodel/AvisosViewModel.kt`: "Tarea lista" y "Recordatorio" llevan un detalle largo. "Modo avión" y "Cargador" no.
- `notificacion/Notificador.kt`: `setStyle(BigTextStyle().bigText(aviso.detalle ?: aviso.texto))`. Cerrada, la notificación muestra el texto corto; expandida, muestra el detalle. El historial sigue mostrando el texto corto.

### 3. Receptor del cargador
- `receiver/CargadorReceiver.kt` (nuevo): `onReceive` corto que convierte la acción en `true` (conectado) o `false` (desconectado).
- `viewmodel/AvisosViewModel.kt`: `escuchandoCargador`, `cambiarEscuchaCargador(valor)` y `alCambiarCargador(conectado)`, que registra "Cargador · Conectado/Desconectado" y emite el evento.
- `ui/PantallaAvisos.kt`: segundo `DisposableEffect(escuchandoCargador)` con `ContextCompat.registerReceiver(..., RECEIVER_NOT_EXPORTED)` y `unregisterReceiver` en `onDispose`. El switch está en la tarjeta *Receptor de anuncios*.
- `ui/HistorialAvisos.kt`: etiqueta de color para el origen "Cargador".

## Estructura de paquetes (archivos que cambiaron en la Fase 2)

```
com.chavez.avisoscampus/
├── model/
│   └── Modelos.kt                + Prioridad, Origen.CARGADOR, Aviso.prioridad y Aviso.detalle
├── data/
│   └── AvisosRepository.kt       registrar(..., prioridad, detalle) con valores por defecto
├── notificacion/
│   └── Notificador.kt            + canal "urgentes", elección de canal y BigTextStyle
├── receiver/
│   └── CargadorReceiver.kt       NUEVO: ACTION_POWER_CONNECTED / DISCONNECTED
├── viewmodel/
│   └── AvisosViewModel.kt        + prioridad del recordatorio, detalles y escucha del cargador
└── ui/
    ├── PantallaAvisos.kt         + switch "Escuchar el cargador" y su DisposableEffect
    └── HistorialAvisos.kt        + etiqueta del origen Cargador
```

## Arquitectura después de las mejoras

```
CargadorReceiver --(conectado)--> View --> vm.alCambiarCargador(conectado)
                                                 |
                                                 v
ViewModel: decide el aviso (titulo, texto, detalle, prioridad)
   |  repo.registrar(...)                    |  emite: eventos (SharedFlow<Aviso>)
   v                                         v
AvisosRepository (interface)            View -> Notificador.mostrar(context, aviso)
                                                 |
                                                 v
                          URGENTE -> canal "urgentes"  |  NORMAL -> canal "avisos"
                          + BigTextStyle con aviso.detalle
```

Reglas que se mantienen después de usar la IA:
- `viewmodel/` no tiene `Context`, `NotificationManager`, `BroadcastReceiver`, `Intent` ni imports de `android.content` o `android.app`.
- Todo receptor registrado (`ModoAvionReceiver` y `CargadorReceiver`) se quita en `onDispose`.
- El `PendingIntent` sigue siendo `FLAG_IMMUTABLE`.

## Cómo probar

1. Desinstala la app anterior e instala esta versión, para que el canal **Urgentes** se cree con importancia alta.
2. **Dos canales:** pulsa *Recordar en 10 s* y, al llegar a 0, el aviso aparece como ventana emergente. *Mostrar aviso* sale solo en el panel.
3. **Texto largo:** pulsa *Mostrar aviso* y expande la notificación para ver el texto completo.
4. **Cargador:** activa *Escuchar el cargador* y conecta o desconecta el cable. Aparece "Cargador · Conectado/Desconectado" y queda en el historial. Con el switch apagado, no hay aviso.

## Capturas

| 1. Recordatorio en el canal Urgentes (ventana emergente) |
|:---:|
| <img src="capturas/fase2-01-urgente.png" width="260" alt="Recordatorio como ventana emergente"> |

| 2. Notificación expandida con texto largo |
|:---:|
| <img src="capturas/fase2-02-texto-largo.png" width="260" alt="Notificación expandida con BigTextStyle"> |

| 3. Aviso del cargador |
|:---:|
| <img src="capturas/fase2-03-cargador.png" width="260" alt="Aviso del cargador conectado"> |

| 4. Historial con avisos del cargador |
|:---:|
| <img src="capturas/fase2-04-historial.png" width="260" alt="Historial con avisos del cargador"> |
