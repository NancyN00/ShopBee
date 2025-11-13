package com.nancy.shopbee.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nancy.shopbee.presentation.screens.account.AccountScreen
import com.nancy.shopbee.presentation.screens.favorite.FavoriteScreen
import com.nancy.shopbee.presentation.screens.home.HomeScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShopBeeNavigation() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listofItems.forEach { bottomNav ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == bottomNav.route } == true,
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
                                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                    )
                }
            }
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
        ) {
            composable(route = Screens.HomeScreen.name) {
                HomeScreen()
            }

            composable(route = Screens.FavoriteScreen.name) {
                FavoriteScreen()
            }

            composable(route = Screens.AccountScreen.name) {
                AccountScreen()
            }
        }
    }
}
