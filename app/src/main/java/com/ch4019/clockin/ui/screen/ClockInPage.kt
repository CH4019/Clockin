package com.ch4019.clockin.ui.screen

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.ch4019.clockin.ui.components.ScanViewTwo
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClockInPage(
    navController: NavHostController
) {
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val handler = remember { Handler(Looper.getMainLooper()) }
    var scanResult by remember { mutableStateOf("") }
    var isScanning by remember { mutableStateOf(true) }
    var isShowResult by remember { mutableStateOf(false) }

    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = withContext(Dispatchers.IO) {
            cameraProviderFuture.get()
        }
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }
        //val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        // 延迟启动预览
        handler.postDelayed({
            cameraProvider.unbindAll()
            val camera =  cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imageAnalysis, preview)
            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context)) { image ->
                // 处理图像
//                TODO 这里在页面中重新扫描并没有实现重新扫描功能
                if (isScanning){
                    val result = processImage(image, context, camera)
                    isShowResult =  if(result == null){
                        false
                    }else{
                        isScanning = false
                        scanResult = result
                        true
                    }
                }
            }
        }, 1350)
    }
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(332.dp),
                shape = RoundedCornerShape(25.dp),
            ) {
                Box (
                    modifier = Modifier
                        .size(332.dp),
                    contentAlignment = Alignment.Center
                ){
                    Box{
                        AndroidView(
                            factory = { previewView },
                            modifier = Modifier
                                .size(300.dp)
                                .clip(RoundedCornerShape(25f))
                                .clipToBounds(),
                        )
                    }
                    Box {
                        ScanViewTwo()
                    }
                }

            }
            Spacer(modifier = Modifier.weight(1f))
            if (isShowResult){
                Text(text = scanResult)
                Spacer(modifier = Modifier.weight(1f))
            }
            Row (
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                    state = rememberTooltipState(),
                    tooltip = {
                        PlainTooltip {
                            Text(text = "重新扫描")
                        }
                    }
                ) {
                    FilledTonalButton(
                        modifier = Modifier.padding(bottom = 56.dp),
                        shape = RoundedCornerShape(15.dp),
                        onClick = {
                            scanResult = ""
                            isShowResult = false
                            isScanning = true
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            text = "重新扫码",
                        )
                    }
                }
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                    state = rememberTooltipState(),
                    tooltip = {
                        PlainTooltip {
                            Text(text = "扫描成功后点击打卡")
                        }
                    }
                ) {
                    FilledTonalButton(
                        modifier = Modifier.padding(bottom = 56.dp),
                        shape = RoundedCornerShape(15.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                            text = "打卡",
                        )
                    }
                }
            }
        }
    }
}

fun processImage(image: ImageProxy, context: Context, camera: Camera): String? {
    val rotationDegrees = image.imageInfo.rotationDegrees
    // 将 ImageProxy 转换为 Bitmap
    val bitmap = image.toBitmap()
    image.close()
    // 创建扫描选项
    val options = HmsScanAnalyzerOptions
        .Creator()
        .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE, HmsScan.DATAMATRIX_SCAN_TYPE)
        .setPhotoMode(false)
        .create()
    // 执行扫描
    val hmsScans = ScanUtil.decodeWithBitmap(context, bitmap, options)
    // 处理扫描结果
   return handleScanResults(hmsScans, camera)
}

fun handleScanResults(hmsScans: Array<HmsScan>?, camera: Camera): String? {
    // 扫码成功时处理解码结果
    if (!hmsScans.isNullOrEmpty() && !TextUtils.isEmpty(hmsScans[0].getOriginalValue())) {
        // 展示扫码结果
        return hmsScans[0].getOriginalValue()
    }
    // zoomValue大于1.0时，根据getZoomValue()调整焦距重新扫码
    if (!hmsScans.isNullOrEmpty() && TextUtils.isEmpty(hmsScans[0].getOriginalValue()) && hmsScans[0].getZoomValue() != 1.0) {
        adjustZoom(hmsScans[0].getZoomValue(), camera)
    }
    return null
}

fun adjustZoom(zoomValue: Double, camera: Camera) {
    val maxZoomRatio = camera.cameraInfo.zoomState.value?.maxZoomRatio ?: 1.0f
    val minZoomRatio = camera.cameraInfo.zoomState.value?.minZoomRatio ?: 1.0f
    val zoomRatioInterval = 0.1f
    val zoomRatios = ArrayList<Float>()
    // 使用一个循环，从最小缩放比例开始，每次增加缩放比例间隔，直到达到或超过最大缩放比例
    var zoomRatio: Float = minZoomRatio
    while (zoomRatio <= maxZoomRatio) {
        // 将每个缩放比例添加到列表中
        zoomRatios.add(zoomRatio)
        zoomRatio += zoomRatioInterval
    }
    val zoomIn = if (zoomValue >= maxZoomRatio){
        zoomRatios.size - 1
    }else if (zoomValue in minZoomRatio..maxZoomRatio){
        zoomRatios.indexOfFirst { it >= zoomValue }
    }else{ -1 }
    val zoom = if(zoomIn != -1)  zoomRatios[zoomIn] else minZoomRatio
    // 设置焦距
    camera.cameraControl.setZoomRatio(zoom)
}
