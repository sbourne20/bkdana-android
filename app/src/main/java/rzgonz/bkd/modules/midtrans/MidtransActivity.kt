package rzgonz.bkd.modules.midtrans

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_midtrans.*
import rzgonz.bkd.R
import rzgonz.core.kotlin.helper.SharedPreferenceService

class MidtransActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_midtrans)
        val webSettings = web.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.setLoadWithOverviewMode(true)
        webSettings.setAllowFileAccess(true)
        webSettings.setSupportMultipleWindows(true)
        webSettings.allowUniversalAccessFromFileURLs = true
        webSettings.allowFileAccessFromFileURLs = true
        webSettings.allowContentAccess = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false)
        var url = intent.getStringExtra("url")
        web.loadUrl(url)

        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.d("LUV", "${url}")
                if(url!!.contains("payment/success")){
                    finish()
                    if(url.contains("transaction_status=pending")){
                        Toast.makeText(applicationContext,"Status Transaksi Anda Pending.\nSegera lakukan pembayaran di ATM.\nCek Email Anda Segera!",Toast.LENGTH_LONG).show()
                    }
                }
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (vf.displayedChild == 0) {
                    Handler().postDelayed({
                        vf.displayedChild = 1
                    }, 1500)
                }
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                //  view?.loadUrl("file:///android_asset/html/error.html")
                finish()
                Toast.makeText(applicationContext,"Maaf sedang terjadi masalah\nHarap coba beberapa saat lagi",Toast.LENGTH_LONG).show()

            }

        }

        web.webChromeClient = getWebChromeClient()

    }


    override fun onBackPressed() {
        if (web.canGoBack()) {
            //mClient is an instance of the MyWebviewClient
            web.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        web.onResume()
    }

    override fun onPause() {
        super.onPause()
        web.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        web.destroy()
    }

    override fun onStop() {
        super.onStop()
        web.onPause()
    }


    private fun getWebChromeClient(): WebChromeClient {
        return object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                pbLoading.setProgress(newProgress)
                if (newProgress < 100) {
                    pbWeb.visibility = View.VISIBLE
                } else {
                    pbWeb.visibility = View.GONE
                }
            }
        }
    }
}

