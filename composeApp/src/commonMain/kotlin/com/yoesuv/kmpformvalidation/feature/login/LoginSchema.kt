package com.yoesuv.kmpformvalidation.feature.login

import com.yoesuv.kmpformvalidation.utils.validation.isValidEmailFormat
import io.konform.validation.Validation
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.notBlank

data class LoginSchema(
    val email: String,
    val password: String
)

fun loginValidation(
    emailRequired: String,
    emailInvalid: String,
    passwordRequired: String,
    passwordTooShort: String,
): Validation<LoginSchema> = Validation {
    LoginSchema::email {
        notBlank() hint emailRequired
        constrain(emailInvalid) { it.isValidEmailFormat() }
    }
    LoginSchema::password {
        notBlank() hint passwordRequired
        minLength(5) hint passwordTooShort
    }
}
