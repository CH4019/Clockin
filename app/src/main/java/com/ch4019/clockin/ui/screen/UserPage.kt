package com.ch4019.clockin.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ch4019.clockin.R
import com.ch4019.clockin.config.MainNavRoute
import com.ch4019.clockin.ui.components.UserCard

@Composable
fun UserPage(
    navController: NavHostController
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
       UserCard(
           topPadding = 16.dp,
           userIcon = R.drawable.map_pin_user_fill,
           userName = "UserName",
           userId = "001",
           userShow = "纵然世间黑暗 仍有一点星光",
           onClick = {},
           onEditClick = {},
           onQRClick = {
               navController.navigate(MainNavRoute.USER_QR_PAGE) {
                   launchSingleTop = true
                   restoreState = true
               }
           }
       )
        Spacer(modifier = Modifier.height(16.dp))
    }
}