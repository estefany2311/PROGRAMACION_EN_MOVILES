# AvisosCampus

Laboratorio 15 · Programación en Móviles · **Notificaciones en Android**

## Descripción

AvisosCampus es una app Android que muestra notificaciones locales y escucha lo que pasa en el celular:

- **Avisos:** el botón *Mostrar aviso* publica la notificación "Tarea lista".
- **Recordatorio:** *Recordar en 10 s* inicia una cuenta regresiva con corrutinas (cancelable) y al llegar a 0 muestra "Recordatorio".
- **Receptor de anuncios:** con el switch *Escuchar el modo avión*, un `BroadcastReceiver` avisa cuando el modo avión se activa o desactiva.
- **Historial:** todos los avisos quedan anotados, con lo más reciente arriba, su origen y su hora.
- **Permiso:** pide `POST_NOTIFICATIONS` (Android 13+), muestra su estado y, si se niega, avisa y ofrece *Abrir ajustes* sin cerrar la app.

Tecnologías: Kotlin, Jetpack Compose, Material 3, notificaciones locales (`NotificationCompat`), `BroadcastReceiver`, corrutinas y MVVM + Repository. Sin internet y solo con datos ficticios.

## Estructura de paquetes

```
com.chavez.avisoscampus/
├── AvisosApp.kt                  Application: crea el canal "avisos"
├── MainActivity.kt               Obtiene el ViewModel con la factory y muestra la pantalla
├── model/
│   └── Modelos.kt                Aviso, Origen, PermisoUi
├── data/
│   └── AvisosRepository.kt       interface AvisosRepository + AvisosRepositoryMemoria
├── notificacion/
│   └── Notificador.kt            crearCanal, tienePermiso y mostrar (con PendingIntent)
├── receiver/
│   └── ModoAvionReceiver.kt      BroadcastReceiver de ACTION_AIRPLANE_MODE_CHANGED
├── viewmodel/
│   ├── AvisosViewModel.kt        Estado (avisos, permiso, faltan, escuchandoAvion, mensaje) y eventos
│   └── AvisosViewModelFactory.kt
└── ui/
    ├── PantallaAvisos.kt         Tarjetas: permiso, avisos, recordatorio y receptor
    ├── HistorialAvisos.kt        Tarjeta Historial (no conoce el ViewModel)
    └── theme/                    Tema Material 3 en azul
```

## Arquitectura MVVM

```
View (PantallaAvisos, HistorialAvisos)
   |  eventos: botones, switch                 ^  collectAsState()
   v                                           |
ViewModel (AvisosViewModel) --- StateFlow (avisos, permiso, faltan, escuchandoAvion, mensaje) --->
   |  registra el aviso                        |  emite: eventos (SharedFlow<Aviso>)
   v                                           v
AvisosRepository (interface)              La View recibe el evento y llama a
   -> AvisosRepositoryMemoria             Notificador.mostrar(context, aviso)
                                               |
                                               v
                                   Sistema Android (canal, NotificationManagerCompat)

ModoAvionReceiver --(activo)--> View --> vm.alCambiarModoAvion(activo)
```

| Capa | Conoce a | No debe |
|------|----------|---------|
| View (`ui/`) | Su ViewModel y el Notificador | Decidir qué se avisa ni guardar el historial |
| ViewModel | Solo la interface del Repository | Usar Context, NotificationManager ni BroadcastReceiver |
| Repository | Nada de la UI | Usar Context |
| Notificador | El sistema (canal, NotificationManager) | Guardar estado de la app |

## Capturas

| 1. Notificación en el panel |
|:---:|
| <img src="capturas/01-notificacion.png" width="260" alt="Notificación en el panel"> |

| 2. Recordatorio con cuenta regresiva |
|:---:|
| <img src="capturas/02-recordatorio.png" width="260" alt="Recordatorio con cuenta regresiva"> |

| 3. Aviso del modo avión |
|:---:|
| <img src="capturas/03-modo-avion.png" width="260" alt="Aviso del modo avión"> |

| 4. Historial de avisos |
|:---:|
| <img src="capturas/04-historial.png" width="260" alt="Historial de avisos"> |

| 5. Permiso denegado con mensaje y *Abrir ajustes* |
|:---:|
| <img src="capturas/05-permiso-denegado.png" width="260" alt="Permiso denegado con mensaje y Abrir ajustes"> |

## Para pensar

**1. ¿Por qué una notificación necesita un canal y un permiso? ¿Qué pasa si falta cada uno?**

El canal (Android 8+) agrupa las notificaciones y define cómo se muestran, y el usuario puede configurarlo en Ajustes. El permiso (Android 13+) es la autorización del usuario para recibir avisos. Si falta el canal, Android descarta la notificación y no aparece. Si falta el permiso, tampoco aparece: en mi app el aviso se guarda en el historial, pero se muestra un mensaje de error.

**2. ¿Por qué el ViewModel emite un evento y no muestra la notificación él mismo?**

Porque el ViewModel no debe tener `Context` ni tocar el sistema. Él solo decide qué avisar y lo anota en el Repository; la pantalla, que sí tiene `Context`, recibe el evento y llama al `Notificador`. Así se respeta MVVM y el ViewModel se puede probar sin Android.

**3. ¿Qué pasa si usas el mismo id en dos notificaciones?**

La segunda reemplaza a la primera y solo se ve una. Por eso uso `aviso.id`, que es distinto en cada aviso, y así aparecen dos notificaciones.

**4. ¿Por qué onReceive debe ser corto y por qué hay que quitar el receptor al salir?**

`onReceive` corre en el hilo principal y dispone de poco tiempo. Si hace un trabajo largo, la app se congela y Android puede mostrar el error "no responde". El receptor se quita en `onDispose` con `unregisterReceiver` para evitar fugas de memoria y para que no siga escuchando cuando la pantalla ya no existe.

**5. ¿En qué casos usarías una notificación local y en cuáles una push de Firebase?**

La local sirve cuando el aviso lo genera la propia app o el celular, sin internet: recordatorios, cuentas regresivas o cambios del sistema como el modo avión. La push de Firebase sirve cuando el mensaje viene de un servidor, como noticias, mensajes de otras personas o avisos de la universidad, incluso con la app cerrada.