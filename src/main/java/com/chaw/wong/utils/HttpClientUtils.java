package com.chaw.wong.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.annotation.PreDestroy;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * use in spring as
 * <bean class="ctd.mvc.support.HttpClientUtils"/>
 */
public class HttpClientUtils {
    private static HttpClientBuilder httpClientBuilder;
    private static HttpClient httpClient;
    public static final String encode = "UTF-8";

    static {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            httpClientBuilder = HttpClientBuilder.create();
            httpClient = httpClientBuilder
                    .setMaxConnTotal(50)
                    .setMaxConnPerRoute(50)
                    .setSSLSocketFactory(sslsf)
                    .setConnectionTimeToLive(10, TimeUnit.SECONDS).build();
        } catch (Exception e) {
            throw new IllegalStateException("init HttpClientUtils failed", e);
        }
    }

    public static String get(String url) throws IOException{
        HttpGet method = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(method);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode < 300){
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity, encode);
            }else{
                throw new IOException("http get[" + url + "] failed,statuCode [" + statusCode + "].");
            }
        }
        finally {
            method.releaseConnection();
        }
    }

    public static <T> T get(String url, Class<T> t) throws IOException{
        return JSONUtils.parse(get(url), t);
    }

    /**
     *
     * @param url
     * @param params    if need order, use LinkedHashMap
     * @return
     */
    public static String post(String url, Map<String, String> params) throws IOException{
        HttpPost method = new HttpPost(url);
        method.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + encode.toLowerCase());
        if(params != null || params.size() > 0){
            List<NameValuePair> nvps = new ArrayList<>();
            Set<String> set = params.keySet();
            for (String k:set){
                nvps.add(new BasicNameValuePair(k,params.get(k)));
            }
            method.setEntity(new UrlEncodedFormEntity(nvps, encode));
        }
        return post(url, method);
    }

    public static String post(String url, String body) throws IOException{
        HttpPost method = new HttpPost(url);
        if(body != null) {
            method.setEntity(new StringEntity(body));
        }
        return post(url, method);
    }

    public static String postJson(String url, Object body) throws IOException{
        HttpPost method = new HttpPost(url);
        if(body != null) {
            method.setEntity(new StringEntity(JSONUtils.toString(body), ContentType.APPLICATION_JSON));
        }
        return post(url, method);
    }

    private static String post(String url, HttpPost method) throws IOException{
        try {
            HttpResponse httpResponse = httpClient.execute(method);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode < 300){
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity);
            }else{
                throw new IOException("http post[" + url + "] failed,statuCode [" + statusCode + "].");
            }
        }
        finally {
            method.releaseConnection();
        }
    }

    public static String putJson(String url, Object body) throws IOException{
        HttpPut method = new HttpPut(url);
        if(body != null) {
            method.setEntity(new StringEntity(JSONUtils.toString(body), ContentType.APPLICATION_JSON));
        }
        try {
            HttpResponse httpResponse = httpClient.execute(method);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode < 300){
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity);
            }else{
                throw new IOException("http put[" + url + "] failed,statuCode [" + statusCode + "].");
            }
        }
        finally {
            method.releaseConnection();
        }
    }

    public static HttpClient getHttpClient(){
        return httpClient;
    }

    @PreDestroy
    public static void release() throws IOException{
        ((CloseableHttpClient)httpClient).close();
    }

}

