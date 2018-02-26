package com.mawaqaa.playermatch.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mawaqaa.playermatch.R;

/**
 * Created by HP on 1/15/2018.
 */

public class PaymentWebViewActivity extends Activity {
    int index = -1;
    String url;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        final ProgressDialog pd = ProgressDialog.show(this, "", "Please wait...", true);


        webView = new WebView(this);




        webView = (WebView) findViewById(R.id.webView1);

        webView.getSettings().setJavaScriptEnabled(true);


        //webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(PaymentUrl.this, description, Toast.LENGTH_SHORT).show();
                Log.e("onReceivedError", "  " + failingUrl);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("onPageStarted", "  " + url);
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("onPageFinished", "  " + url);
                pd.dismiss();

            }

        });
        webView.loadUrl("http://live.gotapnow.com/payment/tap.aspx?web=09C%2bpwoEwc65sY9wd7Nh5jbxfaQPIqA%2b&sess=4bqTh%2b9lo14%3d&token=09C%2bpwoEwc65sY9wd7Nh5jUKVmIkCBzm");





    }
}
