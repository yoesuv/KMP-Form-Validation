package com.yoesuv.kmpformvalidation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yoesuv.kmpformvalidation.core.route.AppRoute
import com.yoesuv.kmpformvalidation.feature.login.LoginScreen
import com.yoesuv.kmpformvalidation.feature.register.RegisterScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppRoute.Login
        ) {
            composable<AppRoute.Login> {
                LoginScreen(
                    onNavigateToRegister = {
                        navController.navigate(AppRoute.Register)
                    }
                )
            }

            composable<AppRoute.Register> {
                RegisterScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}