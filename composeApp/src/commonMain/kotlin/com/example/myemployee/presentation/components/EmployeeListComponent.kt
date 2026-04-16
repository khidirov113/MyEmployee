package com.example.myemployee.presentation.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.myemployee.domain.usecase.GetAllEmployee
import com.example.myemployee.domain.usecase.GetRefresh
import com.example.myemployee.domain.usecase.SearchEmployeeUseCase
import com.example.myemployee.presentation.mvi.list.EmplStore
import com.example.myemployee.presentation.mvi.list.EmployeeListStoreFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface EmployeeListComponent {
    val state: StateFlow<EmplStore.State>
    fun onSearch(query: String)
    fun onRefresh()
    fun onEmployeeClicked(id: String)
}

class DefaultEmployeeListComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val onNavigateToProfile: (id: String) -> Unit,
    getAllEmployee: GetAllEmployee,
    getRefresh: GetRefresh,
    searchEmployeeUseCase: SearchEmployeeUseCase
) : EmployeeListComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        EmployeeListStoreFactory(
            storeFactory, getAllEmployee = getAllEmployee,
            getRefresh = getRefresh,
            searchEmployeeUseCase = searchEmployeeUseCase
        ).create()
    }

    init {
        lifecycle.doOnCreate {
            CoroutineScope(Dispatchers.Main).launch {
                store.labels.collect { label ->
                    when (label) {
                        is EmplStore.Label.ClickedEmployee -> onNavigateToProfile(label.employeeId)
                        is EmplStore.Label.ShowError -> { }
                    }
                }
            }
        }
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<EmplStore.State> = store.stateFlow

    override fun onSearch(query: String) = store.accept(EmplStore.Intent.Search(query))

    override fun onRefresh() = store.accept(EmplStore.Intent.Refresh)

    override fun onEmployeeClicked(id: String) {
        store.accept(EmplStore.Intent.EmployeeClicked(id))
    }


}

