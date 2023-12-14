package com.ch4019.clockin.ui.components

import android.graphics.Typeface
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huawei.hms.scankit.drawable.ScanDrawable


@Composable
fun ScanViewOne(
    modifier: Modifier = Modifier,
    sizeShow: Dp,
    isSpecial: Boolean = false
) {
    var enabled by remember { mutableStateOf(true) }
    SideEffect {
        enabled = false
    }
    val alpha: Float by animateFloatAsState(
        targetValue = if (enabled)0f else 1f,
        // 配置动画持续时间和缓动.
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 150,
        ),
        label = ""
    )
    val alpha0: Float by animateFloatAsState(
        targetValue = if (enabled)0f else 1f,
        // 配置动画持续时间和缓动.
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 10,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    Canvas(modifier = modifier
        .size(sizeShow)
        .onGloballyPositioned {
        }
    ) {
//        准备画笔
        val paint = Paint().asFrameworkPaint()
        paint.apply {
            isAntiAlias = true
            textSize = 24.sp.toPx()
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }

        // 绘制背景
//        drawRect(Color.Transparent.copy(alpha = 0.1f))


        // 扫描框 高度、宽度
        val scanSize = sizeShow.toPx() to sizeShow.toPx()

        // 扫描框 矩形
        val rectF = Rect(
            offset = Offset(16f, 16f),
            size = Size(
                scanSize.second - 32f,
                scanSize.first - 32f
            )
        )

        // 边框
        val lineWith = scanSize.second * 0.02f
        val lineLength = scanSize.second * 0.08f
        val cornerRadius = 35f

        val path = Path()
        // 左上角
        path.addRoundRect(
            RoundRect(
                rectF.left,
                rectF.top,
                rectF.left + lineLength,
                rectF.top + lineWith,
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
                CornerRadius.Zero
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.left,
                rectF.top,
                rectF.top + lineWith,
                rectF.left + lineLength,
                CornerRadius(cornerRadius),
                CornerRadius.Zero,
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius)
            ),
        )
        // 左下角
        path.addRoundRect(
            RoundRect(
                rectF.left,
                rectF.bottom,
                rectF.left + lineLength,
                rectF.bottom - lineWith,
                CornerRadius.Zero,
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.left,
                rectF.bottom - lineLength,
                rectF.left + lineWith,
                rectF.bottom,
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
                CornerRadius.Zero,
                CornerRadius(cornerRadius),
            ),
        )
        // 右上角
        path.addRoundRect(
            RoundRect(
                rectF.right,
                rectF.top,
                rectF.right - lineLength,
                rectF.top + lineWith,
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
                CornerRadius.Zero,
                CornerRadius(cornerRadius),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.right,
                rectF.top,
                rectF.right - lineWith,
                rectF.top + lineLength,
                CornerRadius.Zero,
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius)
            ),
        )
        // 右下角
        path.addRoundRect(
            RoundRect(
                rectF.right - lineLength,
                rectF.bottom - lineWith,
                rectF.right,
                rectF.bottom,
                CornerRadius(cornerRadius),
                CornerRadius.Zero,
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.right - lineWith,
                rectF.bottom - lineLength,
                rectF.right,
                rectF.bottom,
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
                CornerRadius(cornerRadius),
                CornerRadius.Zero,
            ),
        )
        scale(
            if (isSpecial) alpha0 else 1f,
            pivot = Offset(sizeShow.toPx() / 2, sizeShow.toPx() / 2)
        ) {
            drawRoundRect(
                color = Color.Transparent,
                size = Size(scanSize.first, scanSize.second),
                cornerRadius = CornerRadius(25f),
                blendMode = BlendMode.Clear
            )
        }
//        绘制路径
        when (alpha) {
            1f -> {
                drawPath(path = path, color = Color.White)
            }
            else -> {
                scale(
                    alpha,
                    pivot = Offset(sizeShow.toPx() / 2, sizeShow.toPx() / 2)
                ) {
                    drawRoundRect(
                        color = Color.White,
                        topLeft = Offset(rectF.left + 3f, rectF.top + 3f),
                        size = Size(rectF.size.width - 6f, rectF.size.height - 6f),
                        cornerRadius = CornerRadius(15f),
                        style = Stroke(
                            lineWith,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ScanViewTwo() {
    val sizeShow = 300.dp
    var enabled by remember { mutableStateOf(true) }
    SideEffect {
        enabled = false
    }
    ScanDrawable()
    val alpha: Float by animateFloatAsState(
        targetValue = if (enabled)0f else 1f,
        // 配置动画持续时间和缓动.
        animationSpec = tween(
            durationMillis = 750,
            delayMillis = 75,
        ),
        label = ""
    )
    val alpha0: Float by animateFloatAsState(
        targetValue = if (alpha != 1f) 1f else 0f,
        // 配置动画持续时间和缓动.
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 10,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    Canvas(
        modifier = Modifier
            .padding(16.dp)
            .size(300.dp)
            .onGloballyPositioned {
            }
    ){
        //        准备画笔
        val paint = Paint().asFrameworkPaint()
        paint.apply {
            isAntiAlias = true
            textSize = 24.sp.toPx()
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        // 扫描框 高度、宽度
        val scanSize = sizeShow.toPx() to sizeShow.toPx()

        // 扫描框 矩形
        val rectF = Rect(
            offset = Offset(16f, 16f),
            size = Size(
                scanSize.second - 32f,
                scanSize.first - 32f
            )
        )
        val path = Path()
        path.addRoundRect(
            RoundRect(
                rectF.left,
                rectF.top,
                rectF.left + 150.dp.toPx()* alpha0+ 60f,
                rectF.top +20f,
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.left,
                rectF.top,
                rectF.left + 20f,
                rectF.top + 150.dp.toPx()* alpha0+ 60f,
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.left,
                rectF.bottom -60f -150.dp.toPx()* alpha0,
                rectF.left + 20f,
                rectF.bottom,
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.left,
                rectF.bottom -20f,
                rectF.left + 60f +150.dp.toPx()* alpha0,
                rectF.bottom,
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.right -60f - 150.dp.toPx()* alpha0,
                rectF.top,
                rectF.right,
                rectF.top + 20f,
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.right -20f,
                rectF.top,
                rectF.right,
                rectF.top + 60f + 150.dp.toPx()* alpha0,
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.right - 60f - 150.dp.toPx()* alpha0,
                rectF.bottom -20f,
                rectF.right,
                rectF.bottom,
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
            ),
        )
        path.addRoundRect(
            RoundRect(
                rectF.right-20f,
                rectF.bottom -60f  - 150.dp.toPx()* alpha0,
                rectF.right,
                rectF.bottom,
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
                CornerRadius(15f),
            ),
        )
        drawRoundRect(
            color = Color.Transparent,
            size = Size(scanSize.first, scanSize.second),
            cornerRadius = CornerRadius(25f),
            blendMode = BlendMode.Clear
        )
        scale(
            alpha,
            pivot = Offset(sizeShow.toPx() / 2, sizeShow.toPx() / 2)
        ) {
            drawPath(path = path, color = Color.White)
        }
    }
}

@Preview
@Composable
fun ShowScan() {
    ScanViewTwo()
}