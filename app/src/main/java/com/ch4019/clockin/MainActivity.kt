package com.ch4019.clockin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ch4019.clockin.config.MainNavRoute
import com.ch4019.clockin.ui.screen.ClockInPage
import com.ch4019.clockin.ui.screen.LoginPage
import com.ch4019.clockin.ui.screen.MainPage
import com.ch4019.clockin.ui.screen.RegisterPage
import com.ch4019.clockin.ui.screen.StartPage
import com.ch4019.clockin.ui.screen.UserPage
import com.ch4019.clockin.ui.screen.UserQRPage
import com.ch4019.clockin.ui.theme.ClockinTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置全屏
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ClockinTheme {
                val navController = rememberNavController()
                val currentPage = remember { mutableIntStateOf(0) }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = MainNavRoute.START_PAGE,
                        enterTransition = {
                            slideInHorizontally(animationSpec = tween(300), initialOffsetX = { it })
                        },
                        exitTransition = {
                            slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { -it })
                        },
                        popEnterTransition = {
                            slideInHorizontally(animationSpec = tween(300), initialOffsetX = { -it })
                        },
                        popExitTransition = {
                            slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { it })
                        }
                    ) {
                        composable(MainNavRoute.START_PAGE){
                            StartPage(navController)
                        }
                        composable(MainNavRoute.LOGIN_PAGE){
                            LoginPage(navController)
                        }
                        composable(MainNavRoute.REGISTER_PAGE){
                            RegisterPage(navController)
                        }
                        composable(MainNavRoute.MAIN_PAGE){
                            MainPage(navController,currentPage)
                        }
                        composable(MainNavRoute.CLOCKIN_PAGE){
                            ClockInPage(navController)
                        }
                        composable(MainNavRoute.USER_QR_PAGE){
                            UserQRPage(navController)
                        }
                    }
                }
                // CAMERA_REQ_CODE为用户自定义，用于接收权限校验结果的请求码
//                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE), CAMERA_REQ_CODE)
            }
        }
    }
}