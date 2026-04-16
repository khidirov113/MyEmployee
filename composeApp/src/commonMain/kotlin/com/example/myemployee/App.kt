package com.example.myemployee

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.myemployee.presentation.root.RootComponent
import com.example.myemployee.presentation.ui.RootContent

@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        RootContent(root)
    }
}
