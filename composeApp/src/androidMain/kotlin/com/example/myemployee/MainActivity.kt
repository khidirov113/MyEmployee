package com.example.myemployee

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.myemployee.data.remote.ApiServiceImpl
import com.example.myemployee.di.initKoin
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        initKoin {
            androidContext(this@MainActivity)
        }
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            try {
                val httpClient = HttpClient {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                ignoreUnknownKeys = true
                            }
                        )
                    }
                }

                val data = ApiServiceImpl(httpClient).getAll()
                Log.d("API_RESULT", "Users: ${data.results}")
                Log.d("API_RESULT", data.results.first().name.firstName)

            } catch (e: Exception) {
                Log.e("API_RESULT", "Error: ${e.message}")
            }
        }

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}