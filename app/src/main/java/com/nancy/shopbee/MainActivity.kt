package com.nancy.shopbee

import ShopBeeBottomNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nancy.shopbee.presentation.auth.LoginScreen
import com.nancy.shopbee.presentation.auth.RegistrationScreen
import com.nancy.shopbee.presentation.onboard.OnboardScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {

            val navController = rememberNavController()
            ShopBeeNavControl(navController = navController)
        }
    }
}

@Composable
fun ShopBeeNavControl(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "onboard") {

        composable(route = "onboard") {
            OnboardScreen(
                navigateToLogin = {
                    navController.navigate("login") {
                        launchSingleTop = true
                        popUpTo("onboard") {
                            inclusive = true
                        }
                    }
                },
                navigateToSignUp = {
                    navController.navigate("signup") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(route = "signin") {
            LoginScreen(
                navigateToHome = {
                    navController.navigate("home") {
                        launchSingleTop = true
                        popUpTo("signin") {
                            inclusive = true
                        }
                    }
                },
                navigateToSignUp = {
                    navController.navigate("signup") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(route = "signup") {
            RegistrationScreen(
                navController
            ) {
                navController.navigate("signin") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        composable(route = "home") {
            ShopBeeBottomNavigation()
        }
    }

}
