package com.example.myemployee

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.myemployee.presentation.root.DefaultRootComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun MainViewController() = ComposeUIViewController {
    val lifecycle = LifecycleRegistry()
    val koin = object : KoinComponent {}

    val root = DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        storeFactory = koin.get(),
        getAllEmployee = koin.get(),
        getEmployeeByIdUseCase = koin.get(),
        getRefresh = koin.get(),
        searchEmployeeUseCase = koin.get()
    )

    App(root = root)
}
