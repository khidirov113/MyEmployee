package com.example.myemployee.presentation.empl.mvi

import com.arkivanov.mvikotlin.core.store.Store
import com.example.myemployee.domain.entity.Employee

interface EmplStore : Store<EmplStore.Intent, EmplStore.State, EmplStore.Label> {

    sealed interface Intent {
        object Refresh : Intent
        class Search(val query: String) : Intent

        object Button : Intent
    }

    data class State(
        val employees: List<Employee> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isEmpty: Boolean = false,
        val searchQuery: String = "",
        val button: Boolean = false
    )

    sealed interface Label {
        data class ShowError(val message: String) : Label
    }

}