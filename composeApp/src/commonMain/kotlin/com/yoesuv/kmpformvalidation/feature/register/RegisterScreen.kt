package com.yoesuv.kmpformvalidation.feature.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
    // Get string resources once in the Composable context
    val messages = RegisterMessages(
        fullNameRequired = stringResource(Res.string.full_name_required),
        fullNameTooShort = stringResource(Res.string.full_name_too_short),
        emailRequired = stringResource(Res.string.email_required),
        emailInvalidFormat = stringResource(Res.string.email_invalid_format),
        passwordRequired = stringResource(Res.string.password_required),
        passwordTooShort = stringResource(Res.string.password_too_short),
        confirmPasswordRequired = stringResource(Res.string.confirm_password_required),
        passwordsDoNotMatch = stringResource(Res.string.passwords_do_not_match),
    )

    val viewModel = viewModel { RegisterViewModel(messages) }
    // Collect state from ViewModel
    val fullName by viewModel.fullName.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val fullNameError by viewModel.fullNameError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val confirmPasswordError by viewModel.confirmPasswordError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isFormValid by viewModel.isFormValid.collectAsState()

    // Focus requesters for keyboard navigation
    val (fullNameFocus, emailFocus, passwordFocus, confirmFocus) = remember { FocusRequester.createRefs() }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
                .verticalScroll(rememberScrollState()),
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
                    text = stringResource(Res.string.register_title),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Full Name Field with Validation
                AppTextField(
                    value = fullName,
                    onValueChange = { newFullName ->
                        viewModel.updateFullName(newFullName)
                    },
                    label = stringResource(Res.string.full_name_label),
                    placeholder = stringResource(Res.string.full_name_placeholder),
                    keyboardType = KeyboardType.Text,
                    isError = fullNameError != null,
                    errorMessage = fullNameError,
                    focusRequester = fullNameFocus,
                    keyboardActions = KeyboardActions(onNext = { emailFocus.requestFocus() })
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Field with Validation
                AppTextField(
                    value = email,
                    onValueChange = { newEmail ->
                        viewModel.updateEmail(newEmail)
                    },
                    label = stringResource(Res.string.email_label),
                    placeholder = stringResource(Res.string.email_placeholder),
                    keyboardType = KeyboardType.Email,
                    isError = emailError != null,
                    errorMessage = emailError,
                    focusRequester = emailFocus,
                    keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() })
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                AppPasswordField(
                    value = password,
                    onValueChange = { newPassword ->
                        viewModel.updatePassword(newPassword)
                    },
                    label = stringResource(Res.string.password_label),
                    placeholder = stringResource(Res.string.password_placeholder),
                    isError = passwordError != null,
                    errorMessage = passwordError,
                    focusRequester = passwordFocus,
                    keyboardActions = KeyboardActions(onNext = { confirmFocus.requestFocus() })
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password Field
                AppPasswordField(
                    value = confirmPassword,
                    onValueChange = { newConfirmPassword ->
                        viewModel.updateConfirmPassword(newConfirmPassword)
                    },
                    label = stringResource(Res.string.confirm_password_label),
                    placeholder = stringResource(Res.string.confirm_password_placeholder),
                    isError = confirmPasswordError != null,
                    errorMessage = confirmPasswordError,
                    focusRequester = confirmFocus,
                    keyboardActions = KeyboardActions(onDone = {
                        if (isFormValid && !isLoading) {
                            viewModel.register()
                        }
                    })
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Register Button
                AppButton(
                    text = stringResource(Res.string.register_button),
                    onClick = {
                        viewModel.register()
                    },
                    fillMaxWidth = true,
                    isLoading = isLoading,
                    enabled = isFormValid && !isLoading
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation back to Log in
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(Res.string.already_have_account),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    TextButton(
                        onClick = onNavigateBack
                    ) {
                        Text(
                            text = stringResource(Res.string.login_link),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}