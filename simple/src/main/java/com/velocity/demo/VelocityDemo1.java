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
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Calendar;
import java.util.Properties;

/**
 * @author songabao
 * @date 2019-9-16 16:21
 */
@RestController
public class VelocityDemo1 {
    private final static Logger log = LoggerFactory.getLogger(VelocityDemo1.class);
    @GetMapping(value = "simpl")
    public void simpleUse() {
        VelocityEngine ve = new VelocityEngine();
        //第一种方法
        Properties prop = new Properties();
        String fileDir = VelocityDemo1.class.getResource("/templates").getPath();
        log.info("相对目录是："+fileDir);
        //  配置模版目录（需要这样配置不然会找不到模板，写绝对路径也是可以的，绝对路径好注意这个路径找的是target下面的模板路径）
        prop.put(Velocity.FILE_RESOURCE_LOADER_PATH, fileDir.substring(1));
        ve.init(prop);
//        //第二种方法
//        String path = "D:/velocity/templates";
//        ve.setProperty("file.resource.loader.path",path);
//        ve.init();
//        //判断文件是否存在
//        File temp = new File(path);
//        if (!temp.isDirectory()){
//            try {
//               temp.mkdirs();
//            }catch (Exception e){
//                log.error("创建文件夹失败");
//            }
//        }
//        File temp2 = new File(path+"/"+"initVelocity.vm");
//
//        if (!temp2.exists()) {
//            try {
//                temp2.createNewFile();
//                StringBuffer  htmlContent = new StringBuffer();
//                htmlContent.append("<!DOCTYPE html>\n" +
//                        "<html lang=\"ch\">\n" +
//                        "<head>\n" +
//                        "    <meta charset=\"UTF-8\">\n" +
//                        "    <title>${title}</title>\n" +
//                        "</head>\n" +
//                        "<body>\n" +
//                        "    <h1>${name} simple use velocity</h1>\n" +
//                        "</body>\n" +
//                        "</html>");
//                FileOutputStream fileOutputStream = new FileOutputStream(temp2);
//                OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream, "UTF-8");
//                osw.write(htmlContent.toString());
//                osw.flush();
//                osw.close();
//                fileOutputStream.close();
//            } catch (IOException e) {
//                log.info("创建文件失败");
//            }
//        }
        //初始化Velocity
        //创建context
        VelocityContext context = new VelocityContext();
        //添加数据
        context.put("name", new String("宋阿豹"));
        context.put("title", new String("简单的使用"));
        //获取模版
        Template template = ve.getTemplate("initVelocity.vm");
        //Merge 模版和context
        try {
            //要写的HTML路径
            // File file = new File("D:/study/velocity/simple/src/main/resources/templates/helloWord.html");
            Calendar calendar = Calendar.getInstance();
            long timeInMillis = calendar.getTimeInMillis();
            File file = new File("D:/"+timeInMillis+".html");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream, "UTF-8");
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