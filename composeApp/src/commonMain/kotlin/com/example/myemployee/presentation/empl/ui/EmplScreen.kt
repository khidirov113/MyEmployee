package com.example.myemployee.presentation.empl.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.example.myemployee.presentation.empl.mvi.EmplStore

@Composable
fun EmplScreen(store: EmplStore) {
    val state by store.states.collectAsState(EmplStore.State())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Список сотрудников", style = MaterialTheme.typography.headlineMedium)

            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { query ->
                    store.accept(EmplStore.Intent.Search(query))
                },
                label = { Text("Поиск") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )

            Button(
                onClick = { store.accept(EmplStore.Intent.Button) },
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Text("Кнопка")
            }
            Button(
                onClick = { store.accept(EmplStore.Intent.Refresh) },

                ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Text("Обновить")
            }

            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
            }

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.employees) { employee ->
                    Text(
                        text = "${employee.firstName} ${employee.lastName}",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}
