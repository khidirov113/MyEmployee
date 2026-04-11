package com.example.myemployee

import androidx.compose.ui.window.ComposeUIViewController
import com.example.myemployee.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}