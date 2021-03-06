package com.snsprj.sbsm.controller;

import com.snsprj.sbsm.common.ConstCode.ConstCodeFTP;
import com.snsprj.sbsm.service.FTPService;
import com.snsprj.sbsm.utils.JsonUtil;
import com.snsprj.sbsm.utils.UUIDUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 *
 * @author SKH
 * @date 2018-09-14 09:29
 **/
@Controller
@Slf4j
public class FileController {

    @Autowired
    private FTPService ftpService;

    /**
     * 文件上传
     *
     * @author skh
     *
     * @param file 文件
     * @return 返回值格式适配CKEditor5
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("upload") MultipartFile file) {

        Map<String, Object> retMap = new HashMap<>();
        if (file == null){

            log.info("====> upload file is null!");
            retMap.put("error","upload file is null!");
            return JsonUtil.obj2String(retMap);
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();

        // 获取文件的后缀名，用于组合随机名称
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 使用uuid构造唯一文件名
        fileName = UUIDUtil.getUUID() + suffixName;

        String filePath = null;

        try {
            InputStream inputStream = file.getInputStream();
            filePath = ftpService.uploadFile(fileName, ConstCodeFTP.FILE_PATH, inputStream);

            log.info("====>upload file to FTP server, filePath is {}", filePath);
        }catch (IOException e){
            e.printStackTrace();
        }

        retMap.put("uploaded", true);
        retMap.put("url", filePath);

        return JsonUtil.obj2String(retMap);
    }
}
