package com.example.homepc.topstories

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import org.jetbrains.anko.find

/**
 * Created by HomePC on 7/1/2017.
 */

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)
        val url = intent.getStringExtra("link")
        val webView = find<WebView>(R.id.webview)
        webView.loadUrl(url)

    }
}