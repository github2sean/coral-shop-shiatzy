package com.dookay.shiatzy.web.mobile.util;

import java.io.*;
import java.util.*;

import com.dookay.coral.common.web.HttpContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by admin on 2017/6/28.
 */
public class FreemarkerUtil {

    /**
     * 获取模板
     * @param name
     * @return
     */
    public static Template getTemplate(String name) {
        try {
            //通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            //设定去哪里读取相应的ftl模板文件
            String path = FreemarkerUtil.class.getResource("").getPath();
            String root= getWebAppPath("/WEB-INF/ftl");
            System.out.println("path:"+path+" root:"+root);
            cfg.setDirectoryForTemplateLoading(new File(root));
            //cfg.setClassForTemplateLoading(FreemarkerUtil.class,"/ftl");
            //在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(name);
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 输出到页面
     * @param name 模板文件名
     * @param root
     */
    public static void print(String name, Map<String,Object> root, HttpServletResponse response) {
        try {
            //通过Template可以将模板文件输出到相应的流
            Template temp = getTemplate(name);
            response.setContentType("text/html; charset=" + temp.getEncoding());
            temp.process(root, new PrintWriter(response.getWriter()));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String printString(String name, Map<String,Object> root) {
        try {
            //通过Template可以将模板文件输出到相应的流
            Template temp = getTemplate(name);
            StringWriter writer = new StringWriter();
            temp.process(root, writer);
            return writer.toString();
        } catch (TemplateException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 输出到文件
     * @param name
     * @param root
     * @param outFile
     */
    public void fprint(String name,Map<String,Object> root,String outFile) {
        FileWriter out = null;
        try {
            //通过一个文件输出流，就可以写到相应的文件中
            out = new FileWriter(new File("E:\\freemarker\\ftl\\"+outFile));
            Template temp = this.getTemplate(name);
            temp.process(root, new PrintWriter(System.out));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out!=null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getServerPath(){
        HttpServletRequest request =  HttpContext.current().getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
       return  basePath;
    }

    public static String getWebAppPath(String fileName){
        return  HttpContext.current().getRequest().getServletContext().getRealPath(fileName);
    }

    public static String getLogoUrl(String fileName){
        return  getServerPath()+fileName;
    }
}

