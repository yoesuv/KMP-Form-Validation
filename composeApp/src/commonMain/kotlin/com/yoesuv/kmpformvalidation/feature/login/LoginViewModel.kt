package com.yoesuv.kmpformvalidation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.Valid
import io.konform.validation.messagesAtPath
import kotlinx.coroutines.flow.map

/**
 * ViewModel for Login Screen
 * Manages login form state and validation logic
 */
class LoginViewModel(
    emailRequired: String,
    emailInvalid: String,
    passwordRequired: String,
    passwordTooShort: String,
) : ViewModel() {

    // Email state
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    // Password state
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // Loading state for login button
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val validation: Validation<LoginSchema> = loginValidation(
        emailRequired, emailInvalid, passwordRequired, passwordTooShort
    )

    val loginState: StateFlow<ValidationResult<LoginSchema>> = combine(_email, _password) { e, p ->
        validation(LoginSchema(e, p))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), validation(LoginSchema("", "")))

    val emailError: StateFlow<String?> = combine(loginState, _email) { result, email ->
        if (email.isNotEmpty()) {
            result.errors.messagesAtPath(LoginSchema::email).firstOrNull()
        } else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val passwordError: StateFlow<String?> =
        combine(loginState, _password) { result, password ->
            if (password.isNotEmpty()) {
                result.errors.messagesAtPath(LoginSchema::password).firstOrNull()
            } else null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isFormValid: StateFlow<Boolean> = loginState
        .map { it is Valid<LoginSchema> }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun login(
    ) {
        if (loginState.value is Valid<LoginSchema>) {
            _isLoading.value = true
            // TODO actual login
            _isLoading.value = false
        }

    }

    /**
     * Clear all form data
     */
    fun clearForm() {
        _email.value = ""
        _password.value = ""
        _isLoading.value = false
    }
}
