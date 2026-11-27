package com.chavez.murocampus.model

import kotlinx.serialization.Serializable

// Los nombres de las propiedades son idénticos a los del JSON de JSONPlaceholder.

// GET posts y GET posts/{id}
@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

// GET posts/{id}/comments
@Serializable
data class Comentario(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

// Cuerpo del POST posts: lo que enviamos no lleva id (lo asigna el servidor).
@Serializable
data class NuevoPost(
    val userId: Int,
    val title: String,
    val body: String
)
