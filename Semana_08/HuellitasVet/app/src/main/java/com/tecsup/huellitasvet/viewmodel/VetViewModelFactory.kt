package com.tecsup.huellitasvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Fabrica generica: recibe la forma de crear el ViewModel con sus dependencias.
// Uso: viewModel(factory = VetViewModelFactory { ServiciosViewModel(app.repository) })
class VetViewModelFactory<T : ViewModel>(private val crear: () -> T) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM = crear() as VM
}
