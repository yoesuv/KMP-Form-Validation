package com.yoesuv.kmpformvalidation.utils.validation

import androidx.compose.runtime.Composable
import kotlin.text.Regex
import org.jetbrains.compose.resources.stringResource
import kmpformvalidation.composeapp.generated.resources.Res
import kmpformvalidation.composeapp.generated.resources.email_required
import kmpformvalidation.composeapp.generated.resources.email_invalid_format

/**
 * Extension function to validate email format (basic version with hardcoded messages)
 * @return ValidationModel with validation result and message
 */
fun String.validateEmail(): ValidationModel {
    return when {
        this.isBlank() -> ValidationModel(
            isValid = false,
            message = "Email is required"
        )
        
        !this.isValidEmailFormat() -> ValidationModel(
            isValid = false,
            message = "Email format is not valid"
        )
        
        else -> ValidationModel(
            isValid = true,
            message = ""
        )
    }
}

/**
 * Extension function to validate email format with string parameters
 * @param emailRequiredMessage Message to show when email is required
 * @param emailInvalidMessage Message to show when email format is invalid
 * @return ValidationModel with validation result and localized message
 */
fun String.validateEmail(
    emailRequiredMessage: String,
    emailInvalidMessage: String
): ValidationModel {
    return when {
        this.isBlank() -> ValidationModel(
            isValid = false,
            message = emailRequiredMessage
        )
        
        !this.isValidEmailFormat() -> ValidationModel(
            isValid = false,
            message = emailInvalidMessage
        )
        
        else -> ValidationModel(
            isValid = true,
            message = ""
        )
    }
}

/**
 * Composable extension function to validate email format with compose string resources
 * @return ValidationModel with validation result and localized message
 */
@Composable
fun String.validateEmailComposable(): ValidationModel {
    return this.validateEmail(
        emailRequiredMessage = stringResource(Res.string.email_required),
        emailInvalidMessage = stringResource(Res.string.email_invalid_format)
    )
}

/**
 * Helper function to check email format using regex
 * @return true if email format is valid, false otherwise
 */
private fun String.isValidEmailFormat(): Boolean {
    val emailRegex = Regex(
        pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )
    return emailRegex.matches(this)
}
