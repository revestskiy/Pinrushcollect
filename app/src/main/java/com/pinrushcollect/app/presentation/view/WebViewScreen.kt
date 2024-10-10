package com.pinrushcollect.app.presentation.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Message
import android.provider.MediaStore
import android.webkit.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

val activity
    @Composable
    get() = LocalContext.current as ComponentActivity

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WInfoScreen(
    wUrl: String,
) {
    var webView: WebView? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    val activity = activity
    BackHandler {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        }
        else {
            activity.finish()
        }
    }
    var wImageUri by remember { mutableStateOf<Uri?>(null) }
    var wMFilePathCallback by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }
    val startForResult = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data ?: wImageUri
        if (result.resultCode == RESULT_OK) {
            wMFilePathCallback?.onReceiveValue(arrayOf(uri!!))
        }
        else {
            wMFilePathCallback?.onReceiveValue(null)
        }
        wMFilePathCallback = null
    }

    fun takePhoto(context: Context) {
        val authorities = "${context.packageName}.provider"
        val photoFile = createImageFile(context)
        wImageUri = FileProvider.getUriForFile(context, authorities, photoFile)

        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, wImageUri)
        }
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val chooserIntent = Intent.createChooser(pickIntent, "Image Chooser").apply {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(captureIntent))
        }
        startForResult.launch(chooserIntent)
    }

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) takePhoto(context) else wMFilePathCallback?.onReceiveValue(null)
    }

    fun askCameraPermission() {
        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    val requestGalleryPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) askCameraPermission()
    }
    AndroidView(factory = { webViewContext ->
        WebView(webViewContext).apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                builtInZoomControls = true
                displayZoomControls = true
                loadWithOverviewMode = true
                useWideViewPort = true
                allowFileAccess = true
                databaseEnabled = true
                allowContentAccess = true
            }

            webViewClient = object : WebViewClient() {

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    CookieManager.getInstance().flush()
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    wMFilePathCallback = filePathCallback
                    requestGalleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    return true
                }

                override fun onPermissionRequest(request: PermissionRequest?) {
                    request?.grant(request.resources)
                }

                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {
                    val url = view?.hitTestResult?.extra ?: return false
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    return true
                }
            }

            loadUrl(wUrl)
            webView = this
        }
    }, modifier = Modifier.fillMaxSize())
}

@Throws(IOException::class)
private fun createImageFile(context: Context): File {
    val storageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images")
    if (!storageDir.exists()) {
        storageDir.mkdirs()
    }
    val fileName = "JPEG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}_"
    return File(storageDir, "$fileName.jpg")
}
