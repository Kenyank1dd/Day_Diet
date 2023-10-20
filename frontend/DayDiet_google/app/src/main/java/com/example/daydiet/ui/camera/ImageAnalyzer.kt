//package com.example.daydiet.ui.camera
//
//import android.graphics.*
//import android.util.Log
//import android.util.Size
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.ImageProxy
//import com.example.daydiet.yolov5.Box
//import com.example.daydiet.yolov5.YOLOv5
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import java.io.ByteArrayOutputStream
//import java.io.IOException
//import java.util.*
//
//class ImageAnalyzer(
//    private val scope: CoroutineScope
//): ImageAnalysis.Analyzer{
//    companion object {
//        private const val TAG = "CameraXApp"
//    }
//
//    override fun analyze(imageProxy: ImageProxy) {
//        scope.launch {
//            delay(200)
//            try {
//
//                Log.d("----",imageProxy.height.toString()+" "+imageProxy.width.toString())
//                val rotationDegree = imageProxy.imageInfo.rotationDegrees
//                detectOnModel(imageProxy, rotationDegree)
//                imageProxy.close()
//
//            }catch (e: Exception){
//                Log.d("====","fatal error")
//            }
//
//        }
//    }
//}
//
//private fun detectOnModel(image: ImageProxy, rotationDegrees: Int) {
//    val bitmapsrc: Bitmap = imageToBitmap(image) // 格式转换
//    detectService.execute {
//        val matrix = Matrix()
//        matrix.postRotate(rotationDegrees.toFloat())
//        val bitmap = Bitmap.createBitmap(
//            bitmapsrc, 0, 0,
//            bitmapsrc.width, bitmapsrc.height, matrix, false)
//        val threshold = 0.3
//        val nms = 0.7
//
//        results = YOLOv5.detect(bitmap, threshold, nms)
//
//        mutableBitmap = bitmap
//        try {
//            result.clear()
//            for(box in results!!){
//                if (box != null) {
//                    result.add(box)
//                }
//            }
//            for(box in result){
//                Log.d("====BoxInResultNow",box.label)
//            }
//        } catch (exc: Exception){
//            Log.d("====dddd","result.clear.failed")
//        }
//    }
//}
//
//private fun imageToBitmap(image: ImageProxy): Bitmap {
//    val nv21: ByteArray = imagetToNV21(image)
//    val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
//    val out = ByteArrayOutputStream()
//    yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 100, out)
//    val imageBytes = out.toByteArray()
//    try {
//        out.close()
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//}
//
//private fun imagetToNV21(image: ImageProxy): ByteArray {
//    val planes = image.planes
//    val y = planes[0]
//    val u = planes[1]
//    val v = planes[2]
//    val yBuffer = y.buffer
//    val uBuffer = u.buffer
//    val vBuffer = v.buffer
//    val ySize = yBuffer.remaining()
//    val uSize = uBuffer.remaining()
//    val vSize = vBuffer.remaining()
//    val nv21 = ByteArray(ySize + uSize + vSize)
//    // U and V are swapped
//    yBuffer[nv21, 0, ySize]
//    vBuffer[nv21, ySize, vSize]
//    uBuffer[nv21, ySize + vSize, uSize]
//    return nv21
//}
//
