package com.nancy.shopbee.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class ShopBeeBtmNavItems(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

val listofItems: List<ShopBeeBtmNavItems> =
    listOf(
        ShopBeeBtmNavItems(
            label = "Home",
            icon = Icons.Default.Home,
            route = Screens.HomeScreen.name,
        ),
        ShopBeeBtmNavItems(
            label = "Favorite",
            icon = Icons.Default.Favorite,
            route = Screens.FavoriteScreen.name,
        ),
        ShopBeeBtmNavItems(
            label = "Account",
            icon = Icons.Default.Person,
            route = Screens.AccountScreen.name,
        ),
    )
