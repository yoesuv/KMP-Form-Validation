package com.yoesuv.kmpformvalidation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yoesuv.kmpformvalidation.core.route.AppRoute
import com.yoesuv.kmpformvalidation.core.theme.AppColor
import com.yoesuv.kmpformvalidation.feature.login.LoginScreen
import com.yoesuv.kmpformvalidation.feature.register.RegisterScreen

@Composable
fun App() {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            background = AppColor.Winter
        ),
    ) {
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