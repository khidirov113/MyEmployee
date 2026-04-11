package com.example.myemployee.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myemployee.domain.repository.EmployeeRepository
import com.example.myemployee.domain.usecase.GetAllEmployee
import com.example.myemployee.domain.usecase.GetRefresh
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class EmployeeViewModel(
    private val getAllEmployee: GetAllEmployee,
    private val getRefresh: GetRefresh
) : ViewModel() {

    val employees = getAllEmployee()
        .onEach { list ->
            println("EmployeeViewModel: ${list.size}")
            list.forEach {
                println("EmployeeViewModel: ${it.firstName}, ${it.lastName}")
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        viewModelScope.launch {
            getRefresh()
        }
    }

}