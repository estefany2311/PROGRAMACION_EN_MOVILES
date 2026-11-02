package com.tecsup.huellitasvet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tecsup.huellitasvet.data.local.entity.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave LIMIT 1")
    suspend fun login(correo: String, clave: String): Usuario?

    // El administrador entra con correo, contraseña y su codigo
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND clave = :clave AND codigo = :codigo AND rol = 'ADMINISTRADOR' LIMIT 1")
    suspend fun loginAdministrador(correo: String, clave: String, codigo: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun obtener(id: Int): Usuario?

    @Query("SELECT * FROM usuarios WHERE rol = :rol ORDER BY id LIMIT 1")
    suspend fun primeroConRol(rol: String): Usuario?

    // Todos los usuarios para la pantalla del administrador
    @Query("SELECT * FROM usuarios ORDER BY rol, nombre")
    fun observarTodos(): Flow<List<Usuario>>

    @Query("SELECT COUNT(*) > 0 FROM usuarios WHERE correo = :correo")
    suspend fun correoExiste(correo: String): Boolean

    @Query("SELECT COUNT(*) > 0 FROM usuarios WHERE codigo = :codigo")
    suspend fun codigoExiste(codigo: String): Boolean

    @Insert suspend fun insertar(usuario: Usuario)

    @Insert suspend fun insertarTodos(lista: List<Usuario>)

    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun contar(): Int
}
