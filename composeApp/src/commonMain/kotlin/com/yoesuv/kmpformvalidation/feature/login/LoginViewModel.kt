package com.yoesuv.kmpformvalidation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import com.yoesuv.kmpformvalidation.utils.validation.ValidationModel
import com.yoesuv.kmpformvalidation.utils.validation.validateEmail
import com.yoesuv.kmpformvalidation.utils.validation.validatePassword

/**
 * ViewModel for Login Screen
 * Manages login form state and validation logic
 */
class LoginViewModel : ViewModel() {
    
    // Email state
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()
    
    // Password state
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()
    
    // Email validation state
    private val _emailValidation = MutableStateFlow(ValidationModel(true, ""))
    val emailValidation: StateFlow<ValidationModel> = _emailValidation.asStateFlow()
    
    // Password validation state
    private val _passwordValidation = MutableStateFlow(ValidationModel(true, ""))
    val passwordValidation: StateFlow<ValidationModel> = _passwordValidation.asStateFlow()
    
    // Show email error state
    private val _showEmailError = MutableStateFlow(false)
    val showEmailError: StateFlow<Boolean> = _showEmailError.asStateFlow()
    
    // Show password error state
    private val _showPasswordError = MutableStateFlow(false)
    val showPasswordError: StateFlow<Boolean> = _showPasswordError.asStateFlow()
    
    // Loading state for login button
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Computed property to check if form is valid (both email and password are valid and not empty)
    val isFormValid: StateFlow<Boolean> = combine(
        _email,
        _password,
        _emailValidation,
        _passwordValidation
    ) { email, password, emailValidation, passwordValidation ->
        email.isNotEmpty() && 
        password.isNotEmpty() && 
        emailValidation.isValid && 
        passwordValidation.isValid
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )
    
    /**
     * Update email value and validate it
     */
    fun updateEmail(newEmail: String, emailRequiredMessage: String, emailInvalidMessage: String) {
        _email.value = newEmail
        _emailValidation.value = newEmail.validateEmail(emailRequiredMessage, emailInvalidMessage)
        // Show error only if user has started typing and field is not empty
        _showEmailError.value = newEmail.isNotEmpty() && !_emailValidation.value.isValid
    }
    
    /**
     * Update password value and validate it
     */
    fun updatePassword(newPassword: String, passwordRequiredMessage: String, passwordTooShortMessage: String) {
        _password.value = newPassword
        _passwordValidation.value = newPassword.validatePassword(passwordRequiredMessage, passwordTooShortMessage)
        // Show error only if user has started typing and field is not empty
        _showPasswordError.value = newPassword.isNotEmpty() && !_passwordValidation.value.isValid
    }
    
    /**
     * Perform login action
     * This method will be expanded later with actual login logic
     */
    fun login(
        emailRequiredMessage: String, 
        emailInvalidMessage: String,
        passwordRequiredMessage: String,
        passwordTooShortMessage: String
    ) {
        // Validate email before proceeding
        val currentEmailValidation = _email.value.validateEmail(emailRequiredMessage, emailInvalidMessage)
        _emailValidation.value = currentEmailValidation
        _showEmailError.value = !currentEmailValidation.isValid
        
        // Validate password before proceeding
        val currentPasswordValidation = _password.value.validatePassword(passwordRequiredMessage, passwordTooShortMessage)
        _passwordValidation.value = currentPasswordValidation
        _showPasswordError.value = !currentPasswordValidation.isValid
        
        // If both validations pass, proceed with login
        if (currentEmailValidation.isValid && currentPasswordValidation.isValid) {
            _isLoading.value = true
            
            // TODO: Implement actual login logic here
            // For now, just simulate loading
            // In the future, this will call authentication service
            
            // Reset loading state (temporary)
            _isLoading.value = false
        }
    }
    
    /**
     * Clear all form data
     */
    fun clearForm() {
        _email.value = ""
        _password.value = ""
        _emailValidation.value = ValidationModel(true, "")
        _passwordValidation.value = ValidationModel(true, "")
        _showEmailError.value = false
        _showPasswordError.value = false
        _isLoading.value = false
    }
}
