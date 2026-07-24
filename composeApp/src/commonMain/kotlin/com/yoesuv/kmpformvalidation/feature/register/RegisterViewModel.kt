package com.yoesuv.kmpformvalidation.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.konform.validation.Validation
import io.konform.validation.ValidationResult
import io.konform.validation.Valid
import io.konform.validation.messagesAtPath
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for Register Screen
 * Manages register form state and validation logic
 */
class RegisterViewModel(
    private val messages: RegisterMessages,
) : ViewModel() {

    // Full Name state
    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    // Email state
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    // Password state
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // Confirm Password state
    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    // Loading state for register button
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val validation: Validation<RegisterSchema> = registerValidation(messages)

    val registerState: StateFlow<ValidationResult<RegisterSchema>> =
        combine(_fullName, _email, _password, _confirmPassword) { n, e, p, c ->
            validation(RegisterSchema(n, e, p, c))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = validation(RegisterSchema("", "", "", ""))
        )

    val fullNameError: StateFlow<String?> =
        combine(registerState, _fullName) { result, value ->
            if (value.isNotEmpty()) {
                result.errors.messagesAtPath(RegisterSchema::fullName).firstOrNull()
            } else null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val emailError: StateFlow<String?> =
        combine(registerState, _email) { result, value ->
            if (value.isNotEmpty()) {
                result.errors.messagesAtPath(RegisterSchema::email).firstOrNull()
            } else null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val passwordError: StateFlow<String?> =
        combine(registerState, _password) { result, value ->
            if (value.isNotEmpty()) {
                result.errors.messagesAtPath(RegisterSchema::password).firstOrNull()
            } else null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val confirmPasswordError: StateFlow<String?> =
        combine(registerState, _confirmPassword) { result, value ->
            if (value.isNotEmpty()) {
                result.errors.messagesAtPath(RegisterSchema::confirmPassword).firstOrNull()
            } else null
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isFormValid: StateFlow<Boolean> = registerState
        .map { it is Valid<RegisterSchema> }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun updateFullName(newFullName: String) {
        _fullName.value = newFullName
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun register() {
        val result = validation(
            RegisterSchema(_fullName.value, _email.value, _password.value, _confirmPassword.value)
        )
        println("RegisterViewModel # register ${result.isValid}")
        if (result is Valid<RegisterSchema>) {
            _isLoading.value = true
            // TODO actual registration
            _isLoading.value = false
        }
    }

    /**
     * Clear all form data
     */
    fun clearForm() {
        _fullName.value = ""
        _email.value = ""
        _password.value = ""
        _confirmPassword.value = ""
        _isLoading.value = false
    }
}