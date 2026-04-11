package com.example.myemployee.di

import com.example.myemployee.data.local.Database
import com.example.myemployee.data.local.DatabaseDriverFactory
import com.example.myemployee.data.remote.ApiService
import com.example.myemployee.data.remote.ApiServiceImpl
import com.example.myemployee.data.repository.EmployeeRepositoryImpl
import com.example.myemployee.domain.repository.EmployeeRepository
import com.example.myemployee.domain.usecase.GetAllEmployee
import com.example.myemployee.presentation.EmployeeViewModel
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

    single { Database(get()) }

    factory {
        GetAllEmployee(get())
    }
    factory {
        EmployeeViewModel(get(), get())
    }

}