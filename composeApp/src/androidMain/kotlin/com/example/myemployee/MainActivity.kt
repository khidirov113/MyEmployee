package com.example.myemployee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myemployee.di.initKoin
import org.koin.android.ext.koin.androidContext

import com.arkivanov.decompose.defaultComponentContext
import com.example.myemployee.presentation.root.DefaultRootComponent
import org.koin.android.ext.android.inject
import com.example.myemployee.domain.usecase.GetAllEmployee
import com.example.myemployee.domain.usecase.GetEmployeeByIdUseCase
import com.example.myemployee.domain.usecase.GetRefresh
import com.example.myemployee.domain.usecase.SearchEmployeeUseCase
import com.arkivanov.mvikotlin.core.store.StoreFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        initKoin {
            androidContext(this@MainActivity)
        }
        super.onCreate(savedInstanceState)

        val getAllEmployee: GetAllEmployee by inject()
        val getEmployeeByIdUseCase: GetEmployeeByIdUseCase by inject()
        val getRefresh: GetRefresh by inject()
        val searchEmployeeUseCase: SearchEmployeeUseCase by inject()
        val storeFactory: StoreFactory by inject()

        val root = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            storeFactory = storeFactory,
            getAllEmployee = getAllEmployee,
            getEmployeeByIdUseCase = getEmployeeByIdUseCase,
            getRefresh = getRefresh,
            searchEmployeeUseCase = searchEmployeeUseCase
        )

        setContent {
            App(root)
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}