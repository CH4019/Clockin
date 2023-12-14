package com.ch4019.clockin.ui.screen

import android.animation.ValueAnimator
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import cn.leancloud.LCUser
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ch4019.clockin.R
import com.ch4019.clockin.config.MainNavRoute

@Composable
fun StartPage(navController: NavHostController) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.infinity_loop))
    val progress by animateLottieCompositionAsState(composition)

//    LaunchedEffect(progress) {
//        when (progress) {
//            1f -> {
//                navController.navigate(MainNavRoute.LOGIN_PAGE) {
//                    popUpTo(MainNavRoute.START_PAGE) {
//                        saveState = true
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                    restoreState = true
//                }
//            }
//        }
//    }

    DisposableEffect(progress) {
        val listener = ValueAnimator.AnimatorUpdateListener { animation ->
            val updatedProgress = animation.animatedValue as Float
            if (updatedProgress >= 1f) {
                navController.navigate(
                    if (LCUser.getCurrentUser() != null) MainNavRoute.MAIN_PAGE else MainNavRoute.LOGIN_PAGE
                ) {
                    popUpTo(MainNavRoute.START_PAGE) {
                        saveState = true
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        val animator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener(listener)
//            动画时长的获取需要优化
//            duration = composition?.durationFrames?.toLong() ?: 0 // 设置适当的动画时长
            start()
        }
        onDispose {
            animator.removeAllUpdateListeners()
            animator.cancel()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
            )
        }
    }
}