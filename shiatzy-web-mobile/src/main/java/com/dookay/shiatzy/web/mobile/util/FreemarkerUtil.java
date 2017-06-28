package com.dookay.shiatzy.web.mobile.util;

import java.io.*;
import java.util.*;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;


/**
 * Created by admin on 2017/6/28.
 */
@Component
public class FreemarkerUtil {

    /**
     * 获取模板
     * @param name
     * @return
     */
    public Template getTemplate(String name) {
        try {
            //通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            //设定去哪里读取相应的ftl模板文件
            cfg.setClassForTemplateLoading(this.getClass(),"/ftl");
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
    public void print(String name, Map<String,Object> root, HttpServletResponse response) {
        try {
            //通过Template可以将模板文件输出到相应的流
            Template temp = this.getTemplate(name);
            response.setContentType("text/html; charset=" + temp.getEncoding());
            temp.process(root, new PrintWriter(response.getWriter()));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            temp.process(root, out);
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
}

