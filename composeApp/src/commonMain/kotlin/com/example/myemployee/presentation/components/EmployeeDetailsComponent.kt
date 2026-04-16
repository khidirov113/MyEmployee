package com.example.myemployee.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.myemployee.domain.entity.Employee
import com.example.myemployee.domain.usecase.GetEmployeeByIdUseCase
import com.example.myemployee.presentation.mvi.details.EmployeeDetailStoreFactory
import com.example.myemployee.presentation.mvi.details.EmployeeDetailsStore
import com.example.myemployee.presentation.mvi.list.EmplStore
import com.example.myemployee.presentation.mvi.list.EmployeeListStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

interface EmployeeDetailsComponent {
    val state: StateFlow<EmployeeDetailsStore.State>
    fun back()

}

class DefaultEmployeeDetailsComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
    private val employeeId: String,
    private val onBack: () -> Unit
) : EmployeeDetailsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        EmployeeDetailStoreFactory(
            storeFactory,
            getEmployeeByIdUseCase = getEmployeeByIdUseCase,
            employeeId = employeeId
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<EmployeeDetailsStore.State> = store.stateFlow


    override fun back() = onBack()


}