package com.example.myemployee.presentation

sealed interface Intent { // Действия пользователя
    object LoadEmployees : Intent
    data class SearchEmployees(val query: String) : Intent
}


