package com.yoesuv.kmpformvalidation.feature.login

import com.yoesuv.kmpformvalidation.utils.validation.isValidEmailFormat
import io.konform.validation.Validation
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.notBlank

data class Login(
    val email: String,
    val password: String
)

fun loginValidation(
    emailRequired: String,
    emailInvalid: String,
    passwordRequired: String,
    passwordTooShort: String,
): Validation<Login> = Validation {
    Login::email {
        notBlank() hint emailRequired
        constrain(emailInvalid) { it.isValidEmailFormat() }
    }
    Login::password {
        notBlank() hint passwordRequired
        minLength(5) hint passwordTooShort
    }
}
