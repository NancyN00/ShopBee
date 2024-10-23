package com.nancy.shopbee.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItems(
    val title: String,
    val icon: ImageVector,
    val route: String
)

val listOfItems: List<BottomNavItems> = listOf(

    BottomNavItems(
        title = "Home",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name
    ),

    BottomNavItems(
        title = "Cart",
        icon = Icons.Default.ShoppingCart,
        route = Screens.CartScreen.name
    ),
    BottomNavItems(
        title = "Favorite",
        icon = Icons.Default.FavoriteBorder,
        route = Screens.FavoriteScreen.name
    ),
    BottomNavItems(
        title = "Profile",
        icon = Icons.Default.Person,
        route = Screens.ProfileScreen.name
    )


)
