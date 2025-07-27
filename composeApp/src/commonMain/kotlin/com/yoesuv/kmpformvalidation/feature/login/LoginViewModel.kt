package com.yoesuv.kmpformvalidation.feature.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.yoesuv.kmpformvalidation.utils.validation.ValidationModel
import com.yoesuv.kmpformvalidation.utils.validation.validateEmail

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
    
    // Show email error state
    private val _showEmailError = MutableStateFlow(false)
    val showEmailError: StateFlow<Boolean> = _showEmailError.asStateFlow()
    
    // Loading state for login button
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
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
     * Update password value
     */
    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
    
    /**
     * Perform login action
     * This method will be expanded later with actual login logic
     */
    fun login(emailRequiredMessage: String, emailInvalidMessage: String) {
        // Validate email before proceeding
        val currentEmailValidation = _email.value.validateEmail(emailRequiredMessage, emailInvalidMessage)
        _emailValidation.value = currentEmailValidation
        _showEmailError.value = !currentEmailValidation.isValid
        
        // If validation passes, proceed with login
        if (currentEmailValidation.isValid && _password.value.isNotEmpty()) {
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
        _showEmailError.value = false
        _isLoading.value = false
    }
}
