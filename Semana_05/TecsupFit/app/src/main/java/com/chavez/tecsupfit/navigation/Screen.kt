package com.chavez.tecsupfit.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ClassDetail : Screen("class_detail/{claseId}") {
        fun createRoute(claseId: Int) = "class_detail/$claseId"
    }
    object Confirmation : Screen("confirmation/{claseId}") {
        fun createRoute(claseId: Int) = "confirmation/$claseId"
    }
    object Reservations : Screen("reservations")
    object Routines : Screen("routines")
    object Profile : Screen("profile")
}