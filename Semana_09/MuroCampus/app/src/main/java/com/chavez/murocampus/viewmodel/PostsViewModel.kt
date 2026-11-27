package com.chavez.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chavez.murocampus.data.PostRepository
import com.chavez.murocampus.data.mensajeDe
import com.chavez.murocampus.model.Comentario
import com.chavez.murocampus.model.NuevoPost
import com.chavez.murocampus.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Lo que muestra la pantalla de detalle: la publicación y sus comentarios.
data class DetalleUi(
    val post: Post,
    val comentarios: List<Comentario>
)

// Solo conoce la interface PostRepository; nada de la capa de red.
class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista = _lista.asStateFlow()

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle = _detalle.asStateFlow()

    // null significa "Todos".
    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro = _usuarioFiltro.asStateFlow()

    // Resultado del POST: null = aún no se envió.
    private val _creado = MutableStateFlow<UiState<Post>?>(null)
    val creado = _creado.asStateFlow()

    // Si llega una carga nueva antes de que termine la anterior, se cancela la vieja.
    private var trabajoLista: Job? = null
    private var trabajoDetalle: Job? = null

    init { cargar() }

    // Carga la lista respetando el filtro actual (también la usa Reintentar).
    fun cargar() {
        val userId = _usuarioFiltro.value
        trabajoLista?.cancel()
        trabajoLista = viewModelScope.launch {
            _lista.value = UiState.Cargando
            val resultado = if (userId == null) repo.listar() else repo.porUsuario(userId)
            resultado
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    // Guarda el filtro y recarga la lista.
    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    // Envía la publicación nueva. El servicio responde con la publicación creada (id 101).
    fun publicar(titulo: String, texto: String) {
        if (_creado.value is UiState.Cargando) return // evita doble envío
        viewModelScope.launch {
            _creado.value = UiState.Cargando
            repo.crear(NuevoPost(userId = 1, title = titulo, body = texto))
                .onSuccess { _creado.value = UiState.Exito(it) }
                .onFailure { _creado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    // Vuelve el formulario a "aún no se envió" (al abrir Crear o al escribir de nuevo).
    fun reiniciarPublicacion() {
        if (_creado.value !is UiState.Cargando) _creado.value = null
    }

    fun abrir(id: Int) {
        trabajoDetalle?.cancel()
        trabajoDetalle = viewModelScope.launch {
            _detalle.value = UiState.Cargando
            // Las dos llamadas se piden a la vez.
            val (post, comentarios) = coroutineScope {
                val p = async { repo.detalle(id) }
                val c = async { repo.comentarios(id) }
                p.await() to c.await()
            }
            // Si cualquiera de las dos falla, todo el detalle muestra el error.
            val error = post.exceptionOrNull() ?: comentarios.exceptionOrNull()
            _detalle.value = if (error != null) {
                UiState.Error(mensajeDe(error))
            } else {
                UiState.Exito(DetalleUi(post.getOrThrow(), comentarios.getOrThrow()))
            }
        }
    }
}
