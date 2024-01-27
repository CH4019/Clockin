package com.ch4019.clockin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch4019.clockin.R
import com.ch4019.clockin.ui.theme.ClockinTheme
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsBuildBitmapOption
import com.huawei.hms.ml.scan.HmsScan


//开发中
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserQRPage(
    navController: NavHostController
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "123")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_public_arrow_left_filled),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        },
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            UserQRView()
        }
    }
}

@Composable
fun UserQRView() {

    val content = "我喜欢你"
    val type = HmsScan.QRCODE_SCAN_TYPE
    val width = 400
    val height = 400
    val options = HmsBuildBitmapOption.Creator().setBitmapBackgroundColor(Color.Transparent.toArgb()).setBitmapColor(
        MaterialTheme.colorScheme.primary.toArgb()
    ).setBitmapMargin(3).create()
    val qrBitmap = ScanUtil.buildBitmap(content, type, width, height, options)

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Card (
            shape = RoundedCornerShape(15.dp),
        ){
            Column (
                modifier = Modifier.size(width = 300.dp, height = 350.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        modifier = Modifier.size(280.dp),
                        bitmap = qrBitmap.asImageBitmap(),
                        contentDescription = null
                    )
//                    Image(
//                        modifier = Modifier.size(280.dp),
//                        painter = painterResource(
//                            R.drawable.ic_public_input_code
//                        ),
//                        contentDescription = null
//                    )
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Cyan
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Card (
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    )
                ){
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

//                       TODO 需要添加时间过渡切换动画
                        Text(
                            modifier = Modifier
                                .padding(vertical = 4.dp),
                            text = "0"
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 4.dp),
                            text = "0"
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 4.dp),
                            text = ":"
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 4.dp),
                            text = "1"
                        )
                        Text(
                            modifier = Modifier
                                .padding(vertical = 4.dp),
                            text = "0"
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        FilledTonalButton(
            onClick = {},
            shape = RoundedCornerShape(10.dp)
        ){
            Text(text = "刷新")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserQRPagePreview() {
    ClockinTheme {
        UserQRPage(
            navController = rememberNavController()
        )
    }
}