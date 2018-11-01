package com.dida.facialtissue.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

/**
 * 使用zxing生成二维码
 */
public class CreateQRCode {
    public static void main(String[] arge) throws WriterException, IOException {
        int width = 300;//定义图片宽度
        int height = 300;//定义图片高度
        String format = "png";//定义图片格式
        String content = "http://aamqei.natappfree.cc/wxLogin";//定义二维码内容

        //定义二维码参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//设置编码
        //设置容错等级，等级越高，容量越小
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);//设置边距

        //生成矩阵
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
            //设置路径
            Path file = new File("F:/baidu_QRCode.png").toPath();
            //输出图像
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}