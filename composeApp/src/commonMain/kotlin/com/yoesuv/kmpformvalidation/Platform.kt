package com.yoesuv.kmpformvalidation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform