
## Fase 2: mejoras asistidas por IA (rama `CON-IA`)

Se implementaron 3 mejoras sin romper la arquitectura MVVM + Repository. La primera cambia
la API y el Repository, como pide el laboratorio. El detalle de cada prompt, lo que se
cambió y por qué está en [PROMPTS.md](PROMPTS.md).

| # | Mejora | Qué hace | Capas que cambian |
|---|--------|----------|-------------------|
| 1 | Borrar publicación | Botón **Eliminar** en el detalle con confirmación; envía `DELETE posts/{id}` y revisa el código con `Response<Unit>` | API + Repository + ViewModel + View |
| 2 | Búsqueda | Campo **Buscar por título** que filtra las publicaciones ya descargadas, sin llamar a la red | ViewModel + View |
| 3 | Encabezado con interceptor | Un `Interceptor` de OkHttp agrega `Accept: application/json` a todas las peticiones | RetrofitClient |

### Cómo funciona cada mejora

- **Borrar:**
  - Con `Response<Unit>`, Retrofit no lanza excepción si el servidor responde 404 o 500. Por eso el Repository revisa `isSuccessful` y, si falla, lo convierte en `Result.failure`.
  - El ViewModel solo recibe `Result<Unit>`: nunca ve `Response` ni `HttpException`.
  - Como JSONPlaceholder no borra de verdad, la publicación se quita de la lista en memoria.
- **Búsqueda:**
  - El ViewModel combina la lista y el texto con `combine` + `stateIn` para producir `listaVisible`. La búsqueda ignora mayúsculas y los espacios de los extremos.
  - Se aplica sobre el filtro de usuario activo y sobrevive a la rotación.
- **Interceptor:**
  - Se registra antes del `HttpLoggingInterceptor`, así Logcat muestra cada petición ya con el encabezado.
  - Ninguna otra capa se entera del cambio.

### Reglas que se mantuvieron

- `viewmodel/` no contiene Retrofit, `PostApi`, `HttpException`, `Response` ni `Context`.
- Las pantallas solo hablan con `PostsViewModel`, nunca con la API.
- El Repository siempre devuelve `Result` y relanza `CancellationException` antes de capturar `Exception`.
- Sin `GlobalScope`: todas las corrutinas usan `viewModelScope`.

### Capturas de la Fase 2

| Eliminar (confirmación) | Búsqueda | Logcat con `Accept` |
|-------------------------|----------|---------------------|
|  |  |  |
