package com.example.myemployee.presentation.mvi.list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.myemployee.domain.entity.Employee
import com.example.myemployee.domain.usecase.GetAllEmployee
import com.example.myemployee.domain.usecase.GetRefresh
import com.example.myemployee.domain.usecase.SearchEmployeeUseCase
import kotlinx.coroutines.launch

class EmployeeListStoreFactory(
    private val storeFactory: StoreFactory,
    private val getAllEmployee: GetAllEmployee,
    private val getRefresh: GetRefresh,
    private val searchEmployeeUseCase: SearchEmployeeUseCase
) {
    fun create(): EmplStore = object : EmplStore,
        Store<EmplStore.Intent, EmplStore.State, EmplStore.Label> by storeFactory.create(
            name = "EmplStore",
            initialState = EmplStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class Loading(val isLoading: Boolean) : Msg
        data class EmployeesLoaded(val list: List<Employee>) : Msg
        data class Error(val isError: Boolean) : Msg
        data class SearchQueryChanged(val query: String) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<EmplStore.Intent, Unit, EmplStore.State, Msg, EmplStore.Label>() {

        override fun executeAction(action: Unit) {
            when (action) {
                is Unit -> {
                    observerEmployees()
                    refreshEmployees()
                }
            }
        }

        override fun executeIntent(intent: EmplStore.Intent) {
            when (intent) {
                is EmplStore.Intent.Refresh -> refreshEmployees()

                is EmplStore.Intent.Search -> {
                    dispatch(Msg.SearchQueryChanged(intent.query))
                    searchEmployees(intent.query)
                }

                is EmplStore.Intent.EmployeeClicked -> {
                    publish(EmplStore.Label.ClickedEmployee(employeeId = intent.id))
                }
            }
        }


        private fun searchEmployees(query: String) {
            if (query.isEmpty()) {
                observerEmployees()
                return
            }
            scope.launch {
                dispatch(Msg.Loading(true))
                try {
                    searchEmployeeUseCase(query).collect { list ->
                        dispatch(Msg.EmployeesLoaded(list))
                        dispatch(Msg.Loading(false))
                    }
                } catch (e: Exception) {
                    dispatch(Msg.Error(true))
                    dispatch(Msg.Loading(false))
                    publish(EmplStore.Label.ShowError("Ошибка поиска"))
                }
            }
        }

        private fun observerEmployees() {
            scope.launch {
                getAllEmployee().collect { list ->
                    dispatch(Msg.EmployeesLoaded(list))
                }
            }
        }

        private fun refreshEmployees() {
            scope.launch {
                dispatch(Msg.Loading(true))
                try {
                    getRefresh()
                } catch (e: Exception) {
                    dispatch(Msg.Error(true))
                    publish(EmplStore.Label.ShowError("Ошибка обновления"))
                } finally {
                    dispatch(Msg.Loading(false))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<EmplStore.State, Msg> {
        override fun EmplStore.State.reduce(msg: Msg): EmplStore.State = when (msg) {
            is Msg.EmployeesLoaded -> copy(employees = msg.list, isEmpty = msg.list.isEmpty())
            is Msg.Error -> copy(isError = msg.isError)
            is Msg.Loading -> copy(isLoading = msg.isLoading)
            is Msg.SearchQueryChanged -> copy(searchQuery = msg.query)
        }
    }
}