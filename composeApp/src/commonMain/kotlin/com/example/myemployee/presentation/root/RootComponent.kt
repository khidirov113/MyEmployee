package com.example.myemployee.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.myemployee.domain.usecase.GetAllEmployee
import com.example.myemployee.domain.usecase.GetEmployeeByIdUseCase
import com.example.myemployee.domain.usecase.GetRefresh
import com.example.myemployee.domain.usecase.SearchEmployeeUseCase
import com.example.myemployee.presentation.components.DefaultEmployeeDetailsComponent
import com.example.myemployee.presentation.components.DefaultEmployeeListComponent
import com.example.myemployee.presentation.components.EmployeeDetailsComponent
import com.example.myemployee.presentation.components.EmployeeListComponent
import kotlinx.serialization.Serializable

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class EmployeeList(val component: EmployeeListComponent) : Child()
        class EmployeeDetails(val component: EmployeeDetailsComponent) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val getAllEmployee: GetAllEmployee,
    private val getEmployeeByIdUseCase: GetEmployeeByIdUseCase,
    private val getRefresh: GetRefresh,
    private val searchEmployeeUseCase: SearchEmployeeUseCase
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.EmployeeList,
            handleBackButton = true,
            childFactory = ::createChild
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun createChild(config: Config, context: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.EmployeeList -> RootComponent.Child.EmployeeList(
                component = DefaultEmployeeListComponent(
                    componentContext = context,
                    storeFactory = storeFactory,
                    getAllEmployee = getAllEmployee,
                    getRefresh = getRefresh,
                    searchEmployeeUseCase = searchEmployeeUseCase,
                    onNavigateToProfile = { id ->
                        navigation.push(Config.Details(id))
                    }
                )
            )

            is Config.Details -> RootComponent.Child.EmployeeDetails(
                component = DefaultEmployeeDetailsComponent(
                    componentContext = context,
                    storeFactory = storeFactory,
                    getEmployeeByIdUseCase = getEmployeeByIdUseCase,
                    employeeId = config.id,
                    onBack = { navigation.pop() }
                )
            )
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object EmployeeList : Config

        @Serializable
        data class Details(val id: String) : Config

    }
}