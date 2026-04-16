package com.example.myemployee.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.myemployee.data.local.Database
import com.example.myemployee.data.remote.ApiService
import com.example.myemployee.data.remote.ApiServiceImpl
import com.example.myemployee.data.repository.EmployeeRepositoryImpl
import com.example.myemployee.domain.repository.EmployeeRepository
import com.example.myemployee.domain.usecase.GetAllEmployee
import com.example.myemployee.domain.usecase.GetEmployeeByIdUseCase
import com.example.myemployee.domain.usecase.GetRefresh
import com.example.myemployee.domain.usecase.SearchEmployeeUseCase
import com.example.myemployee.presentation.mvi.details.EmployeeDetailStoreFactory
import com.example.myemployee.presentation.mvi.list.EmployeeListStoreFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    single<ApiService> {
        ApiServiceImpl(get())
    }

    single<EmployeeRepository> {
        EmployeeRepositoryImpl(get(), get())
    }

    single<StoreFactory> {
        DefaultStoreFactory()
    }

    single { Database(get()) }

    factory {
        GetAllEmployee(get())
    }
    factory {
        GetRefresh(get())
    }

    factory {
        GetEmployeeByIdUseCase(get())
    }
    factory {
        SearchEmployeeUseCase(get())
    }


    factory {
        EmployeeListStoreFactory(
            storeFactory = get(),
            getAllEmployee = get(),
            getRefresh = get(),
            searchEmployeeUseCase = get()
        ).create()
    }

    factory {
        EmployeeDetailStoreFactory(
            storeFactory = get(),
            getEmployeeByIdUseCase = get(),
            employeeId = get()
        ).create()
    }

}