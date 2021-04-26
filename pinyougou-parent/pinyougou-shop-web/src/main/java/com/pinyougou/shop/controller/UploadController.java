package com.pinyougou.shop.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.FastDFSClient;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author LZL
 * @Date 2021.04.22-21:09
 */
@RestController
public class UploadController {
    @Autowired
    private HttpServletResponse response;
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;
    @RequestMapping("/upload")
    public void uploadImage(@RequestParam("imgFile") MultipartFile[] imgFile) throws Exception {
        PrintWriter writer = response.getWriter();
        FastDFSClient fastDFSClient=new FastDFSClient("classpath:config/fdfs_client.conf");
        for(MultipartFile multipartFile : imgFile) {
            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;
            String extName = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
            String path = fastDFSClient.uploadFile(multipartFile.getBytes(), extName);
            Map map=new HashMap();
            map.put("error",0);
            map.put("url",FILE_SERVER_URL+path);
            Object o = JSON.toJSON(map);
            System.out.println(o);
            writer.write(o.toString());
        }
        writer.close();
    }
}
