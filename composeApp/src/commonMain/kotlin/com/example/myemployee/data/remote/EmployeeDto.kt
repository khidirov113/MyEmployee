package com.example.myemployee.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeListResponseDto(
    @SerialName("results") val results: List<EmployeeResponseDto>
)

@Serializable
data class EmployeeResponseDto(
    @SerialName("name") val name: NameDto,
    @SerialName("email") val email: String,
    @SerialName("login") val login: LoginDto,
    @SerialName("phone") val phone: String,
    @SerialName("picture") val image: PictureDto
)

@Serializable
data class NameDto(
    @SerialName("first") val firstName: String,
    @SerialName("last") val lastName: String
)

@Serializable
data class LoginDto(
    @SerialName("uuid") val id: String
)

@Serializable
data class PictureDto(
    @SerialName("large") val image: String
)