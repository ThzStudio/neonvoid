package com.thzstudio.neonvoid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView webView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(7, 4, 26));
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setSupportZoom(false);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // Android 15+/targetSdk 36 forca edge-to-edge: aplica as barras do sistema como padding.
        webView.setOnApplyWindowInsetsListener((v, insets) -> {
            if (Build.VERSION.SDK_INT >= 30) {
                android.graphics.Insets b = insets.getInsets(
                        WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                v.setPadding(b.left, b.top, b.right, b.bottom);
            } else {
                v.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            }
            return WindowInsets.CONSUMED;
        });
        // Fundo escuro atras das barras do sistema (substitui setStatusBarColor, obsoleto no API 35+)
        getWindow().getDecorView().setBackgroundColor(Color.rgb(7, 4, 26));
        setContentView(webView);

        // Botao/gesto voltar: no targetSdk 36 onBackPressed() nao e mais chamado.
        if (Build.VERSION.SDK_INT >= 33) {
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_DEFAULT, new OnBackInvokedCallback() {
                        @Override public void onBackInvoked() { handleBack(); }
                    });
        }
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void handleBack() {
        if (webView != null && webView.canGoBack()) webView.goBack(); else finish();
    }

    @Override public void onBackPressed() { // Android < 13
        if (Build.VERSION.SDK_INT >= 33) super.onBackPressed(); else handleBack();
    }

    @Override protected void onPause() { super.onPause(); if (webView != null) webView.onPause(); }
    @Override protected void onResume() { super.onResume(); if (webView != null) webView.onResume(); }
    @Override protected void onDestroy() {
        if (webView != null) { webView.destroy(); webView = null; }
        super.onDestroy();
    }
}
