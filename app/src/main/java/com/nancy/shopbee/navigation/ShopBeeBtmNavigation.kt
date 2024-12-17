import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nancy.shopbee.navigation.Screens
import com.nancy.shopbee.navigation.listOfItems
import com.nancy.shopbee.presentation.favorite.FavoriteScreen
import com.nancy.shopbee.presentation.home.HomeScreen
import com.nancy.shopbee.presentation.profile.ProfileScreen
import com.nancy.shopbee.ui.theme.Purple40

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShopBeeBottomNavigation() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOfItems.forEach { bottomNav ->

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
                                selectedIconColor = Purple40,
                                unselectedIconColor = Color.Black,
                                selectedTextColor = Color.Black,
                                unselectedTextColor = Purple40,
                            ),
                    )
                }
            }
        },
    ) { paddingValues ->

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

            composable(route = Screens.ProfileScreen.name) {
                ProfileScreen()
            }
        }
    }
}
