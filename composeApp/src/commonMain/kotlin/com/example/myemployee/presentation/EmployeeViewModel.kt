package com.example.myemployee.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myemployee.domain.usecase.GetAllEmployee
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.emptyList

class EmployeeViewModel(
    private val getAllEmployee: GetAllEmployee
) : ViewModel() {

    val employees = getAllEmployee()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}