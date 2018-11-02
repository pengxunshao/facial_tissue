package com.dida.facialtissue.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.dida.facialtissue.WeChatEntity.AccessToken;
import com.dida.facialtissue.WeChatEntity.ArticleItem;
import com.dida.facialtissue.WeChatEntity.WeChatContant;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 请求校验工具类
 * 
 * @author 32950745
 *
 */
public class WeChatHttpUtil {

    /**
     * 验证签名
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[] { WeChatContant.TOKEN, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;

        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    /**
     * 获取access_token
     * @return
     */
    public static AccessToken getAccessToken() {
        AccessToken accessToken = null;
        String access_token_url = WeChatContant.access_token_url;
        String requestUrl = access_token_url.replace("APPID", WeChatContant.appID).replace("APPSECRET", WeChatContant.appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                //log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }
    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param params 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String params) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();

        try {
            /** 创建SSLContext对象,并用我们制定的管理对象初始化 */
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            /** 从上述的SSLContext对象中获得 SSLSocketFactory */
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpsUrlConn = (HttpsURLConnection) url
                    .openConnection();
            httpsUrlConn.setSSLSocketFactory(ssf);

            httpsUrlConn.setDoInput(true);
            httpsUrlConn.setDoOutput(true);
            httpsUrlConn.setUseCaches(false);
            httpsUrlConn.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpsUrlConn.connect();
            }
            /** 当有数据提交时 */
            if (null != params) {
                OutputStream output = httpsUrlConn.getOutputStream();
                /** 设置编码格式，防止中文乱码 */
                output.write(params.getBytes("UTF-8"));
                output.close();
            }

            String contentType = httpsUrlConn.getHeaderField("Content-Type");
            if(contentType.indexOf("image") != -1){// 返回值类型是图片
                String oldFileName = httpsUrlConn.getHeaderField("Content-disposition");
                String[] oldFileNameSplits = oldFileName.split("\\.");
                String fileType = oldFileNameSplits[oldFileNameSplits.length - 1];
                oldFileNameSplits = fileType.split("\\\"");
                fileType = oldFileNameSplits[0];
                String folderPath = getProjectPath()+"/resource/weixinimg";
                String fileName = "WX_" + getUUID() + "." + fileType;

                File folder = new File(folderPath);
                // 判断文件目录是否存在,创建文件夹和文件
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File file = new File(folderPath + "/" + fileName);
                BufferedInputStream bis = new BufferedInputStream(httpsUrlConn.getInputStream());
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bufferArray = new byte[1024];
                int len;
                while ((len = bis.read(bufferArray)) != -1) {
                    fos.write(bufferArray, 0, len);
                }
                fos.close();
                bis.close();

                httpsUrlConn.disconnect();

                jsonObject = new JSONObject();
                jsonObject.put("JSON_PARAM_NAME_IMAGE_PATH", "/resource/weixinimg/" + fileName);
            }else{// 返回值类型是文本
                /** 将返回的输入流转换成字符 */
                InputStream inputStream = httpsUrlConn.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(isr);

                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                bufferedReader.close();
                isr.close();
                inputStream.close();

                inputStream = null;
                httpsUrlConn.disconnect();
                jsonObject = JSONObject.parseObject(buffer.toString());
            }
        } catch (ConnectException ce) {
            System.out.println("wechat connect is error."+ce);
        } catch (Exception e) {
            System.out.println("https request error." +e);
        }
        return jsonObject;
    }

    /**
     *
     * @description:
     * @return
     * @author:
     * @createTime:2018年6月1日 上午11:47:02
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }

    /**
     *
     * @description:
     * @return
     * @author:王涛
     * @createTime:2018年6月1日 上午11:45:58
     */
    public final static String getProjectPath() {
        String ProjectPath= WeChatHttpUtil.class.getClassLoader().getResource("").toString();
        ProjectPath = replaceSlash(ProjectPath);
        int i=0;
        while (ProjectPath.indexOf("//")!=-1 && i<20){
            ProjectPath = ProjectPath.replaceAll("\\Q//\\E", "/");
            i++;
        }
        ProjectPath = ProjectPath.replaceAll("\\Qfile:\\E", "");
        if(ProjectPath.indexOf(":")!=-1)
            ProjectPath = ProjectPath.substring(1);
        if(ProjectPath.indexOf("WEB-INF")!=-1)
            ProjectPath = ProjectPath.substring(0,ProjectPath.indexOf("WEB-INF")-1);
        return ProjectPath;
    }
    /**
     *
     * @description:
     * @param str
     * @return
     * @author:王涛
     * @createTime:2018年6月1日 上午11:46:14
     */
    public static String replaceSlash(String str){
        String temp="";
        int n=0;
        for(int i = 0; i < str.length(); i ++){
            if(str.charAt(i) == '\\'){
                temp = temp + str.substring(n,i)+"/";
                n = i+1;
            }
        }
        if(n<str.length())
            temp = temp + str.substring(n);
        return temp;
    }
    /**
     * 解析微信发来的请求(xml)
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked"})
    public static Map<String,String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String,String> map = new HashMap<String,String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    /**
     * 将map转换为XML
     * @param map
     * @return
     */
    public static String mapToXML(Map map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        mapToXML2(map, sb);
        sb.append("</xml>");
        try {
            return sb.toString();
        } catch (Exception e) {
        }
        return null;
    }

    private static void mapToXML2(Map map, StringBuffer sb) {
        Set set = map.keySet();
        for (Iterator it = set.iterator(); it.hasNext();) {
            String key = (String) it.next();
            Object value = map.get(key);
            if (null == value)
                value = "";
            if (value.getClass().getName().equals("java.util.ArrayList")) {
                ArrayList list = (ArrayList) map.get(key);
                sb.append("<" + key + ">");
                for (int i = 0; i < list.size(); i++) {
                    HashMap hm = (HashMap) list.get(i);
                    mapToXML2(hm, sb);
                }
                sb.append("</" + key + ">");

            } else {
                if (value instanceof HashMap) {
                    sb.append("<" + key + ">");
                    mapToXML2((HashMap) value, sb);
                    sb.append("</" + key + ">");
                } else {
                    sb.append("<" + key + "><![CDATA[" + value + "]]></" + key + ">");
                }

            }

        }
    }
    /**
     * 回复文本消息
     * @param requestMap
     * @param content
     * @return
     */
     public static String sendTextMsg(Map<String,String> requestMap,String content){
        
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("ToUserName", requestMap.get(WeChatContant.FromUserName));
        map.put("FromUserName",  requestMap.get(WeChatContant.ToUserName));
        map.put("MsgType", WeChatContant.RESP_MESSAGE_TYPE_TEXT);
        map.put("CreateTime", new Date().getTime());
        map.put("Content", content);
        return  mapToXML(map);
    }
     /**
      * 回复图文消息
      * @param requestMap
      * @param items
      * @return
      */
    public static String sendArticleMsg(Map<String,String> requestMap,List<ArticleItem> items){
        if(items == null || items.size()<1){
            return "";
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("ToUserName", requestMap.get(WeChatContant.FromUserName));
        map.put("FromUserName", requestMap.get(WeChatContant.ToUserName));
        map.put("MsgType", "news");
        map.put("CreateTime", new Date().getTime());
        List<Map<String,Object>> Articles=new ArrayList<Map<String,Object>>();  
        for(ArticleItem itembean : items){
            Map<String,Object> item=new HashMap<String, Object>();
            Map<String,Object> itemContent=new HashMap<String, Object>();
            itemContent.put("Title", itembean.getTitle());
            itemContent.put("Description", itembean.getDescription());
            itemContent.put("PicUrl", itembean.getPicUrl());
            itemContent.put("Url", itembean.getUrl());
            item.put("item",itemContent);
            Articles.add(item);
        }
        map.put("Articles", Articles);
        map.put("ArticleCount", Articles.size());
        return mapToXML(map);
    }

}