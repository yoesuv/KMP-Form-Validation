package com.yoesuv.kmpformvalidation.utils.validation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import kmpformvalidation.composeapp.generated.resources.Res
import kmpformvalidation.composeapp.generated.resources.password_required
import kmpformvalidation.composeapp.generated.resources.password_too_short

/**
 * Extension function to validate password (basic version with hardcoded messages)
 * @return ValidationModel with validation result and message
 */
fun String.validatePassword(): ValidationModel {
    return when {
        this.isBlank() -> ValidationModel(
            isValid = false,
            message = "Password is required"
        )
        
        this.length < 5 -> ValidationModel(
            isValid = false,
            message = "Password must be at least 5 characters"
        )
        
        else -> ValidationModel(
            isValid = true,
            message = ""
        )
    }
}

/**
 * Extension function to validate password with string parameters
 * @param passwordRequiredMessage Message to show when password is required
 * @param passwordTooShortMessage Message to show when password is too short
 * @return ValidationModel with validation result and localized message
 */
fun String.validatePassword(
    passwordRequiredMessage: String,
    passwordTooShortMessage: String
): ValidationModel {
    return when {
        this.isBlank() -> ValidationModel(
            isValid = false,
            message = passwordRequiredMessage
        )
        
        this.length < 5 -> ValidationModel(
            isValid = false,
            message = passwordTooShortMessage
        )
        
        else -> ValidationModel(
            isValid = true,
            message = ""
        )
    }
}

/**
 * Composable extension function to validate password with compose string resources
 * @return ValidationModel with validation result and localized message
 */
@Composable
fun String.validatePasswordComposable(): ValidationModel {
    return this.validatePassword(
        passwordRequiredMessage = stringResource(Res.string.password_required),
        passwordTooShortMessage = stringResource(Res.string.password_too_short)
    )
}
