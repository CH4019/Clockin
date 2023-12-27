package com.ch4019.clockin.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.ch4019.clockin.config.MainNavRoute
import com.ch4019.clockin.ui.components.TextFieldView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginPage(navController: NavHostController) {
    var userName by remember { mutableStateOf("") }
    var passWord by remember { mutableStateOf("") }
    val context = LocalContext.current
    var usePasswordVisualTransformation by remember { mutableStateOf(true) }
    val hostState = remember { SnackbarHostState() }
    val  scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = hostState,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                snackbar = {
                    Snackbar (
                        shape = RoundedCornerShape(15.dp),
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ){
                        Text(text = it.visuals.message)
                    }
                }
            )
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .padding(top = 32.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "ClockIn ID",
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "登录你的 ClockIn 账户",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(32.dp))
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
                shape = RoundedCornerShape(15.dp),
                singleLine = true,
                visualTransformation = if (usePasswordVisualTransformation){
                    PasswordVisualTransformation('*')
                }else{
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
                        onClick = { usePasswordVisualTransformation = !usePasswordVisualTransformation }
                    ) {
                        Icon(
                            painter = if (usePasswordVisualTransformation){
                                painterResource(R.drawable.eye_off_fill)
                            }else{
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
                        navController.navigate(MainNavRoute.REGISTER_PAGE){
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Register")
                }
                FilledTonalButton(
                    onClick = {
                        when{
                            userName.length < 4 ->{
                                scope.launch (Dispatchers.IO){
                                    hostState.showSnackbar("请输入4位以上字符用户名",duration= SnackbarDuration.Short)
                                }
                            }
                            passWord.length < 6 ->{
                                scope.launch (Dispatchers.IO){
                                    hostState.showSnackbar("请输入6位以上字符密码",duration= SnackbarDuration.Short)
                                }
                            }
                            else ->{
                                LCUser.logIn(userName, passWord).subscribe(object : Observer<LCUser> {
                                        override fun onSubscribe(d: Disposable) {}
                                        override fun onNext(t: LCUser) {
                                            navController.navigate(MainNavRoute.MAIN_PAGE) {
                                                popUpTo(MainNavRoute.LOGIN_PAGE) {
                                                    saveState = true
                                                    inclusive = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }

                                        override fun onError(e: Throwable) {
                                            Toast.makeText(
                                                context,
                                                "${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        override fun onComplete() {}
                                    })
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}


