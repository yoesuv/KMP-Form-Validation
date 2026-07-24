package com.yoesuv.kmpformvalidation.feature.login

data class LoginMessages(
    val emailRequired: String,
    val emailInvalid: String,
    val passwordRequired: String,
    val passwordTooShort: String,
)