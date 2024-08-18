package cn.danyhug.spring_weixin_login.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson2.JSONObject;

public class HttpClientUtils {

    /**
     * 发送 GET 请求并解析响应为 JsonObject。
     *
     * @param url 要请求的 URL
     * @return 包含响应数据的 JsonObject
     */
    public static JSONObject doGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            // 执行 GET 请求
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // 获取响应内容
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                // 将 JSON 字符串转换为 JsonObject
                return JSONObject.parseObject(result);
            } else {
                System.err.println("GET request failed with status code: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
