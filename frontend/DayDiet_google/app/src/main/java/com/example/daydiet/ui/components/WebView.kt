package com.example.daydiet.ui.components

import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(state:WebViewState ) {
    
    AndroidView(factory = { context ->

        android.webkit.WebView(context)

    }){ view ->

//        view.loadUrl("https://www.baidu.com")

//

        when(val content = state.content){
            is WebContent.Url -> {
                val url = content.url

                // url不空且当前的webview加载的url不相同
                if(url.isNotEmpty() && url != view.url)
                view.loadUrl(content.url)
            }
            is WebContent.Data -> {
                view.loadDataWithBaseURL(
                    content.baseUrl,
                    content.data,
                    null,
                    "utf-8",
                    null
                )
            }
        }
    }
    
}

sealed class WebContent(){

    data class Url(val url:String):WebContent()
    data class Data(val data:String, val baseUrl: String? = null):WebContent()

}

class WebViewState(webContent:WebContent){

    // 网页内容： url 或者 data(html内容)
    var content by mutableStateOf(webContent)

    // TODO 遗留问题
    var pageTitle:String? by mutableStateOf(null)
}

@Composable
fun rememberWebViewState(url: String) = remember(key1 = url) {
    WebViewState(WebContent.Url(url))
}

@Composable
fun rememberWebViewState(data: String,baseUrl: String? = null) = remember(
    key1 = data,
    key2 = baseUrl
) {
    WebViewState(WebContent.Data(data,baseUrl))
}