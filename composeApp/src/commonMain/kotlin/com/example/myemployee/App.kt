package com.example.myemployee

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myemployee.presentation.empl.ui.EmplScreen
import com.example.myemployee.presentation.empl.mvi.EmplStore
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {
        val store = koinInject<EmplStore>()
        EmplScreen(store)
    }

































//        val getEmployee = koinInject<GetAllEmployee>()
//        val refresh = koinInject<GetRefresh>()
//
//        LaunchedEffect(Unit) {
//            launch {
//                try {
//                    refresh()
//                }catch (e: Exception){
//                    println("Error $e")
//                }
//            }
//            getEmployee().collect { employees ->
//                println("${employees.size}")
//                employees.forEach { employee ->
//                    println("Имя: ${employee.firstName} ${employee.lastName}, Должность: ${employee.position}")
//                }
//                println("==================================")
//            }
//        }

//        var showContent by remember { mutableStateOf(false) }
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
//    }
}