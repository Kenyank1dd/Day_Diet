package com.example.daydiet

import android.Manifest
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.daydiet.ui.components.NaviHostApp
import com.example.daydiet.ui.theme.DayDietTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.heytap.databaseengine.HeytapHealthApi
import com.heytap.databaseengine.apiv2.HResponse
import com.heytap.databaseengine.apiv2.IHeytapHealthApi
import com.heytap.databaseengine.apiv2.auth.AuthResult
import com.heytap.databaseengine.utils.HLog
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateEncodingException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.*

class MainActivity : ComponentActivity() {

    lateinit var mApi: IHeytapHealthApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HeytapHealthApi.init(this)
        mApi = HeytapHealthApi.getInstance()

        setContent {
            DayDietTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // 设置状态栏的颜色为变白色
                    rememberSystemUiController().setStatusBarColor(
                        Color.White
                    )

                    NaviHostApp()

                    getCertificateSHA1Fingerprint(this)?.let { Log.d("keystore", it) }

                    var have_been_authed by remember { mutableStateOf(false) }

//                    HeytapHealthApi.getInstance()
//                        .authorityApi()
//                        .valid(object : HResponse<List<String?>> {
//                            override fun onSuccess(scopeList: List<String?>) {
//                                scopeList.forEach {
//                                    if(it == "READ_DAILY_ACTIVITY") {
//                                        have_been_authed = true
//                                    }
//                                }
//                                Log.d("daydietauth", "Auth scope is $scopeList")
//                            }
//                            override fun onFailure(errorCode: Int) {
//                                Log.d("daydietauth", "Auth valid failed! Error code: $errorCode")
//                            }
//                        })

                    var confirm by remember { mutableStateOf(false) }
//                    if(!have_been_authed) {
//                        AlertDialog(
//                            onDismissRequest = {
//                                have_been_authed = true
//                            },
//                            title = { Text(text = "权限获取", fontWeight = FontWeight.W700, style = MaterialTheme.typography.h6) },
//                            text = { Text(text = "为给您提供更好的服务，请将健康平台数据\"读取日常活动数据\"权限授权悦食Day Diet。", style = MaterialTheme.typography.body1) },
//                            confirmButton = {
//                                TextButton(
//                                    onClick = {
//                                        have_been_authed = true
//                                        confirm = true
//                                        // 先解除授权
//                                        HeytapHealthApi.getInstance()
//                                            .authorityApi()
//                                            .revoke(object : HResponse<List<Any?>?> {
//                                                override fun onSuccess(objectList: List<Any?>?) {
//                                                    Log.d("daydietauth", "revoke access successfully")
//                                                }
//
//                                                override fun onFailure(errorCode: Int) {
//                                                    Log.d("daydietauth", "revoke access failed! Error code: $errorCode")
//                                                }
//                                            })
//                                    },
//                                ) {
//                                    Text(
//                                        "确认",
//                                        fontWeight = FontWeight.W700,
//                                        style = MaterialTheme.typography.button
//                                    )
//                                }
//                            },
//                            dismissButton = {
//                                TextButton(
//                                    onClick = {
//                                        have_been_authed = true
//                                    }
//                                ) {
//                                    Text(
//                                        "取消",
//                                        fontWeight = FontWeight.W700,
//                                        style = MaterialTheme.typography.button
//                                    )
//                                }
//                            }
//                        )
//                    }
//                    if(confirm){
//                        mApi.authorityApi().request(this, object : HResponse<AuthResult?> {
//                            override fun onSuccess(authResult: AuthResult?) {
//                                Log.d("yyyy", "Success!")
//                            }
//                            override fun onFailure(i: Int) {
//                                Log.d("yyyy", "Failed!")
//                            }
//                        })
//                    }
                    CertificatePermission()
                }
            }
        }
    }
}



@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CertificatePermission() {
    val permissionState =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
            )
        )
    if (!permissionState.allPermissionsGranted) {
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
    }
}

//这个是获取SHA1的方法
fun getCertificateSHA1Fingerprint(context: Context): String? {
    //获取包管理器
    val pm: PackageManager = context.getPackageManager()
    //获取当前要获取SHA1值的包名，也可以用其他的包名，但需要注意，
    //在用其他包名的前提是，此方法传递的参数Context应该是对应包的上下文。
    val packageName: String = context.getPackageName()
    //返回包括在包中的签名信息
    val flags = PackageManager.GET_SIGNATURES
    var packageInfo: PackageInfo? = null
    try {
        //获得包的所有内容信息类
        packageInfo = pm.getPackageInfo(packageName, flags)
    } catch (e: NameNotFoundException) {
        e.printStackTrace()
    }
    //签名信息
    val signatures: Array<out android.content.pm.Signature>? = packageInfo!!.signatures
    val cert: ByteArray = (signatures?.get(0)?.toByteArray() ?:null) as ByteArray
    //将签名转换为字节数组流
    val input: InputStream = ByteArrayInputStream(cert)
    //证书工厂类，这个类实现了出厂合格证算法的功能
    var cf: CertificateFactory? = null
    try {
        cf = CertificateFactory.getInstance("X509")
    } catch (e: Exception) {
        e.printStackTrace()
    }
    //X509证书，X.509是一种非常通用的证书格式
    var c: X509Certificate? = null
    try {
        c = cf!!.generateCertificate(input) as X509Certificate
    } catch (e: Exception) {
        e.printStackTrace()
    }
    var hexString: String? = null
    try {
        //加密算法的类，这里的参数可以使MD4,MD5等加密算法
        val md = MessageDigest.getInstance("SHA1")
        //获得公钥
        val publicKey = md.digest(c?.getEncoded() ?: null)
        //字节到十六进制的格式转换
        hexString = byte2HexFormatted(publicKey)
    } catch (e1: NoSuchAlgorithmException) {
        e1.printStackTrace()
    } catch (e: CertificateEncodingException) {
        e.printStackTrace()
    }
    return hexString
}

//这里是将获取到得编码进行16进制转换
private fun byte2HexFormatted(arr: ByteArray): String? {
    val str = StringBuilder(arr.size * 2)
    for (i in arr.indices) {
        var h = Integer.toHexString(arr[i].toInt())
        val l = h.length
        if (l == 1) h = "0$h"
        if (l > 2) h = h.substring(l - 2, l)
        str.append(h.uppercase(Locale.getDefault()))
        if (i < arr.size - 1) str.append(':')
    }
    return str.toString()
}