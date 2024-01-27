package com.ch4019.clockin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch4019.clockin.R
import com.ch4019.clockin.ui.theme.ClockinTheme

@Composable
fun UserCard(
    topPadding: Dp = 0.dp,
    userIcon: Int,
    userName: String,
    userId: String,
    userShow: String,
    onClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onQRClick: () -> Unit = {},
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(top = topPadding),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
            ) {
                Surface(
                    modifier = Modifier
                        .size(48.dp),
                    shape = RoundedCornerShape(50.dp),
                    onClick = { onClick() }
                ) {
                    Image(
                        painterResource(id =  userIcon),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    Modifier.fillMaxWidth()
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = userName,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Card(
                                modifier = Modifier.padding(start = 2.dp,top = 1.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFB388FF)
                                ),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(start = 2.dp, end = 1.dp, top = 1.dp),
                                    text = "#$userId",
                                    fontSize = 10.sp,
                                    lineHeight = 12.sp,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        IconButton(
                            modifier = Modifier
                                .size(24.dp),
                            onClick = onQRClick
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_public_input_code),
                                contentDescription = null
                            )
                        }
                    }
                    Row (
                        modifier = Modifier.clickable { onEditClick() },
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            modifier = Modifier
                                .size(16.dp),
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = userShow,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Light
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    ClockinTheme {
        UserCard(
            userIcon = R.drawable.map_pin_user_fill,
            userName = "User Name",
            userId = "0001",
            userShow = "User Show",
        )
    }
}