package com.cab.commom.utils.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    // 默认字符集
    private static final String ENCODING = "UTF-8";

    private static int socketTimeout = 10000;
    private static int connectTimeout = 10000;
    private static int connectionRequestTimeout = 10000;
    private static int maxConnTotal = 999;   //最大不要超过1000
    private static int maxConnPerRoute = 512;//实际的单个连接池大小，如tps定为50，那就配置50

    private static RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();
    private static CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).setMaxConnTotal(maxConnTotal).setMaxConnPerRoute(maxConnPerRoute).build();

    /**
     * @Title: sendPost
     * @Description: TODO(发送post请求form-data)
     * @param url 请求地址
     * @param headers 请求头
     * @param params 请求实体
     * @return String
     * @date 2018年9月4日 下午4:36:17
     * @throws
     */
    public static String sendPost_form(String url, Map<String, String> headers, Map<String,Object> params) {
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();

        for (int i = 0; i < 3; i++) {  // 重试5次
            try {
                // 设置请求地址
                httpPost.setURI(new URI(url));
                // 设置请求头
                if (headers != null) {
                    for (Map.Entry<String, String> e : headers.entrySet()) {
                        httpPost.addHeader(e.getKey(), e.getValue());
                    }
                }
                // 设置参数（form-data）
                if(params != null){
                    List<NameValuePair> pairs  = new ArrayList<NameValuePair>();//用于存放表单数据
                    for (Map.Entry<String, Object> param : params.entrySet()) {
                        NameValuePair nvp = new BasicNameValuePair(param.getKey(), param.getValue().toString());
                        pairs .add(nvp);
                    }
                    httpPost.setEntity(new UrlEncodedFormEntity(pairs, ENCODING));
                }
                // 发送请求,返回响应对象
                CloseableHttpResponse response = client.execute(httpPost);
                // 获取响应状态
                int status = response.getStatusLine().getStatusCode();
                if (status == HttpStatus.SC_OK) {
                    // 获取响应结果
                    resultJson = EntityUtils.toString(response.getEntity(), ENCODING);
                    //logger.info("sendPost_form请求方法return:"+resultJson);
                } else {
                    logger.error("响应失败，状态码：" + status);
                }
            } catch (Exception e) {
                logger.error("发送post请求失败,重试："+i, e);
            }
            if(StringUtils.isNotBlank(resultJson)){
                break;
            }
        }
        httpPost.releaseConnection();
        return resultJson;
    }

    public static String sendPost_json(String url, Map<String, String> headers, String params_json) {
        // 请求返回结果
        String resultJson = null;
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setConfig(requestConfig);
        boolean ifretry = false;
        String param = "";
        for (int i = 0; i < 3; i++) {  // 重试5次
            try {
                // 设置请求地址
                httpPost.setURI(new URI(url));
                // 设置请求头
                if (headers != null) {
                    for (Map.Entry<String, String> e : headers.entrySet()) {
                        httpPost.addHeader(e.getKey(), e.getValue());
                    }
                }
                // 设置参数
                if (StringUtils.isNotEmpty(params_json)) {
                    httpPost.setEntity(new StringEntity(params_json, Charset.forName("UTF-8")));
                }
                InputStream inputStream = httpPost.getEntity().getContent();
                byte[] bytes = new byte[0];
                bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                param = new String(bytes);

                // 发送请求,返回响应对象
                CloseableHttpResponse response = client.execute(httpPost);
                // 获取响应状态
                int status = response.getStatusLine().getStatusCode();
                if (status == HttpStatus.SC_OK) {
                    // 获取响应结果
                    resultJson = EntityUtils.toString(response.getEntity(), ENCODING);
                    //logger.info("sendPost_form请求方法return:"+resultJson);
                } else {
                    logger.error("响应失败，状态码：" + status);
                }
            } catch (Exception e) {
                logger.error("发送post请求失败,重试："+i, e);
            }
            if(StringUtils.isNotBlank(resultJson)){
                break;
            }
        }
        httpPost.releaseConnection();
        return resultJson;
    }

    /**
     * @Title: sendGet
     * @Description: TODO(发送get请求)
     * @param url 请求地址
     * @param params 请求参数
     * @author wangxy
     * @return String
     * @date 2018年5月14日 下午2:39:01
     * @throws
     */
    public static String sendGet(String url, Map<String, String> headers, Map<String,Object> params){
        // 请求结果
        String resultJson = null;
        // 创建HttpGet
        HttpGet httpGet = new HttpGet();
        httpGet.setConfig(requestConfig);
        for (int i = 0; i < 3; i++) {  // 重试5次
            try {
                // 创建uri
                URIBuilder builder = new URIBuilder(url);
                // 设置请求头
                if (headers != null) {
                    for (Map.Entry<String, String> e : headers.entrySet()) {
                        httpGet.addHeader(e.getKey(), e.getValue());
                    }
                }
                // 封装参数
                if (params != null) {
                    for (String key : params.keySet()) {
                        builder.addParameter(key, params.get(key).toString());
                    }
                }
                URI uri = builder.build();
                logger.info("请求地址：" + uri);
                // 设置请求地址
                httpGet.setURI(uri);
                // 发送请求，返回响应对象
                CloseableHttpResponse response = client.execute(httpGet);
                // 获取响应状态
                int status = response.getStatusLine().getStatusCode();
                if (status == HttpStatus.SC_OK) {
                    // 获取响应数据
                    resultJson = EntityUtils.toString(response.getEntity(), ENCODING);
                    //logger.info("sendGet请求方法return:"+resultJson);
                } else {
                    logger.error("响应失败，状态码：" + status);
                }
            } catch (Exception e) {
                logger.error("发送post请求失败,重试："+i, e);
            }
            if(StringUtils.isNotBlank(resultJson)){
                break;
            }
        }
        httpGet.releaseConnection();
        return resultJson;
    }
}
