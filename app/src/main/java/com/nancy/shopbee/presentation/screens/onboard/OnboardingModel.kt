package com.nancy.shopbee.presentation.screens.onboard

import com.nancy.shopbee.R

sealed class OnboardingModel(
    val title: String,
    val desc: String,
    val img: Int
) {

    data object FirstOnboardingPage : OnboardingModel(
        title = "Find best deals",
        desc = "Best deals in clothes, shoes, and jewels in one place",
        img = R.drawable.onboard_discover
    )

    data object SecondOnboardingPage : OnboardingModel(
        title = "Save what you love",
        desc = "Manage your cart with one tap",
        img = R.drawable.onboard_cart
    )

    data object ThirdOnboardingPage : OnboardingModel(
        title = "Pay safely",
        desc = "Pay safely with trusted method, you are always protected",
        img = R.drawable.onboard_pay
    )
}