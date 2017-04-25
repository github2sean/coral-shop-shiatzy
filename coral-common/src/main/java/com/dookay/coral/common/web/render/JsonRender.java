package com.dookay.coral.common.web.render;

import com.alibaba.fastjson.JSON;
import com.dookay.coral.common.utils.UserAgentUtils;
import com.dookay.coral.common.web.HttpContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Luxor
 * @version v0.1.1
 * @since 2016/11/22
 */
public class JsonRender {

    private String jsonText;

    public JsonRender(String jsonText) {
        if (jsonText == null)
            throw new IllegalArgumentException("The parameter jsonString can not be null.");
        this.jsonText = jsonText;
    }

    public JsonRender(Object object) {
        if (object == null)
            throw new IllegalArgumentException("The parameter object can not be null.");
        this.jsonText = JSON.toJSONString(object);
    }

    private boolean isIE(){
        HttpServletRequest request = HttpContext.current().getRequest();
        return UserAgentUtils.getBrowser(request).getGroup().toString().equalsIgnoreCase("IE");
    }

    /**
     * http://zh.wikipedia.org/zh/MIME
     * 在wiki中查到: 尚未被接受为正式数据类型的subtype，可以使用x-开始的独立名称（例如application/x-gzip）
     * 所以以下可能要改成 application/x-json
     *
     * 通过使用firefox测试,struts2-json-plugin返回的是 application/json, 所以暂不改为 application/x-json
     * 1: 官方的 MIME type为application/json, 见 http://en.wikipedia.org/wiki/MIME_type
     * 2: IE 不支持 application/json, 在 ajax 上传文件完成后返回 json时 IE 提示下载文件
     */
    private static final String contentType = "application/json; charset=UTF-8";
    private static final String contentTypeForIE = "text/html; charset=UTF-8";

    public void render() {

        PrintWriter writer = null;
        HttpServletResponse  response = HttpContext.current().getResponse();

        try {
            response.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType(isIE() ? contentTypeForIE : contentType);
            writer = response.getWriter();
            writer.write(jsonText);
            writer.flush();
        } catch (IOException e) {
            throw new RenderException(e);
        }
        finally {
            if (writer != null)
                writer.close();
        }
    }

}
