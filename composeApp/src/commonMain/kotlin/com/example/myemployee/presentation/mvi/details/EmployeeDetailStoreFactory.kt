package com.example.myemployee.presentation.mvi.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.myemployee.domain.entity.Employee
import com.example.myemployee.domain.usecase.GetEmployeeByIdUseCase
import kotlinx.coroutines.launch

class EmployeeDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
    private val employeeId: String
) {
    fun create(): EmployeeDetailsStore = object : EmployeeDetailsStore,
        Store<EmployeeDetailsStore.Intent, EmployeeDetailsStore.State, EmployeeDetailsStore.Label> by storeFactory.create(
            name = "EmployeeDetailsStore",
            initialState = EmployeeDetailsStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class EmployeeLoaded(val employee: Employee) : Msg
        data class Error(val isError: Boolean) : Msg
        data class Loading(val isLoading: Boolean) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<EmployeeDetailsStore.Intent, Unit, EmployeeDetailsStore.State, Msg, EmployeeDetailsStore.Label>() {
        override fun executeAction(action: Unit) {
            loadEmployee(employeeId)
        }

        override fun executeIntent(intent: EmployeeDetailsStore.Intent) {
            when (intent) {
                is EmployeeDetailsStore.Intent.LoadEmployee -> loadEmployee(intent.id)
                is EmployeeDetailsStore.Intent.Back -> {
                    onClickBack()
                }
            }
        }

        private fun loadEmployee(id: String) {
            scope.launch {
                dispatch(Msg.Loading(true))
                runCatching {
                    getEmployeeByIdUseCase(id)
                }.onSuccess {
                    dispatch(Msg.EmployeeLoaded(it))
                    dispatch(Msg.Loading(false))
                }.onFailure {
                    dispatch(Msg.Error(true))
                    dispatch(Msg.Loading(false))
                }
            }
        }

        private fun onClickBack() {
            publish(EmployeeDetailsStore.Label.Back)
        }
    }

    private object ReducerImpl : Reducer<EmployeeDetailsStore.State, Msg> {
        override fun EmployeeDetailsStore.State.reduce(
            msg: Msg
        ): EmployeeDetailsStore.State = when (msg) {
            is Msg.EmployeeLoaded -> copy(employee = msg.employee)
            is Msg.Error -> copy(isError = msg.isError)
            is Msg.Loading -> copy(isLoading = msg.isLoading)
        }
    }
}