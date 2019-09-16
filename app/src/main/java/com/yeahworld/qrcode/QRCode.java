package com.yeahworld.qrcode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.yeahworld.loginsdk.R;

import com.yeahworld.util.QRCodeUtil;

import static com.yeahworld.util.QRCodeUtil.addLogo;

public class QRCode extends AppCompatActivity {

    private Button btn_generateQRCode, btn_scanQRCode, btn_addLogo;
    private ImageView image_qrCode;

    public String tag = "Byron";
    public static final int REQUEST_TAKE_PHOTO_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        initView();

        btn_generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = QRCodeUtil.createQRCodeBitmap("https://www.baidu.com", 880, 880);
                image_qrCode.setImageBitmap(bitmap);
            }
        });

        btn_addLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap logoBitmap = QRCodeUtil.createQRCodeBitmap("啊！行了行了~~", 880, 880);
                Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.byron);
                image_qrCode.setImageBitmap(addLogo(logoBitmap, logo));
            }
        });

        btn_scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent scanIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(scanIntent, REQUEST_TAKE_PHOTO_CODE);
            }
        });
    }

    public void initView(){
        btn_generateQRCode = (Button)findViewById(R.id.generateQRCode);
        btn_scanQRCode = (Button) findViewById(R.id.scanQRCode);
        image_qrCode = (ImageView)findViewById(R.id.image_qrcode);
        btn_addLogo = (Button)findViewById(R.id.addLogo);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode   请求参数
     * @param resultCode    返回参数
     * @param data          意图
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO_CODE){
            Log.e(tag, "request!!");
        }
        if (resultCode == RESULT_OK){
            Log.e(tag, "result");
        }
    }
}
