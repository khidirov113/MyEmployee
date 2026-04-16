package com.example.myemployee.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.myemployee.presentation.root.RootComponent

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.childStack,
        modifier = modifier.fillMaxSize(),
        animation = stackAnimation()
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.EmployeeList -> EmplScreen(child.component)
            is RootComponent.Child.EmployeeDetails -> EmployeeDetailsScreen(child.component)
        }
    }
}
