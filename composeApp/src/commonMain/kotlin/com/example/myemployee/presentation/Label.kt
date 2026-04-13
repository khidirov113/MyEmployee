package com.example.myemployee.presentation

sealed interface Label {
    data class ShowError(val message: String) : Label
}