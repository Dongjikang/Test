package com.mymxhbyf.dongjk.myh5app;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebBackForwardList;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    Button btn;
    LinearLayout cl_root;

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cl_root = findViewById(R.id.cl_root);
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:javaCallJs("+"'" + et.getText().toString()+"'"+")");
            }
        });

        initWebView();哈哈
 
    }

    //在页面销毁的时候将webview移除
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cl_root.removeView(webView);
        webView.stopLoading();
        webView.removeAllViews();
        webView.destroy();
        webView = null;
    }

    private void initWebView() {
        webView = new WebView(getApplication());
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        webView.setLayoutParams(params);
        cl_root.addView(webView);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String  url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//支持js
        webView.loadUrl("file:///android_asset/h5.html");
        webView.addJavascriptInterface(new JSInterface(),"Android");
    }







    private class JSInterface{
        //js需要调用的方法
        @JavascriptInterface
        public void showToast(String arg){
            Toast.makeText(MainActivity.this, arg, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void show(String arg){
            tv.setText(arg);
        }
    }









}
