package com.nancy.shopbee.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nancy.shopbee.presentation.screens.account.AccountScreen
import com.nancy.shopbee.presentation.screens.account.SettingsScreen
import com.nancy.shopbee.presentation.screens.auth.signin.SignInScreen
import com.nancy.shopbee.presentation.screens.auth.signup.SignUpScreen
import com.nancy.shopbee.presentation.screens.auth.socialmedia.tel.OtpVerifyScreen
import com.nancy.shopbee.presentation.screens.auth.socialmedia.tel.PhoneEntryScreen
import com.nancy.shopbee.presentation.screens.favorite.FavoriteScreen
import com.nancy.shopbee.presentation.screens.home.HomeScreen
import com.nancy.shopbee.presentation.screens.home.details.ProductDetailsScreen
import com.nancy.shopbee.presentation.screens.onboard.OnboardingScreen
import com.nancy.shopbee.utils.OnboardingUtils

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShopBeeNavigation(
    showOnboarding: Boolean,
    onboardingUtils: OnboardingUtils,
    isSignedIn: Boolean,
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val startDestination =
        getStartDestination(
            showOnboarding = showOnboarding,
            isSignedIn = isSignedIn,
        )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (shouldShowBottomBar(currentRoute)) {
                ShopBeeBottomBar(
                    navController = navController,
                    currentRoute = currentRoute,
                )
            }
        },
    ) { paddingValues ->
        ShopBeeNavHost(
            navController = navController,
            startDestination = startDestination,
            onboardingUtils = onboardingUtils,
            paddingValues = paddingValues,
        )
    }
}

private fun getStartDestination(
    showOnboarding: Boolean,
    isSignedIn: Boolean,
): String =
    when {
        showOnboarding -> Screens.OnboardScreen.name
        isSignedIn -> Screens.HomeScreen.name
        else -> Screens.LoginScreen.name
    }

private fun shouldShowBottomBar(route: String?): Boolean =
    route in
        listOf(
            Screens.HomeScreen.name,
            Screens.FavoriteScreen.name,
            Screens.AccountScreen.name,
            Screens.SettingScreen.name,
        )

@Composable
private fun ShopBeeBottomBar(
    navController: NavHostController,
    currentRoute: String?,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        listofItems.forEach { bottomNav ->

            val selected =
                currentRoute == bottomNav.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(bottomNav.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = bottomNav.icon,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(text = bottomNav.label)
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedIconColor =
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedTextColor =
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
            )
        }
    }
}

@Composable
private fun ShopBeeNavHost(
    navController: NavHostController,
    startDestination: String,
    onboardingUtils: OnboardingUtils,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
    ) {
        composable(Screens.OnboardScreen.name) {
            OnboardingScreen {
                onboardingUtils.setOnboardingCompleted()

                navController.navigate(Screens.LoginScreen.name) {
                    popUpTo(Screens.OnboardScreen.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(Screens.HomeScreen.name) {
            HomeScreen(navController)
        }

        composable(
            route = "${Screens.ProductDetailsScreen.name}/{productId}",
            arguments =
                listOf(
                    navArgument("productId") {
                        type = NavType.IntType
                    },
                ),
        ) { backStackEntry ->

            val productId =
                backStackEntry.arguments?.getInt("productId") ?: 0

            ProductDetailsScreen(
                productId = productId,
                navController = navController,
            )
        }

        composable(Screens.FavoriteScreen.name) {
            FavoriteScreen(
                navController = navController,
            )
        }

        composable(Screens.AccountScreen.name) {
            AccountScreen(navController)
        }

        composable(Screens.SettingScreen.name) {
            SettingsScreen(navController)
        }

        composable(Screens.LoginScreen.name) {
            SignInScreen(navController)
        }

        composable(Screens.RegScreen.name) {
            SignUpScreen(navController)
        }

        composable(Screens.PhoneEntrScreen.route) {
            PhoneEntryScreen(navController)
        }

        composable(Screens.OtpVerScreen.route) {
            OtpVerifyScreen(navController)
        }
    }
}
