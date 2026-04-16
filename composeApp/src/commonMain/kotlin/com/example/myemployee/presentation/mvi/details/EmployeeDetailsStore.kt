package com.example.myemployee.presentation.mvi.details

import com.arkivanov.mvikotlin.core.store.Store
import com.example.myemployee.domain.entity.Employee

interface EmployeeDetailsStore :
    Store<EmployeeDetailsStore.Intent, EmployeeDetailsStore.State, EmployeeDetailsStore.Label> {
    sealed interface Intent {
        object Back : Intent
        data class LoadEmployee(val id: String) : Intent
    }

    data class State(
        val employee: Employee? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )

    sealed interface Label {
        data class ShowError(val message: String) : Label
        object Back : Label
    }
}