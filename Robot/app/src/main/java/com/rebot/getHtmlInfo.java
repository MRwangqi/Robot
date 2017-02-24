package com.rebot;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Entity;
import android.util.Log;

public class getHtmlInfo {
    /**
     * 链接： http://www.tuling123.com/openapi/api?key=74
     * ba0496b9c8f783ccca78c7e8921e40&info=你好
     */
    public static final String API_KEY = "74ba0496b9c8f783ccca78c7e8921e40";
    public static final String html_url = "http://www.tuling123.com/openapi/api?key=";
    static String result = "";

    public static String getMsg(String msg) {
        HttpGet get = new HttpGet(html_url + API_KEY + "&info=" + URLEncoder.encode(msg));
        try {
            HttpResponse response = new DefaultHttpClient().execute(get);
            if (response.getStatusLine().getStatusCode() == 200)
                result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
