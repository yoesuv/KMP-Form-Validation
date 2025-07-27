package com.yoesuv.kmpformvalidation.core.route

import kotlinx.serialization.Serializable

sealed class AppRoute {
    @Serializable
    data object Login : AppRoute()
    @Serializable
    data object Register : AppRoute()
}