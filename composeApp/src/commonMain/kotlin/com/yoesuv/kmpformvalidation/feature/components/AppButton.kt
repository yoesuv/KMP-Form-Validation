package com.yoesuv.kmpformvalidation.feature.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Button style variants for AppButton
 */
enum class AppButtonStyle {
    FILLED,      // Primary filled button
    OUTLINED,    // Outlined button
    TEXT,        // Text button
    TONAL        // Filled tonal button
}

/**
 * Button size variants for AppButton
 */
enum class AppButtonSize {
    SMALL,       // Compact size
    MEDIUM,      // Default size
    LARGE        // Larger size
}

/**
 * Reusable button component for the application
 * Compatible with both Android and iOS platforms
 *
 * @param text Button text
 * @param onClick Click callback
 * @param modifier Modifier for styling
 * @param style Button style variant
 * @param size Button size variant
 * @param enabled Whether the button is enabled
 * @param isLoading Whether to show loading indicator
 * @param loadingText Text to show when loading
 * @param leadingIcon Optional leading icon composable
 * @param trailingIcon Optional trailing icon composable
 * @param fillMaxWidth Whether button should fill maximum width
 * @param backgroundColor Custom background color (overrides style)
 * @param contentColor Custom content color (overrides style)
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: AppButtonStyle = AppButtonStyle.FILLED,
    size: AppButtonSize = AppButtonSize.MEDIUM,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    loadingText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    fillMaxWidth: Boolean = true,
    backgroundColor: Color? = null,
    contentColor: Color? = null
) {
    val buttonModifier = if (fillMaxWidth) {
        modifier.fillMaxWidth()
    } else {
        modifier
    }
    
    val (height, contentPadding, textStyle) = when (size) {
        AppButtonSize.SMALL -> Triple(
            36.dp,
            PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            MaterialTheme.typography.labelMedium
        )
        AppButtonSize.MEDIUM -> Triple(
            48.dp,
            PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            MaterialTheme.typography.labelLarge
        )
        AppButtonSize.LARGE -> Triple(
            56.dp,
            PaddingValues(horizontal = 32.dp, vertical = 16.dp),
            MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
        )
    }
    
    val buttonContent: @Composable RowScope.() -> Unit = {
        ButtonContent(
            text = if (isLoading && !loadingText.isNullOrBlank()) loadingText else text,
            isLoading = isLoading,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = textStyle
        )
    }
    
    val colors = when {
        backgroundColor != null && contentColor != null -> ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.12f),
            disabledContentColor = contentColor.copy(alpha = 0.38f)
        )
        else -> when (style) {
            AppButtonStyle.FILLED -> ButtonDefaults.buttonColors()
            AppButtonStyle.OUTLINED -> ButtonDefaults.outlinedButtonColors()
            AppButtonStyle.TEXT -> ButtonDefaults.textButtonColors()
            AppButtonStyle.TONAL -> ButtonDefaults.filledTonalButtonColors()
        }
    }
    
    when (style) {
        AppButtonStyle.FILLED -> {
            Button(
                onClick = onClick,
                modifier = buttonModifier.height(height),
                enabled = enabled && !isLoading,
                colors = colors,
                contentPadding = contentPadding,
                shape = RoundedCornerShape(12.dp),
                content = buttonContent
            )
        }
        AppButtonStyle.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                modifier = buttonModifier.height(height),
                enabled = enabled && !isLoading,
                colors = colors,
                contentPadding = contentPadding,
                shape = RoundedCornerShape(12.dp),
                content = buttonContent
            )
        }
        AppButtonStyle.TEXT -> {
            TextButton(
                onClick = onClick,
                modifier = buttonModifier.height(height),
                enabled = enabled && !isLoading,
                colors = colors,
                contentPadding = contentPadding,
                shape = RoundedCornerShape(12.dp),
                content = buttonContent
            )
        }
        AppButtonStyle.TONAL -> {
            FilledTonalButton(
                onClick = onClick,
                modifier = buttonModifier.height(height),
                enabled = enabled && !isLoading,
                colors = colors,
                contentPadding = contentPadding,
                shape = RoundedCornerShape(12.dp),
                content = buttonContent
            )
        }
    }
}

@Composable
private fun ButtonContent(
    text: String,
    isLoading: Boolean,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    textStyle: androidx.compose.ui.text.TextStyle
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp
            )
            if (text.isNotBlank()) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        } else if (leadingIcon != null) {
            leadingIcon()
            Spacer(modifier = Modifier.width(8.dp))
        }
        
        if (text.isNotBlank()) {
            Text(
                text = text,
                style = textStyle
            )
        }
        
        if (!isLoading && trailingIcon != null) {
            Spacer(modifier = Modifier.width(8.dp))
            trailingIcon()
        }
    }
}
