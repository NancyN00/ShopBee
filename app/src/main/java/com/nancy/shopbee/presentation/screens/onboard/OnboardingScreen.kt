package com.nancy.shopbee.presentation.screens.onboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nancy.shopbee.presentation.components.IndicatorUi
import com.nancy.shopbee.presentation.components.OnboardingButtonUi
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pages =
        listOf(
            OnboardingModel.FirstOnboardingPage,
            OnboardingModel.SecondOnboardingPage,
            OnboardingModel.ThirdOnboardingPage,
        )

    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val scope = rememberCoroutineScope()

    val buttonState =
        remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Start")
                    else -> listOf("", "")
                }
            }
        }

    // A Solid Column mapping the entire screen makes sure nothing floats
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
    ) {
        // The Pager gets 'weight(1f)' to consume exactly all available space,
        // aggressively pushing the BottomBar down.
        OnboardingPager(
            pages = pages,
            pagerState = pagerState,
            modifier = Modifier.weight(1f),
        )

        // The BottomBar stays rigidly at the bottom
        OnboardingBottomBar(
            pagerState = pagerState,
            buttonState = buttonState.value,
            pageCount = pages.size,
            onFinished = onFinished,
            scope = scope,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingPager(
    pages: List<OnboardingModel>,
    pagerState: androidx.compose.foundation.pager.PagerState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        // Setting fillMaxSize forces it to use the weight allotted to it
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { index ->
            OnboardingItemUi(onboardingModel = pages[index])
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingBottomBar(
    pagerState: androidx.compose.foundation.pager.PagerState,
    buttonState: List<String>,
    pageCount: Int,
    onFinished: () -> Unit,
    scope: kotlinx.coroutines.CoroutineScope,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .navigationBarsPadding() // Keep this so buttons don't overlap system Android bar
                .padding(horizontal = 24.dp, vertical = 16.dp),
        // <--- Change vertical = 0.dp if you want it mathematically flush
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // Back button
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (buttonState[0].isNotEmpty()) {
                OnboardingButtonUi(
                    text = buttonState[0],
                ) {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                }
            }
        }

        // Indicator
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            IndicatorUi(pageSize = pageCount, currentPage = pagerState.currentPage)
        }

        // Next / Start button
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            OnboardingButtonUi(
                text = buttonState[1],
            ) {
                scope.launch {
                    if (pagerState.currentPage < pageCount - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        onFinished()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen {}
}
