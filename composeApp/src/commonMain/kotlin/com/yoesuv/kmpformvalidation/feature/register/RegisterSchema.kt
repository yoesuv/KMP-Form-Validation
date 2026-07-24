package com.yoesuv.kmpformvalidation.feature.register

import com.yoesuv.kmpformvalidation.utils.validation.isValidEmailFormat
import io.konform.validation.Validation
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.notBlank

data class RegisterSchema(
    val fullName: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

fun registerValidation(messages: RegisterMessages): Validation<RegisterSchema> = Validation {
    RegisterSchema::fullName {
        notBlank() hint messages.fullNameRequired
        constrain(messages.fullNameTooShort) { it.trim().length >= 2 }
    }
    RegisterSchema::email {
        notBlank() hint messages.emailRequired
        constrain(messages.emailInvalidFormat) { it.trim().isValidEmailFormat() }
    }
    RegisterSchema::password {
        notBlank() hint messages.passwordRequired
        minLength(5) hint messages.passwordTooShort
    }
    // Cross-field validation: confirm password must match password.
    // The `dynamic` block gives access to the whole schema so the
    // confirmPassword constraints can reference schema.password.
    dynamic { schema ->
        RegisterSchema::confirmPassword {
            notBlank() hint messages.confirmPasswordRequired
            constrain(messages.passwordsDoNotMatch) { it == schema.password }
        }
    }
}