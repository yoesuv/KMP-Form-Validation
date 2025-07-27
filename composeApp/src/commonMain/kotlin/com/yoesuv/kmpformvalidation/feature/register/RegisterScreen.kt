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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yoesuv.kmpformvalidation.feature.components.AppButton
import com.yoesuv.kmpformvalidation.feature.components.AppPasswordField
import com.yoesuv.kmpformvalidation.feature.components.AppTextField
import kmpformvalidation.composeapp.generated.resources.Res
import kmpformvalidation.composeapp.generated.resources.already_have_account
import kmpformvalidation.composeapp.generated.resources.confirm_password_label
import kmpformvalidation.composeapp.generated.resources.confirm_password_placeholder
import kmpformvalidation.composeapp.generated.resources.email_label
import kmpformvalidation.composeapp.generated.resources.email_placeholder
import kmpformvalidation.composeapp.generated.resources.full_name_label
import kmpformvalidation.composeapp.generated.resources.full_name_placeholder
import kmpformvalidation.composeapp.generated.resources.login_link
import kmpformvalidation.composeapp.generated.resources.password_label
import kmpformvalidation.composeapp.generated.resources.password_placeholder
import kmpformvalidation.composeapp.generated.resources.register_button
import kmpformvalidation.composeapp.generated.resources.register_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
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
                onValueChange = { fullName = it },
                label = stringResource(Res.string.full_name_label),
                placeholder = stringResource(Res.string.full_name_placeholder),
                keyboardType = KeyboardType.Text
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Email Field
            AppTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(Res.string.email_label),
                placeholder = stringResource(Res.string.email_placeholder),
                keyboardType = KeyboardType.Email
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Password Field
            AppPasswordField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(Res.string.password_label),
                placeholder = stringResource(Res.string.password_placeholder)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Confirm Password Field
            AppPasswordField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = stringResource(Res.string.confirm_password_label),
                placeholder = stringResource(Res.string.confirm_password_placeholder)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Register Button
            AppButton(
                text = stringResource(Res.string.register_button),
                onClick = {
                    // TODO: Implement register functionality
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Navigate back to Login
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
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
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
