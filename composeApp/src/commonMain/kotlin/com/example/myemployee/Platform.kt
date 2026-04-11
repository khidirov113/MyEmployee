package com.example.myemployee

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform