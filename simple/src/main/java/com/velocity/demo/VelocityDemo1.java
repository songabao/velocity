package com.velocity.demo;

import com.velocity.entiy.HtmlEntiy;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.*;
import java.util.Properties;

/**
 * @author songabao
 * @date 2019-9-16 16:21
 */

public class VelocityDemo1 {
    private final  static Logger log = LoggerFactory.getLogger(VelocityDemo1.class);

    public void simpleUse (){
        VelocityEngine ve = new VelocityEngine();
        //第一种方法
      //  Properties prop = new Properties();
//        String fileDir = VelocityDemo1.class.getResource("/templates").getPath();
//        配置模版目录（需要这样配置不然会找不到模板，写绝对路径也是可以的，绝对路径好注意这个路径找的是target下面的模板路径）
//        prop.put(Velocity.FILE_RESOURCE_LOADER_PATH, fileDir.substring(1));
//       ve.init(prop);
        //第二种方法
        ve.setProperty("file.resource.loader.path","D:/study/velocity/simple/target/classes/templates");
        ve.init();


        //初始化Velocity
        //创建context
        VelocityContext context = new VelocityContext();
        //添加数据
        context.put( "name", new String("Velocity") );
        context.put( "title", new String("简单的使用") );
        //获取模版
        Template template = ve.getTemplate("initVelocity.vm");
        //Merge 模版和context
        try {
            //要写的HTML路径
            File file = new File("D:/study/velocity/simple/src/main/resources/templates/helloWord.html");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream,"UTF-8");
            //会自动写入到helloWord.html中
            template.merge(context, osw);
            osw.flush();
            osw.close();
            fileOutputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}