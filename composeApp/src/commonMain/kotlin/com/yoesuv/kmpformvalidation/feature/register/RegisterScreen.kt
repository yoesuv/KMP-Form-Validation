package com.yoesuv.kmpformvalidation.feature.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yoesuv.kmpformvalidation.feature.components.AppButton
import com.yoesuv.kmpformvalidation.feature.components.AppPasswordField
import com.yoesuv.kmpformvalidation.feature.components.AppTextField
import kmpformvalidation.composeapp.generated.resources.Res
import kmpformvalidation.composeapp.generated.resources.already_have_account
import kmpformvalidation.composeapp.generated.resources.confirm_password_label
import kmpformvalidation.composeapp.generated.resources.confirm_password_placeholder
import kmpformvalidation.composeapp.generated.resources.confirm_password_required
import kmpformvalidation.composeapp.generated.resources.email_invalid_format
import kmpformvalidation.composeapp.generated.resources.email_label
import kmpformvalidation.composeapp.generated.resources.email_placeholder
import kmpformvalidation.composeapp.generated.resources.email_required
import kmpformvalidation.composeapp.generated.resources.full_name_label
import kmpformvalidation.composeapp.generated.resources.full_name_placeholder
import kmpformvalidation.composeapp.generated.resources.full_name_required
import kmpformvalidation.composeapp.generated.resources.full_name_too_short
import kmpformvalidation.composeapp.generated.resources.login_link
import kmpformvalidation.composeapp.generated.resources.password_label
import kmpformvalidation.composeapp.generated.resources.password_placeholder
import kmpformvalidation.composeapp.generated.resources.password_required
import kmpformvalidation.composeapp.generated.resources.password_too_short
import kmpformvalidation.composeapp.generated.resources.passwords_do_not_match
import kmpformvalidation.composeapp.generated.resources.register_button
import kmpformvalidation.composeapp.generated.resources.register_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit = {},
) {
    val viewModel = viewModel { RegisterViewModel() }
    // Collect states from ViewModel
    val fullName by viewModel.fullName.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val fullNameValidation by viewModel.fullNameValidation.collectAsState()
    val emailValidation by viewModel.emailValidation.collectAsState()
    val passwordValidation by viewModel.passwordValidation.collectAsState()
    val confirmPasswordValidation by viewModel.confirmPasswordValidation.collectAsState()
    val showFullNameError by viewModel.showFullNameError.collectAsState()
    val showEmailError by viewModel.showEmailError.collectAsState()
    val showPasswordError by viewModel.showPasswordError.collectAsState()
    val showConfirmPasswordError by viewModel.showConfirmPasswordError.collectAsState()
    val isFormValid by viewModel.isFormValid.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    // String resources for validation messages
    val fullNameRequiredMessage = stringResource(Res.string.full_name_required)
    val fullNameTooShortMessage = stringResource(Res.string.full_name_too_short)
    val emailRequiredMessage = stringResource(Res.string.email_required)
    val emailInvalidMessage = stringResource(Res.string.email_invalid_format)
    val passwordRequiredMessage = stringResource(Res.string.password_required)
    val passwordTooShortMessage = stringResource(Res.string.password_too_short)
    val confirmPasswordRequiredMessage = stringResource(Res.string.confirm_password_required)
    val passwordsDoNotMatchMessage = stringResource(Res.string.passwords_do_not_match)
    
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Register Title
            Text(
                text = stringResource(Res.string.register_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Full Name Field
            AppTextField(
                value = fullName,
                onValueChange = { 
                    viewModel.updateFullName(it, fullNameRequiredMessage, fullNameTooShortMessage)
                },
                label = stringResource(Res.string.full_name_label),
                placeholder = stringResource(Res.string.full_name_placeholder),
                keyboardType = KeyboardType.Text,
                isError = showFullNameError,
                errorMessage = if (showFullNameError) fullNameValidation.message else ""
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Email Field
            AppTextField(
                value = email,
                onValueChange = { 
                    viewModel.updateEmail(it, emailRequiredMessage, emailInvalidMessage)
                },
                label = stringResource(Res.string.email_label),
                placeholder = stringResource(Res.string.email_placeholder),
                keyboardType = KeyboardType.Email,
                isError = showEmailError,
                errorMessage = if (showEmailError) emailValidation.message else ""
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Password Field
            AppPasswordField(
                value = password,
                onValueChange = { 
                    viewModel.updatePassword(it, passwordRequiredMessage, passwordTooShortMessage, confirmPasswordRequiredMessage, passwordsDoNotMatchMessage)
                },
                label = stringResource(Res.string.password_label),
                placeholder = stringResource(Res.string.password_placeholder),
                isError = showPasswordError,
                errorMessage = if (showPasswordError) passwordValidation.message else ""
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Confirm Password Field
            AppPasswordField(
                value = confirmPassword,
                onValueChange = { 
                    viewModel.updateConfirmPassword(it, confirmPasswordRequiredMessage, passwordsDoNotMatchMessage)
                },
                label = stringResource(Res.string.confirm_password_label),
                placeholder = stringResource(Res.string.confirm_password_placeholder),
                isError = showConfirmPasswordError,
                errorMessage = if (showConfirmPasswordError) confirmPasswordValidation.message else ""
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Register Button
            AppButton(
                text = stringResource(Res.string.register_button),
                onClick = {
                    viewModel.register(
                        fullNameRequiredMessage = fullNameRequiredMessage,
                        fullNameTooShortMessage = fullNameTooShortMessage,
                        emailRequiredMessage = emailRequiredMessage,
                        emailInvalidMessage = emailInvalidMessage,
                        passwordRequiredMessage = passwordRequiredMessage,
                        passwordTooShortMessage = passwordTooShortMessage,
                        confirmPasswordRequiredMessage = confirmPasswordRequiredMessage,
                        passwordsDoNotMatchMessage = passwordsDoNotMatchMessage
                    )
                },
                enabled = isFormValid,
                isLoading = isLoading,
                fillMaxWidth = true
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Login Link
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.already_have_account),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TextButton(
                    onClick = onNavigateBack
                ) {
                    Text(
                        text = stringResource(Res.string.login_link),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
