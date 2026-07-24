package com.yoesuv.kmpformvalidation.feature.register

data class RegisterMessages(
    val fullNameRequired: String,
    val fullNameTooShort: String,
    val emailRequired: String,
    val emailInvalidFormat: String,
    val passwordRequired: String,
    val passwordTooShort: String,
    val confirmPasswordRequired: String,
    val passwordsDoNotMatch: String,
)