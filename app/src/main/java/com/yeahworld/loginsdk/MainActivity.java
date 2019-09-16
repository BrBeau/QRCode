package com.yeahworld.loginsdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yeahworld.qrcode.QRCode;
import com.yeahworld.qrcode.RequestPresenter;
import com.yeahworld.callback.RequestCallback;
import com.yeahworld.util.LayoutTest;
import com.yeahworld.util.YeahAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button btn_qrCode;
    private TextView txt_text;
    private ImageView qrCode;
    public String data2;

    private String WECHAT_HEAD = "weixin:";
    private String wechatUrl = "https://games.yeahworld.com/api/sdk/payment_redirect/2019091116464035949.html?access_token=4b40FPCiKbtQpUNqspDsA2BocgWLXC1miognsXkoQEMbz7dsH3Tev8l-jUsSOoy1*JyhMYBLl314I9oXmscuWp7DXgKgP5EMLWw*-tvI8PiGuct0ivVY-wRqCD6b-l7wcAbO9fMBhMUvOlT8k-QXlu3tdj*mznXUBCDGwdeT7TplHw88dqdAMhbqZ7l76lkhsGihOxfF7KD3sjR7azE96GpUgK83EgUPeDpE8LuAhfzkFhAdATxCTS2zam4qK5WBV4X8LlSDVxfCXf5By4Yn5Pxds3szJ9xWMnzcCA6ezFARDeHiwdeR9wk*bqqFq*nOBFS3xTf15g";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle data1 = msg.getData();
            data2 = data1.getString("1");
            txt_text.setText(data2);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Message msg = Message.obtain();
                Bundle data = new Bundle();
                RequestPresenter requestPresenter = new RequestPresenter();
                data.putString("1", requestPresenter.Request(new RequestCallback() {
                    @Override
                    public void successful(String result) {
                        System.out.println("interface： successful" + result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            System.out.println("jsonObject： " + jsonObject);
                            JSONObject response = jsonObject.optJSONObject("response");
                            System.out.println("response： " + response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(String result) {
                        System.out.println("interface： failure" + result);
                    }
                }));
                msg.setData(data);
                handler.sendMessage(msg);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_qrCode = (Button)findViewById(R.id.btn_qrCode);
        txt_text = findViewById(R.id.txt_text);
        btn_qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, QRCode.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.btn_callback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebView webView = findViewById(R.id.webView_btn);
                webView.setVisibility(View.VISIBLE);
                WebSettings webSettings = webView.getSettings();
                webSettings.setUseWideViewPort(true);
                webSettings.setLoadWithOverviewMode(true);

                webSettings.setSupportZoom(true);
                webSettings.setBuiltInZoomControls(true);
                webSettings.setDisplayZoomControls(true);

                System.out.println("ssddsdsdsdsssds" + Uri.parse(wechatUrl));
                webView.loadUrl("https://games.yeahworld.com/api/sdk/payment_redirect/2019091611221678363.html?access_token=68d7gEybMZmzRVxIOm-e8skClvoBH*4xrqEQKg46xo1OkIdAOzf99e503DcEEVBu4Y2OLF*ax03hT3ZObHbXHwBdmLqRQD-b0UlSOULSYwfbRB04MZ9ik0V1b2*LcMu2KSgB0NO9XTHZJduACCpQky7WSVrGNnOd9ts4QojoEa*xGOyROSnrFWhBXGL2xtpMeFiQcj8vKnYv8rEwKKqLh22ra36qR1xV*6vs65*Sfn8daUN-trPdJbP5ZTW0Q9mIRr6RCHSypjechyMoeh0YXnE*kHttb1T42Og2xGnXbsEgL3MHLd1MML5fu*2VDgiCC0*xx0l5lQ");

                webView.setWebViewClient(new WebViewClient());

            }
        });

        findViewById(R.id.btn_checkInternet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNetWorkConnected(MainActivity.this);
            }
        });

        findViewById(R.id.layout_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//                View contextView = inflater.inflate(R.layout.webview_layout, null);
////                contextView.findViewById(R.id.webView);
//                MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                setContentView(contextView);

                //先将布局的缓冲情况再进行加载
                LayoutTest.dismissDialog();
                View view1 = null;
                view1 = LayoutTest.pushView(MainActivity.this, LayoutTest.getResLayout(MainActivity.this, "webview_layout"));
                ImageView img_back = view1.findViewById(LayoutTest.getResId(MainActivity.this, "img_back"));
                WebView webView = view1.findViewById(LayoutTest.getResId(MainActivity.this, "webView"));

                img_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutTest.popView(MainActivity.this);
                    }
                });

                //允许缩放
                WebSettings webSettings = webView.getSettings();
                webSettings.setSupportZoom(true);
                webSettings.setBuiltInZoomControls(true);
                webSettings.setDisplayZoomControls(true);

                webView.loadUrl("https://360.com");
                webView.setWebViewClient(new WebViewClient());

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
    }

    public boolean isNetWorkConnected(Context context){
        if (context != null){
            ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = systemService.getActiveNetworkInfo();
            if (networkInfo != null){
                Toast.makeText(context, "Internet is working", Toast.LENGTH_LONG).show();
                return networkInfo.isAvailable();
            }
        }
        Toast.makeText(context, "Internet isn't working", Toast.LENGTH_LONG).show();
        return false;
    }


}
