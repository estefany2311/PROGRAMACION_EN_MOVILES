# MuroCampus

Laboratorio 9 **Servicios Web y plataforma REST**.

MuroCampus es una app Android que consume el servicio REST de
[JSONPlaceholder](https://jsonplaceholder.typicode.com/). Lista publicaciones, muestra el
detalle de cada una con sus comentarios, filtra por usuario y crea una publicación nueva.
Si no hay internet o el servidor falla, la app no se cierra: muestra un mensaje y un botón
**Reintentar**.

**Tecnologías:** Kotlin, Jetpack Compose con Material 3, Retrofit, OkHttp,
kotlinx.serialization, corrutinas y MVVM + Repository.

## Endpoints usados

| Para...               | Verbo y ruta              | Función de `PostApi` |
|-----------------------|---------------------------|----------------------|
| Listar publicaciones  | `GET posts`               | `listar()`           |
| Ver una publicación   | `GET posts/{id}`          | `detalle(id)`        |
| Ver sus comentarios   | `GET posts/{id}/comments` | `comentarios(id)`    |
| Filtrar por usuario   | `GET posts?userId=N`      | `porUsuario(userId)` |
| Crear una publicación | `POST posts` (JSON)       | `crear(nuevo)`       |

## Estructura de paquetes

```
com.chavez.murocampus/
├── MainActivity.kt              Scaffold, navegación con estado y armado del ViewModel
├── model/
│   └── Modelos.kt               Post, Comentario, NuevoPost (@Serializable)
├── data/
│   ├── remote/
│   │   ├── PostApi.kt           Interface de Retrofit (@GET, @POST, @Path, @Query, @Body)
│   │   └── RetrofitClient.kt    Retrofit + OkHttp + conversor JSON (se crea una sola vez)
│   ├── PostRepository.kt        Interface + PostRepositoryRemote (devuelve Result)
│   └── MensajeDeError.kt        mensajeDe(): convierte la excepción en texto
├── viewmodel/
│   ├── UiState.kt               Cargando / Exito / Error
│   ├── PostsViewModel.kt        Lista, detalle, filtro y crear
│   └── PostsViewModelFactory.kt Crea el ViewModel con su Repository
└── ui/
    ├── Componentes.kt           TarjetaPost, Cargando, ErrorConReintento
    ├── PantallaLista.kt         Chips de filtro + lista de publicaciones
    ├── PantallaDetalle.kt       Publicación completa + comentarios
    ├── PantallaCrear.kt         Formulario y resultado del POST
    └── theme/                   Colores (azul), tipografía y tema
```

## Arquitectura MVVM + Repository

| Capa                | Conoce a                              | No debe                                       |
|---------------------|---------------------------------------|-----------------------------------------------|
| View (`ui/`)        | Solo a su ViewModel                   | Llamar a Retrofit ni conocer PostApi          |
| ViewModel           | Solo a la interface `PostRepository`  | Usar Retrofit, PostApi, HttpException, Context |
| Repository (`data/`) | `PostApi` y las excepciones de red   | Conocer la UI ni dejar escapar excepciones    |
| `data/remote`       | Retrofit, OkHttp y el JSON            | Guardar estado de pantalla                    |

## Capturas

| Lista | Detalle | Filtro | Crear | Error de conexión |
|-------|---------|--------|-------|-------------------|
|  |  |  |  |  |

## Para pensar

**1. ¿Qué diferencia hay entre GET y POST? ¿Por qué crear lleva `@Body` y porUsuario lleva `@Query`?**

GET pide datos y POST envía datos nuevos para crear algo. crear usa @Body para mandar el objeto completo en el cuerpo de la petición, mientras porUsuario usa @Query porque solo agrega un filtro simple al final de la URL (posts?userId=2).

**2. ¿Por qué la ruta es `"posts"` y no `"/posts"`?**

Porque la URL base ya termina con /. Si pongo "/posts", Retrofit reemplaza la ruta anterior en vez de pegarla a continuación.

**3. ¿Por qué el Repository devuelve `Result` en vez de dejar que la excepción llegue al ViewModel?**

Para mantener limpio el ViewModel sin que sepa nada de Retrofit o errores de red; el Repository atrapa cualquier fallo y solo le avisa si todo salió bien o mal.

**4. ¿Qué pasa si olvidas el permiso INTERNET? ¿Y si olvidas `ignoreUnknownKeys` y el servidor agrega un campo nuevo?**

Sin INTERNET, Android bloquea la conexión y siempre saldrá error de red. Sin ignoreUnknownKeys, la app fallará con un error al no saber qué hacer con ese campo nuevo del JSON.

**5. ¿Por qué la pantalla de lista usa un `when` sobre `UiState` y no un `if` con tres variables?**

Porque UiState garantiza un solo estado a la vez (cargando, éxito o error), evitando que con varios if se muestren dos estados al mismo tiempo por accidente.
**6. JSONPlaceholder no guarda los POST. ¿Cómo cambiaría tu app si usaras un servicio real?**

El post sí se guardaría de verdad, así que tendría que recargar la lista al volver para verlo y usar un inicio de sesión real en lugar de poner userId = 1 fijo.
