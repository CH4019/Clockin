package com.ch4019.clockin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch4019.clockin.R
import com.ch4019.clockin.ui.theme.ClockinTheme


//开发中
@Composable
fun UserQRPage() {
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
                        painter = painterResource(R.drawable.ic_public_input_code),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Cyan
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Card (
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    )
                ){
                    Column {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            text = "00:10"
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
        UserQRPage()
    }
}