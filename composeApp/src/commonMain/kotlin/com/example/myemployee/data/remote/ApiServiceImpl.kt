package com.example.myemployee.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
private const val URL = "https://randomuser.me/api/"
class ApiServiceImpl(
    private val httpClient: HttpClient
) : ApiService {
    override suspend fun getAll(
        results: Int,
        include: String
    ): EmployeeListResponseDto {
        return httpClient.get {
            url("$URL?results=$results&inc=$include")
        }.body()
    }
}

