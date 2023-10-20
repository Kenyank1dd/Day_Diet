//package com.example.daydiet.ui.camera
//
//import android.annotation.SuppressLint
//import android.graphics.*
//import android.graphics.Color
//import android.graphics.Paint
//import android.util.Log
//import android.util.Size
//import android.widget.ImageView
//import androidx.camera.core.*
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.layout.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.*
//import androidx.compose.ui.graphics.Canvas
//import androidx.compose.ui.graphics.Color.Companion.Blue
//import androidx.compose.ui.graphics.drawscope.DrawStyle
//import androidx.compose.ui.graphics.drawscope.Fill
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.platform.LocalLifecycleOwner
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.LifecycleOwner
//import androidx.navigation.NavHostController
//import com.example.daydiet.R
//import com.example.daydiet.yolov5.Box
//import com.example.daydiet.yolov5.YOLOv5
//import com.google.common.util.concurrent.ListenableFuture
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.InternalCoroutinesApi
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import java.io.ByteArrayOutputStream
//import java.io.IOException
//import java.util.*
//import java.util.concurrent.Executors
//import kotlin.math.abs
//
//var detectService = Executors.newSingleThreadExecutor()
//
//var mutableBitmap: Bitmap? = null
//
//var results: Array<Box?>? = null
//
//var result = mutableStateListOf<Box>()
//
////var results = mutableListOf<Box>()
////var results by remember {
////    mutableStateOf(result)
////}
//
////var mutableBitmap = mutableStateOf(mutableBitmapp)
//
//@SuppressLint("RestrictedApi")
//@Composable
//fun CameraScreen(
//    scope: CoroutineScope = rememberCoroutineScope(),
//    navHostController: NavHostController,
//    onBack: ()->Unit
//) {
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    val context = LocalContext.current
//
//    var cameraSelector by remember {
//        mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)
//    }
//
//    val imageAnalyzer = remember {
//        ImageAnalyzer(scope)
//    }
//
//    val cameraProviderFuture = remember {
//        ProcessCameraProvider.getInstance(context)
//    }
//    val previewView = remember {
//        PreviewView(context)
//            .apply {
//            id = R.id.preview_view
//        }
//    }
//
//    YOLOv5.init(context.assets, false)
//
//    androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxSize()){
//        //相机预览界面
//        CameraPreviewView(
//            modifier = Modifier.fillMaxSize(),
//            lifecycleOwner = lifecycleOwner,
//            cameraProviderFuture = cameraProviderFuture,
//            previewView = previewView,
//            imageAnalyzer = imageAnalyzer,
//            cameraSelector = cameraSelector,
//        )
//
//        //绘制
//        CanvasGrid(cameraSelector)
//    }
//
//    DisposableEffect(key1 = Unit) {
//        onDispose {
//            cameraProviderFuture.get().unbindAll()
//        }
//    }
//}
//
//@OptIn(InternalCoroutinesApi::class)
//@SuppressLint("RestrictedApi", "UnrememberedMutableState")
//@Composable
//fun CameraPreviewView(
//    modifier: Modifier,
//    lifecycleOwner: LifecycleOwner,
//    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
//    previewView: PreviewView,
//    imageAnalyzer: ImageAnalysis.Analyzer,
//    cameraSelector: CameraSelector,
//) {
//    AndroidView(
//        factory = { ctx ->
//            val executor = ContextCompat.getMainExecutor(ctx)
//            cameraProviderFuture.addListener({
//                val cameraProvider = cameraProviderFuture.get()
//                bindCameraUseCases(
//                    cameraProvider,
//                    lifecycleOwner,
//                    cameraSelector,
//                    imageAnalyzer,
//                    previewView,
//                )
//            }, executor)
//            previewView
//        },
//        modifier = modifier,
//    ) {
//
//    }
//}
//
//@SuppressLint("UnsafeOptInUsageError")
//fun bindCameraUseCases(
//    cameraProvider: ProcessCameraProvider,
//    lifecycleOwner: LifecycleOwner,
//    cameraSelector: CameraSelector,
//    imageAnalyzer: ImageAnalysis.Analyzer,
//    previewView: PreviewView,
//) {
//
//    val builder = ImageAnalysis.Builder()
//    val imageAnalysis = builder
////        .setTargetResolution(Size(0, 100))
//        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//        .build()
//        .apply {
//            setAnalyzer(
//                Executors.newSingleThreadExecutor(),
//                imageAnalyzer
//            )
//        }
//    val preview = Preview.Builder().build().also {
//        it.setSurfaceProvider(
//            previewView.surfaceProvider
//        )
//    }
//    try {
//        cameraProvider.unbindAll()
//        cameraProvider.bindToLifecycle(
//            lifecycleOwner,
//            cameraSelector,
//            imageAnalysis,
//            preview
//        )
//    } catch (exc: Exception) {
//        Log.e("CameraXApp", "Use case binding failed", exc)
//    }
//}
//
//@Composable
//fun CanvasGrid(
//    cameraSelector: CameraSelector
//) {
//    val radius = with(LocalDensity.current) {
//        4.dp.toPx()
//    }
//    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
//        Log.d("====Canvas","CanvasGird")
//        drawIntoCanvas { canvas ->
//            if (result != null && result.size >= 0) {
//
//                val boxPaint = Paint()
//                boxPaint.alpha = 200
//                boxPaint.style = Paint.Style.STROKE
//                if (mutableBitmap != null) {
//                    boxPaint.strokeWidth = 4 * mutableBitmap!!.width / 800.0f
//                    boxPaint.textSize = 30 * mutableBitmap!!.width / 800.0f
//                }
//                for (box in result) {
//                    if (box != null) {
//                        Log.d("++++", box.x0.toString()+" "+box.y0.toString()+" "+box.x1.toString()+" "+box.y1.toString())
//                        boxPaint.color = box.color
//                        boxPaint.style = Paint.Style.FILL
////                        canvas.nativeCanvas.drawText(
////                            box.label + java.lang.String.format(Locale.CHINESE, " %.3f", box.score),
////                            box.y0,
////                             box.x0,
////                            boxPaint
////                        )
//                        boxPaint.style = Paint.Style.STROKE
//                        canvas.nativeCanvas.drawRect(box.rect, boxPaint)
//                        Log.d("====drawdraw", box.label)
//                        Log.d("====drawdraw", box.score.toString())
//                    }
//                }
//            }
//        }
//    }
//}
