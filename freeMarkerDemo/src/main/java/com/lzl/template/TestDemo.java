package com.lzl.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description
 * @Author LZL
 * @Date 2021.05.01-0:39
 */
public class TestDemo {
    public static void main(String[] args) throws IOException, TemplateException {
        //配置类
        Configuration configuration=new Configuration(Configuration.getVersion());
        //设置模板路径
        configuration.setDirectoryForTemplateLoading(new File("F:\\pingyougou_project\\freeMarkerDemo\\src\\main\\resources"));
        //设置字符集
        configuration.setDefaultEncoding("UTF-8");
        //获取模板
        Template template = configuration.getTemplate("demo.ftl");
        //构建模型
        Map map =new HashMap();
        map.put("name","上官婉儿");
        map.put("message","233233上天");
        List list=new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add("你好"+i);
        }
        map.put("d",25);
        map.put("list",list);
        map.put("today", new Date());
        map.put("num",24554552);
        //创建writer
        Writer writer=new FileWriter(new File("F:\\pingyougou_project\\freeMarkerDemo\\src\\main\\resources\\hello.html"));
        //输出
        template.process(map,writer);
        writer.close();
    }
}
