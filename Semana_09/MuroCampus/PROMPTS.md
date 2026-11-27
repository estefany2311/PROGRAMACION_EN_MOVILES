# PROMPTS.md: Fase 2 de MuroCampus

Mejoras elegidas (mínimo 3, al menos una cambia el Repository o la API):

| # | Mejora | Capas que cambian | Commit |
|---|--------|-------------------|--------|
| 1 | Borrar publicación con `@DELETE` y `Response<Unit>` | API + Repository + ViewModel + View | `feat(borrar): elimina una publicacion con DELETE y Response<Unit>` |
| 2 | Búsqueda por título de lo ya descargado | ViewModel + View | `feat(busqueda): filtra por titulo las publicaciones descargadas` |
| 3 | Interceptor con `Accept: application/json` | RetrofitClient | `feat(red): agrega interceptor que envia Accept application/json` |

## Tabla de prompts

| Prompt que escribí | Qué respondió la IA | Qué cambié o descarté | Por qué |
|--------------------|---------------------|-----------------------|---------|
| "Implementa la mejora Borrar publicación: botón Eliminar en el detalle con `@DELETE("posts/{id}")` que revise el código con `Response<Unit>`, sin romper MVVM." | Agregó `borrar()` en `PostApi` y en el Repository (revisa `isSuccessful` y lanza `HttpException` dentro de `llamar`, que lo vuelve `Result.failure`), un estado `borrado` en el ViewModel y un diálogo de confirmación en el detalle. | Evité que `Response<Unit>` llegara al `PostsViewModel`; dejé que el Repository devuelva `Result<Unit>` y ajusté el estado para que la pantalla regrese a la lista al borrarse con éxito. | Para respetar la regla de oro de MVVM: el ViewModel no debe importar clases de Retrofit ni manejar códigos HTTP directamente. |
| "Implementa la búsqueda por título de las publicaciones ya descargadas." | Usó `combine` + `stateIn` en el ViewModel para crear `listaVisible` a partir de la lista y el texto; agregó el campo "Buscar por título" y el mensaje "Sin resultados". No hace llamadas a la red. | Añadí `.trim()` e `ignoreCase = true` en el filtro de texto, y mantuve la barra de búsqueda oculta o deshabilitada mientras la lista está en estado `Cargando` o `Error`. | Para evitar que espacios accidentales o diferencias entre mayúsculas y minúsculas rompan la búsqueda local. |
| "Implementa el interceptor que agregue `Accept: application/json` a todas las peticiones." | Agregó un `Interceptor` en `RetrofitClient`, registrado antes del `HttpLoggingInterceptor` para verlo en Logcat. | Lo integré directamente en el `OkHttpClient.Builder()` verificando en Logcat que apareciera antes del log del cuerpo (`BODY`). | Si se coloca después del `HttpLoggingInterceptor`, el encabezado agregado no se registra en la consola de depuración. |
| "¿Cómo paso mis cambios a la rama CON-IA?" | Primero sugirió `git stash` + `git merge SIN-IA`. El merge trajo conflictos de otros laboratorios (Semana_08, 12 y 13). Luego propuso `git merge --abort` y traer solo la carpeta con `git checkout SIN-IA -- .`. | Descarté el `git merge` completo y usé `git checkout` apuntando únicamente a la carpeta del proyecto actual. | Así evité mezclar carpetas de otras semanas y mantuve limpio el historial de la rama sin conflictos innecesarios. |
| "Revisa que `PostsViewModel` no tenga ninguna dependencia prohibida tras agregar borrar y búsqueda." | Verificó las importaciones del archivo y confirmó que solo usa clases de Kotlin, Coroutines, `ViewModel` y `PostRepository`. | Limpié un par de importaciones sin uso que habían quedado de pruebas anteriores y verifiqué el manejo de `CancellationException` en el Repository. | Para asegurar que el código cumpla al 100 % con las reglas de desacoplamiento y no se traguen excepciones de corrutinas. |

## Revisión de las reglas

- [x] `viewmodel/` no contiene Retrofit, `PostApi`, `HttpException`, `Response` ni `Context`.
- [x] Ninguna pantalla llama a la API: solo hablan con `PostsViewModel`.
- [x] El Repository nunca deja escapar una excepción: todo devuelve `Result`.
- [x] Cada `catch (e: Exception)` relanza antes `CancellationException`; no hay catch vacíos.
- [x] No se usa `GlobalScope`; las corrutinas van en `viewModelScope`.
- [x] La URL base sigue siendo `https://` y las rutas no empiezan con `/`.

## Conclusión

Usar los prompts aceleró bastante la implementación de las nuevas funcionalidades, pero revisar cada bloque de código antes de aceptarlo fue clave para no romper la arquitectura MVVM. La IA suele resolver rápido mezclando capas si no se le ponen límites claros, por lo que mantener el aislamiento entre `PostApi`, `PostRepository` y `PostsViewModel` garantizó que la aplicación siguiera siendo ordenada, segura ante fallos de red y fácil de mantener.