package com.yoesuv.kmpformvalidation.feature.register

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
import com.yoesuv.kmpformvalidation.utils.validation.validateFullName
import com.yoesuv.kmpformvalidation.utils.validation.validateConfirmPassword

/**
 * ViewModel for Register Screen
 * Manages register form state and validation logic
 */
class RegisterViewModel : ViewModel() {
    
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
    
    // Full Name validation state
    private val _fullNameValidation = MutableStateFlow(ValidationModel(true, ""))
    val fullNameValidation: StateFlow<ValidationModel> = _fullNameValidation.asStateFlow()
    
    // Email validation state
    private val _emailValidation = MutableStateFlow(ValidationModel(true, ""))
    val emailValidation: StateFlow<ValidationModel> = _emailValidation.asStateFlow()
    
    // Password validation state
    private val _passwordValidation = MutableStateFlow(ValidationModel(true, ""))
    val passwordValidation: StateFlow<ValidationModel> = _passwordValidation.asStateFlow()
    
    // Confirm Password validation state
    private val _confirmPasswordValidation = MutableStateFlow(ValidationModel(true, ""))
    val confirmPasswordValidation: StateFlow<ValidationModel> = _confirmPasswordValidation.asStateFlow()
    
    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Error display states
    private val _showFullNameError = MutableStateFlow(false)
    val showFullNameError: StateFlow<Boolean> = _showFullNameError.asStateFlow()
    
    private val _showEmailError = MutableStateFlow(false)
    val showEmailError: StateFlow<Boolean> = _showEmailError.asStateFlow()
    
    private val _showPasswordError = MutableStateFlow(false)
    val showPasswordError: StateFlow<Boolean> = _showPasswordError.asStateFlow()
    
    private val _showConfirmPasswordError = MutableStateFlow(false)
    val showConfirmPasswordError: StateFlow<Boolean> = _showConfirmPasswordError.asStateFlow()
    
    // Computed property to check if form is valid
    val isFormValid: StateFlow<Boolean> = combine(
        _fullName,
        _email,
        _password,
        _confirmPassword,
        _fullNameValidation,
        _emailValidation,
        _passwordValidation,
        _confirmPasswordValidation
    ) { values ->
        val fullName = values[0] as String
        val email = values[1] as String
        val password = values[2] as String
        val confirmPassword = values[3] as String
        val fullNameValidation = values[4] as ValidationModel
        val emailValidation = values[5] as ValidationModel
        val passwordValidation = values[6] as ValidationModel
        val confirmPasswordValidation = values[7] as ValidationModel
        
        fullName.isNotEmpty() && 
        email.isNotEmpty() && 
        password.isNotEmpty() && 
        confirmPassword.isNotEmpty() &&
        fullNameValidation.isValid && 
        emailValidation.isValid && 
        passwordValidation.isValid &&
        confirmPasswordValidation.isValid
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )
    
    /**
     * Update full name value and validate it
     */
    fun updateFullName(newFullName: String, fullNameRequiredMessage: String, fullNameTooShortMessage: String) {
        _fullName.value = newFullName
        _fullNameValidation.value = newFullName.validateFullName(fullNameRequiredMessage, fullNameTooShortMessage)
        // Show error only if user has started typing and field is not empty
        _showFullNameError.value = newFullName.isNotEmpty() && !_fullNameValidation.value.isValid
    }
    
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
        
        // Re-validate confirm password when password changes
        if (_confirmPassword.value.isNotEmpty()) {
            val confirmPasswordRequiredMessage = "Confirm password is required" // This will be passed from UI
            val passwordsDoNotMatchMessage = "Passwords do not match" // This will be passed from UI
            _confirmPasswordValidation.value = _confirmPassword.value.validateConfirmPassword(
                newPassword, confirmPasswordRequiredMessage, passwordsDoNotMatchMessage
            )
            _showConfirmPasswordError.value = _confirmPassword.value.isNotEmpty() && !_confirmPasswordValidation.value.isValid
        }
    }
    
    /**
     * Update confirm password value and validate it
     */
    fun updateConfirmPassword(newConfirmPassword: String, confirmPasswordRequiredMessage: String, passwordsDoNotMatchMessage: String) {
        _confirmPassword.value = newConfirmPassword
        _confirmPasswordValidation.value = newConfirmPassword.validateConfirmPassword(
            _password.value, confirmPasswordRequiredMessage, passwordsDoNotMatchMessage
        )
        // Show error only if user has started typing and field is not empty
        _showConfirmPasswordError.value = newConfirmPassword.isNotEmpty() && !_confirmPasswordValidation.value.isValid
    }
    
    /**
     * Perform register action
     * This method will be expanded later with actual registration logic
     */
    fun register(
        fullNameRequiredMessage: String,
        fullNameTooShortMessage: String,
        emailRequiredMessage: String, 
        emailInvalidMessage: String,
        passwordRequiredMessage: String,
        passwordTooShortMessage: String,
        confirmPasswordRequiredMessage: String,
        passwordsDoNotMatchMessage: String
    ) {
        // Validate all fields first
        updateFullName(_fullName.value, fullNameRequiredMessage, fullNameTooShortMessage)
        updateEmail(_email.value, emailRequiredMessage, emailInvalidMessage)
        updatePassword(_password.value, passwordRequiredMessage, passwordTooShortMessage)
        updateConfirmPassword(_confirmPassword.value, confirmPasswordRequiredMessage, passwordsDoNotMatchMessage)
        
        // Show all errors if fields are empty
        _showFullNameError.value = !_fullNameValidation.value.isValid
        _showEmailError.value = !_emailValidation.value.isValid
        _showPasswordError.value = !_passwordValidation.value.isValid
        _showConfirmPasswordError.value = !_confirmPasswordValidation.value.isValid
        
        // Only proceed if form is valid
        if (isFormValid.value) {
            _isLoading.value = true
            // TODO: Implement actual registration logic here
            // For now, just simulate loading
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
        _fullNameValidation.value = ValidationModel(true, "")
        _emailValidation.value = ValidationModel(true, "")
        _passwordValidation.value = ValidationModel(true, "")
        _confirmPasswordValidation.value = ValidationModel(true, "")
        _showFullNameError.value = false
        _showEmailError.value = false
        _showPasswordError.value = false
        _showConfirmPasswordError.value = false
        _isLoading.value = false
    }
}
