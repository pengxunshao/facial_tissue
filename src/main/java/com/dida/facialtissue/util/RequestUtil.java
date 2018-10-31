package com.dida.facialtissue.util;

import com.dida.facialtissue.commons.BizException;
import com.dida.facialtissue.error.CommonError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 泰迪请求url
 */
public class RequestUtil {

	private Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	public String requestTaidiiService(String url) throws Exception {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);

			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result = result + line;
			}
			in.close();
		} catch (Exception e) {
			logger.error("发送请求异常，请求url"+url);
			throw new BizException(CommonError.SYSTEM_ERROR);
		}
		return result;
	}
}
