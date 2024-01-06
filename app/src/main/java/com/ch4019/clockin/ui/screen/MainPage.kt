package com.ch4019.clockin.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cn.leancloud.LCUser
import com.ch4019.clockin.R
import com.ch4019.clockin.config.MainNavRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainPage(
    navController: NavHostController
) {
//    val userName = LCUser.getCurrentUser().username ?:"111"
    val pageState = rememberPagerState(0) { 3 }
    val currentPage = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "ClockIn")},
                actions = {
                    when(currentPage.intValue){
                        0 ->{
                            IconButton(
                                onClick = {
                                    navController.navigate(MainNavRoute.CLOCKIN_PAGE) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_public_scan_filled),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        1 ->{}
                        2 ->{
                            IconButton(
                                onClick = {
                                    navController.navigate(MainNavRoute.LOGIN_PAGE) {
                                        popUpTo(MainNavRoute.MAIN_PAGE) {
                                            saveState = true
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                    LCUser.logOut()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.loader_line),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
            ){
                NavigationBarItem(
                    selected = currentPage.intValue == 0,
                    onClick = {
                        scope.launch(scope.coroutineContext) {
                            currentPage.intValue = 0
                            pageState.animateScrollToPage(0)
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.qr_scan_line),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(text = "签到") },
                    alwaysShowLabel = false,
                )
                NavigationBarItem(
                    selected = currentPage.intValue == 1,
                    onClick = {
                        scope.launch(scope.coroutineContext) {
                            currentPage.intValue = 1
                            pageState.animateScrollToPage(1)
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.file_list_line),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(text = "活动") },
                    alwaysShowLabel = false
                )
                NavigationBarItem(
                    selected = currentPage.intValue == 2,
                    onClick = {
                        scope.launch(scope.coroutineContext) {
                            currentPage.intValue = 2
                            pageState.animateScrollToPage(2)
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.map_pin_user_line),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(text = "我的") },
                    alwaysShowLabel = false
                )
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pageState,
                userScrollEnabled = false
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    when (pageState.currentPage) {
                        0 -> {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "登录成功1",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                        1 -> {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "登录成功2 ",
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                        2 -> {
                            UserPage()
                        }
                    }
                }
            }
        }
    }
}
