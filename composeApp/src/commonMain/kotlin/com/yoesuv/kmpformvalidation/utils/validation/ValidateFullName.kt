package com.yoesuv.kmpformvalidation.utils.validation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import kmpformvalidation.composeapp.generated.resources.Res
import kmpformvalidation.composeapp.generated.resources.full_name_required
import kmpformvalidation.composeapp.generated.resources.full_name_too_short

/**
 * Extension function to validate full name (basic version with hardcoded messages)
 * @return ValidationModel with validation result and message
 */
fun String.validateFullName(): ValidationModel {
    return when {
        this.isBlank() -> ValidationModel(
            isValid = false,
            message = "Full name is required"
        )
        
        this.trim().length < 2 -> ValidationModel(
            isValid = false,
            message = "Full name must be at least 2 characters"
        )
        
        else -> ValidationModel(
            isValid = true,
            message = ""
        )
    }
}

/**
 * Extension function to validate full name with string parameters
 * @param fullNameRequiredMessage Message to show when full name is required
 * @param fullNameTooShortMessage Message to show when full name is too short
 * @return ValidationModel with validation result and localized message
 */
fun String.validateFullName(
    fullNameRequiredMessage: String,
    fullNameTooShortMessage: String
): ValidationModel {
    return when {
        this.isBlank() -> ValidationModel(
            isValid = false,
            message = fullNameRequiredMessage
        )
        
        this.trim().length < 2 -> ValidationModel(
            isValid = false,
            message = fullNameTooShortMessage
        )
        
        else -> ValidationModel(
            isValid = true,
            message = ""
        )
    }
}

/**
 * Composable extension function to validate full name with compose string resources
 * @return ValidationModel with validation result and localized message
 */
@Composable
fun String.validateFullNameComposable(): ValidationModel {
    return this.validateFullName(
        fullNameRequiredMessage = stringResource(Res.string.full_name_required),
        fullNameTooShortMessage = stringResource(Res.string.full_name_too_short)
    )
}
