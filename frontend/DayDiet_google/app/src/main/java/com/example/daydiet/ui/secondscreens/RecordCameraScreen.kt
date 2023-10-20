package com.example.daydiet.ui.secondscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun RecordCameraScreen(
    navHostController: NavHostController,
    onBack: ()->Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = "快速记录",
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .clickable { onBack() }
                            .padding(8.dp)
                    )
                },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) {
//        CameraX()
    }
}

//@Composable
//fun CameraX() {
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val context = LocalContext.current
//
//    val cameraProviderFuture = remember {
//        ProcessCameraProvider.getInstance(context)
//    }
//
//    val previewView = remember {
//        PreviewView(context).apply{
//            id = R.id.preview_view
//        }
//    }
//
//    AndroidView(factory = {previewView}, modifier = Modifier.fillMaxSize()){
//        cameraProviderFuture.addListener({
//            val cameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder().build().also{
//                it.setSurfaceProvider(previewView.surfaceProvider)
//            }
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            try{
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
//            }catch (e: Exception){
//                Log.e("====", e.toString())
//            }
//        },ContextCompat.getMainExecutor(context))
//    }
//}