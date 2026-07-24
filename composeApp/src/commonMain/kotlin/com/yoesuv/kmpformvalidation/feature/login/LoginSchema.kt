package com.yoesuv.kmpformvalidation.feature.login

import com.yoesuv.kmpformvalidation.utils.validation.isValidEmailFormat
import io.konform.validation.Validation
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.notBlank

data class LoginSchema(
    val email: String,
    val password: String
)

fun loginValidation(messages: LoginMessages): Validation<LoginSchema> = Validation {
    LoginSchema::email {
        notBlank() hint messages.emailRequired
        constrain(messages.emailInvalid) { it.trim().isValidEmailFormat() }
    }
    LoginSchema::password {
        notBlank() hint messages.passwordRequired
        minLength(5) hint messages.passwordTooShort
    }
}
