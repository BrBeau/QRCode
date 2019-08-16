package com.yeahworld.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

public class QRCodeUtil {

    @Nullable
    public static Bitmap createQRCodeBitmap(String content, int width, int height){
        return createQRCodeBitmap(content, width, height, "UTF-8", "H", "2", Color.BLACK, Color.WHITE);
    }

    /**
     * 生成二维码
     *@date 8/9/2019
     *
     * @param content   数据
     * @param width     显示QRCode的宽
     * @param height    显示QRCode的高
     * @param character_set     字符编码
     * @param error_correction  容错级别
     * @param margin    像素点的间距
     * @param color_black       像素点为黑色
     * @param color_white       像素点为白色
     * @return
     */
    @Nullable
    public static Bitmap createQRCodeBitmap(String content, int width, int height,
                                            @Nullable String character_set, @Nullable String error_correction,
                                            @Nullable String margin, @ColorInt int color_black, @ColorInt int color_white){

        if (TextUtils.isEmpty(content)){
            return null;
        }

        if (width < 0 || height < 0){
            return null;
        }

        try {
            Hashtable<EncodeHintType, String> hashtable = new Hashtable<>();
            //字符转码设置
            if (!TextUtils.isEmpty(character_set)){
                hashtable.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            //错码偏差率
            if (!TextUtils.isEmpty(error_correction)){
                hashtable.put(EncodeHintType.ERROR_CORRECTION, error_correction);
            }
            //空白间隙
            if (!TextUtils.isEmpty(margin)){
                hashtable.put(EncodeHintType.MARGIN, margin);
            }

            //第三方包引用实现类
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hashtable);
            int[] pix = new int[width*height];
            for (int y = 0; y<height; y++){
                for (int x = 0; x<width; x++){
                    if (bitMatrix.get(x,y)){
                        pix[y*width + x] = color_black;
                    }else {
                        pix[y*width + x] = color_white;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pix, 0, width, 0, 0, width, height);
            return bitmap;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 含有logo的二维码
     * @author  Byron
     * @date   8/12
     *
     * @param initBitmap    原二维码
     * @param logo          要添加的logo
     * @return logoBitmap   返回含有logo的矩阵图
     */
    public static Bitmap addLogo(Bitmap initBitmap, Bitmap logo){

        if (initBitmap == null){    //如果不存在原始二维码，返回null
            return null;
        }
        if (logo == null || initBitmap == null) {   //如果不存在logo，则返回原始二维码
            return initBitmap;
        }

        //获取原二维码的宽高
        int BitmapWidth = initBitmap.getWidth();
        int BitmapHeight = initBitmap.getHeight();
        //获取logo的宽高
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (BitmapWidth == 0 || BitmapHeight == 0){
            return null;
        }   //如果不存在原始二维码宽高，返回null
        if (logoWidth == 0 || logoHeight == 0){ return initBitmap; }    //如果不存在logo宽高，返回原二维码

        float scaleFactor = BitmapWidth * 1.0f / 5 / logoWidth; //*1.0f实现数据类型的转换
        Bitmap logoBitmap = Bitmap.createBitmap(BitmapWidth, BitmapHeight, Bitmap.Config.ARGB_8888);
        try{
            final float radius = 5.0f;
            Canvas canvas = new Canvas(logoBitmap);
            //定义画笔
            Paint borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setColor(Color.WHITE);
            //question: 将logo弄成圆角
            //要实现logo的圆角输出
            canvas.drawBitmap(initBitmap, 0, 0, null);  //0 0取中间位置
            canvas.scale(scaleFactor, scaleFactor, BitmapWidth/2, BitmapHeight/2);
            canvas.drawBitmap(logo, (BitmapWidth - logoWidth)/2, (BitmapHeight - logoHeight)/2, null);

            canvas.save();
            canvas.restore();
        }catch (Exception e){
            logoBitmap = null;
            e.printStackTrace();
        }
        return logoBitmap;

    }
}
