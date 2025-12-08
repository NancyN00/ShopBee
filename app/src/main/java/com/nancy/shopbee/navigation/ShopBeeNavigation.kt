package com.nancy.shopbee.navigation

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
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
import com.nancy.shopbee.presentation.screens.auth.login.LoginScreen
import com.nancy.shopbee.presentation.screens.auth.reg.RegScreen
import com.nancy.shopbee.presentation.screens.favorite.FavoriteScreen
import com.nancy.shopbee.presentation.screens.home.HomeScreen
import com.nancy.shopbee.presentation.screens.home.details.ProductDetailsScreen
import com.nancy.shopbee.presentation.screens.onboard.OnboardingScreen
import com.nancy.shopbee.ui.theme.ShopBeeTheme
import com.nancy.shopbee.utils.OnboardingUtils
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShopBeeNavigation(
    showOnboarding: Boolean,
    onboardingUtils: OnboardingUtils,
    isSignedIn: Boolean
) {
    val navController: NavHostController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    // Hide bottom nav on auth screens
    val showBottomNav = currentDestination in listOf(
        Screens.HomeScreen.name,
        Screens.FavoriteScreen.name,
        Screens.AccountScreen.name,
        Screens.SettingScreen.name
    )

    // Determine start destination based on state
    val startDestination = when {
        showOnboarding -> Screens.OnboardScreen.name
        isSignedIn -> Screens.HomeScreen.name
        else -> Screens.LoginScreen.name
    }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ) {
                    listofItems.forEach { bottomNav ->
                        NavigationBarItem(
                            selected = currentDestination?.let {
                                it == bottomNav.route || navBackStackEntry?.destination?.hierarchy?.any {
                                    it.route == bottomNav.route
                                } == true
                            } ?: false,
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
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(Screens.OnboardScreen.name) {
                OnboardingScreen {
                    onboardingUtils.setOnboardingCompleted()
                    navController.navigate(Screens.LoginScreen.name) {
                        popUpTo(Screens.OnboardScreen.name) { inclusive = true }
                    }
                }
            }

            composable(route = Screens.HomeScreen.name) {
                HomeScreen(navController)
            }

            composable(
                route = "${Screens.ProductDetailsScreen.name}/{productId}",
                arguments = listOf(
                    navArgument("productId") { type = NavType.IntType }
                ),
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                ProductDetailsScreen(
                    productId = productId,
                    navController = navController,
                )
            }

            composable(route = Screens.FavoriteScreen.name) {
                FavoriteScreen(navController = navController)
            }

            composable(route = Screens.AccountScreen.name) {
                AccountScreen(navController = navController)
            }

            composable(route = Screens.SettingScreen.name) {
                SettingsScreen(navController)
            }

            composable(route = Screens.LoginScreen.name) {
                LoginScreen(navController)
            }

            composable(route = Screens.RegScreen.name) {
                RegScreen(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopBeeNavPreview() {
    ShopBeeTheme {
     //   ShopBeeNavigation()
    }
}
