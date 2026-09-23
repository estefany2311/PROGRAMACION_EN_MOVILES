package com.chavez.NavLab5.models

data class Student(
    val id: Int,
    val nombre: String,
    val carrera: String,
    val correo: String,
    val facultad: String,
    val biografia: String,
    val telefono: String = "+51 987 654 321"
)

object StudentProvider {
    val students = listOf(
        Student(
            id = 1,
            nombre = "Juan Diego Ramos Torres",
            carrera = "Diseño y Desarrollo de Software",
            correo = "juan.diego@tecsup.edu.pe",
            facultad = "Tecnología Digital",
            biografia = "Apasionado por el desarrollo móvil Android con Kotlin y Jetpack Compose. Enfocado en patrones de arquitectura y diseño UI/UX limpio."
        ),
        Student(
            id = 2,
            nombre = "Karla Estefany Chavez Lazo",
            carrera = "Diseño y Desarrollo de Software",
            correo = "karla.chavez@tecsup.edu.pe",
            facultad = "Tecnología Digital",
            biografia = "Desarrolladora Android con experiencia en creación de interfaces modernas con Material 3 y navegación orientada a la experiencia de usuario."
        ),
        Student(
            id = 3,
            nombre = "María Alejandra Flores",
            carrera = "Redes y Telecomunicaciones",
            correo = "maria.flores@tecsup.edu.pe",
            facultad = "Redes y Sistemas",
            biografia = "Especialista en infraestructura de red, ciberseguridad y gestión de servicios cloud en entornos de alta disponibilidad."
        ),
        Student(
            id = 4,
            nombre = "Carlos Eduardo Mendoza",
            carrera = "Big Data y Analítica",
            correo = "carlos.mendoza@tecsup.edu.pe",
            facultad = "Tecnología Digital",
            biografia = "Entusiasta de la ciencia de datos, machine learning y procesamiento de grandes volúmenes de datos en tiempo real."
        ),
        Student(
            id = 5,
            nombre = "Ana Sofía Torres",
            carrera = "Diseño Multimedial",
            correo = "ana.torres@tecsup.edu.pe",
            facultad = "Diseño y Comunicación",
            biografia = "Diseñadora UX/UI especializada en sistemas de diseño, prototipado interactivo y metodologías centradas en el usuario."
        )
    )

    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
}
