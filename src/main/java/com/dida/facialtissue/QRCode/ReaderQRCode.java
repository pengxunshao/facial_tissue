package com.dida.facialtissue.QRCode;


import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;


public class ReaderQRCode {


    public static void main(String[] args) {
        try {
            /*
            * MultiFormatReader 多格式读取
			* */
            MultiFormatReader formatReader = new MultiFormatReader();
            File file = new File("D:/baidu_QRCode.png");
            //读取图片buffer中
            BufferedImage bufferedImage = ImageIO.read(file);
            /*
            * BinaryBitmap			二进制位图
			* HybridBinarizer		混合二值化器
			* BufferedImageLuminanceSource   图像缓存区 亮度 资源
			* */
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
            //定义二维码参数
            HashMap hashMap = new HashMap();
            hashMap.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码方式
            Result result = formatReader.decode(binaryBitmap, hashMap);
            System.out.println("解析结果：" + result.toString());
            System.out.println("二维码格式类型：" + result.getBarcodeFormat());//BarcodeFormat   条形码格式
            System.out.println("二维码文本内容：" + result.getText());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}