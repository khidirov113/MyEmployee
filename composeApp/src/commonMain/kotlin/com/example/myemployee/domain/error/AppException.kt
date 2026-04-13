package com.example.myemployee.domain.error

sealed class AppException(massage: String) : RuntimeException(massage) {
    class NetworkException : AppException("Не удается подключиться к интернету")

    class EmptyResultException : AppException("Нет результатов")
}