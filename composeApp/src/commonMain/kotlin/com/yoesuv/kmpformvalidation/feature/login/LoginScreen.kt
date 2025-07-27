package com.yoesuv.kmpformvalidation.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yoesuv.kmpformvalidation.feature.components.AppButton
import com.yoesuv.kmpformvalidation.feature.components.AppPasswordField
import com.yoesuv.kmpformvalidation.feature.components.AppTextField
import kmpformvalidation.composeapp.generated.resources.Res
import kmpformvalidation.composeapp.generated.resources.create_account_link
import kmpformvalidation.composeapp.generated.resources.dont_have_account
import kmpformvalidation.composeapp.generated.resources.email_label
import kmpformvalidation.composeapp.generated.resources.email_placeholder
import kmpformvalidation.composeapp.generated.resources.email_required
import kmpformvalidation.composeapp.generated.resources.email_invalid_format
import kmpformvalidation.composeapp.generated.resources.login_button
import kmpformvalidation.composeapp.generated.resources.login_title
import kmpformvalidation.composeapp.generated.resources.password_label
import kmpformvalidation.composeapp.generated.resources.password_placeholder
import kmpformvalidation.composeapp.generated.resources.password_required
import kmpformvalidation.composeapp.generated.resources.password_too_short
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
) {
    val viewModel = viewModel { LoginViewModel() }
    // Collect state from ViewModel
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val emailValidation by viewModel.emailValidation.collectAsState()
    val passwordValidation by viewModel.passwordValidation.collectAsState()
    val showEmailError by viewModel.showEmailError.collectAsState()
    val showPasswordError by viewModel.showPasswordError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Get string resources once in the Composable context
    val emailRequiredMessage = stringResource(Res.string.email_required)
    val emailInvalidMessage = stringResource(Res.string.email_invalid_format)
    val passwordRequiredMessage = stringResource(Res.string.password_required)
    val passwordTooShortMessage = stringResource(Res.string.password_too_short)

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Title
                Text(
                    text = stringResource(Res.string.login_title),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Email Field with Validation
                AppTextField(
                    value = email,
                    onValueChange = { newEmail ->
                        viewModel.updateEmail(newEmail, emailRequiredMessage, emailInvalidMessage)
                    },
                    label = stringResource(Res.string.email_label),
                    placeholder = stringResource(Res.string.email_placeholder),
                    keyboardType = KeyboardType.Email,
                    isError = showEmailError,
                    errorMessage = if (showEmailError) emailValidation.message else null
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                AppPasswordField(
                    value = password,
                    onValueChange = { newPassword ->
                        viewModel.updatePassword(newPassword, passwordRequiredMessage, passwordTooShortMessage)
                    },
                    label = stringResource(Res.string.password_label),
                    placeholder = stringResource(Res.string.password_placeholder),
                    isError = showPasswordError,
                    errorMessage = if (showPasswordError) passwordValidation.message else null
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                AppButton(
                    text = stringResource(Res.string.login_button),
                    onClick = {
                        viewModel.login(emailRequiredMessage, emailInvalidMessage, passwordRequiredMessage, passwordTooShortMessage)
                    },
                    fillMaxWidth = true,
                    isLoading = isLoading
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation to Register
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.dont_have_account),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    TextButton(
                        onClick = onNavigateToRegister
                    ) {
                        Text(
                            text = stringResource(Res.string.create_account_link),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
