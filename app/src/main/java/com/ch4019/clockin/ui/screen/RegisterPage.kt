package com.ch4019.clockin.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cn.leancloud.LCUser
import com.ch4019.clockin.R
import com.ch4019.clockin.ui.components.TextFieldView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

@Composable
fun RegisterPage(navController: NavHostController) {
    var userName by remember { mutableStateOf("") }
    var passWord by remember { mutableStateOf("") }
    val context = LocalContext.current
    var usePasswordVisualTransformation by remember { mutableStateOf(true) }
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .padding(top = 32.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "创建你的 ClockIn ID",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(48.dp))
            TextFieldView(
                value = userName,
                onValueChange = { newValue ->
                    userName = newValue
                },
                shape = RoundedCornerShape(15.dp),
                singleLine = true,
                label = { Text(text = "UserName") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.map_pin_user_fill),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldView(
                value = passWord,
                onValueChange = { newValue ->
                    passWord = newValue
                },
//                这个imePadding()会导致点击输入出现按钮下浮动画
                modifier = Modifier
                    .imePadding(),
                shape = RoundedCornerShape(15.dp),
                singleLine = true,
                visualTransformation = if (usePasswordVisualTransformation) {
                    PasswordVisualTransformation('*')
                } else {
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                label = { Text(text = "Password") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.key_fill),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            usePasswordVisualTransformation = !usePasswordVisualTransformation
                        }
                    ) {
                        Icon(
                            painter = if (usePasswordVisualTransformation) {
                                painterResource(R.drawable.eye_off_fill)
                            } else {
                                painterResource(R.drawable.eye_fill)
                            },
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilledTonalButton(
                    onClick = {
                        LCUser().apply{
                            username = userName
                            password = passWord
                            signUpInBackground().subscribe(object : Observer<LCUser> {
                                override fun onSubscribe(d: Disposable) {}
                                override fun onNext(t: LCUser) {
                                    // 注册成功
                                    Toast.makeText(
                                        context,
                                        "注册成功，请重新登录",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigateUp()
                                }
                                override fun onError(e: Throwable) {
                                    // 注册失败（通常是因为用户名已被使用）
                                    Toast.makeText(
                                        context,
                                        "${ e.message }",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                override fun onComplete() {}
                            })
                        }
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Register",
                        modifier = Modifier
                            .padding(horizontal = 64.dp)
                    )
                }
            }
        }
    }
}
