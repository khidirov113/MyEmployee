package com.example.myemployee.presentation.mvi.list

import com.arkivanov.mvikotlin.core.store.Store
import com.example.myemployee.domain.entity.Employee

interface EmplStore : Store<EmplStore.Intent, EmplStore.State, EmplStore.Label> {

    sealed interface Intent {
        object Refresh : Intent
        class Search(val query: String) : Intent
        data class EmployeeClicked(val id: String) : Intent
    }

    data class State(
        val employees: List<Employee> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isEmpty: Boolean = false,
        val searchQuery: String = "",
    )

    sealed interface Label {
        data class ShowError(val message: String) : Label
        data class ClickedEmployee(val employeeId: String) : Label
    }

}