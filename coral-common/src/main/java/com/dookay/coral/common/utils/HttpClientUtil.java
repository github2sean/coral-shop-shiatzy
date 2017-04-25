package com.dookay.coral.common.utils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * http请求工具
 * @author Luxor
 * @version v0.1.1
 * @since 2016/11/21
 */
public class HttpClientUtil {
    private Logger logger=Logger.getLogger(HttpClientUtil.class);

    private int status;

    private org.apache.http.client.HttpClient httpClient;
    private org.apache.http.client.methods.HttpRequestBase httpRequest;
    private HttpContext localContext;

    public Object sendGetRequest(String url) throws Exception{

        HttpGet httpGet = new HttpGet(url);

        httpClient = new DefaultHttpClient();

        //设置连接核心参数
        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,HttpVersion.HTTP_1_1);
        httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,HTTP.UTF_8);
        httpClient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,Boolean.FALSE);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);

        //设置参数

        //设置请求报头 请求完毕立马关闭
        httpGet.setHeader("Connection","close");

        ResponseHandler<String> handler = new ResponseHandler<String>() {

            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                status=response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                } else {
                    return null;
                }
            }
        };

        Object response=null;

        try {
            response = httpClient.execute(httpGet, handler);

        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }finally {
            if(httpClient!=null)
                httpClient.getConnectionManager().shutdown();
        }

        if(status==HttpStatus.SC_OK)
        {
            return response;
        }else{
            throw new Exception("http status:"+status+", deal with request failed! error:"+response);
        }

    }


    public Object sendPostRequest(String url,Map<String, Object> param) throws Exception{

        List<NameValuePair> formParams = setNameValuePair(param);

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);

        httpClient = new DefaultHttpClient();
        localContext = new BasicHttpContext();

        //设置连接核心参数
        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,HttpVersion.HTTP_1_1);
        httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,HTTP.UTF_8);
        httpClient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE,Boolean.FALSE);
        //设置参数

        //设置请求报头 请求完毕立马关闭
        httpPost.setHeader("Connection","close");

        ResponseHandler<String> handler = new ResponseHandler<String>() {

            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                status=response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                } else {
                    return null;
                }
            }
        };

        Object response=null;

        try {
            response = httpClient.execute(httpPost, handler, localContext);

        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }finally {
            if(httpClient!=null)
                httpClient.getConnectionManager().shutdown();
        }

        if(status==HttpStatus.SC_OK)
        {
            return response;
        }else{
            throw new Exception("http status:"+status+", deal with request failed! error:"+response);
        }

    }

    /**
     * 设置 form内容请求参数
     * @param param
     */
    public List<NameValuePair> setNameValuePair(Map<String, Object> param)
    {
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();

        if(param==null)
            return null;
        Set<String> keySet=param.keySet();
        String key="";
        for(Iterator<String> it=keySet.iterator();it.hasNext();)
        {
            key=it.next();
            if(key!=null && !key.trim().equals(""))
            {
                paramsList.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
            }
        }

        return paramsList;
    }

    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public org.apache.http.client.HttpClient getHttpClient() {
        return httpClient;
    }


    public void setHttpClient(org.apache.http.client.HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public org.apache.http.client.methods.HttpRequestBase getHttpRequest() {
        return httpRequest;
    }


    public void setHttpRequest(
            org.apache.http.client.methods.HttpRequestBase httpRequest) {
        this.httpRequest = httpRequest;
    }


    public org.apache.http.protocol.HttpContext getLocalContext() {
        return localContext;
    }


    public void setLocalContext(org.apache.http.protocol.HttpContext localContext) {
        this.localContext = localContext;
    }

}
