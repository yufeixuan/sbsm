package com.snsprj.sbsm.service.impl;

import com.snsprj.sbsm.service.FTPService;
import com.snsprj.sbsm.utils.FTPUtil;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author SKH
 * @date 2018-10-16 17:45
 **/
@Service
@Slf4j
public class FTPServiceImpl implements FTPService {

    @Value("${ftp.host}")
    private String ftpHost;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.nginx.port}")
    private int ftpNginxPost;

    /**
     * 上传文件到FTP服务器并返回文件地址
     *
     * @param fileName 上传到FTP服务器后文件名称。例如：foo.png
     * @param pathName FTP服务器保存目录。例如：file/
     * @param inputStream 文件输入流
     * @return 文件地址
     * @author skh
     */
    @Override
    public String uploadFile(String fileName, String pathName, InputStream inputStream) {

        log.info("====>upload file to ftp server, fileName is {}, pathName is {},"
                + " ftpNginxPort is {}", fileName, pathName, ftpNginxPost);

        FTPUtil ftpUtil = new FTPUtil(ftpHost, ftpPort, ftpUsername, ftpPassword);
        boolean isUploadSuccess = ftpUtil.uploadFile(fileName, inputStream, pathName);

        if (isUploadSuccess) {

            return "http://" + ftpHost + ":" + ftpNginxPost + "/" + pathName + fileName;
        } else {

            return null;
        }
    }

    /**
     * 根据文件全路径删除ftp服务器上的文件
     *
     * @param pathName 文件在ftp服务器的路径。例如：file/foo.png
     * @return true/false
     * @author skh
     */
    @Override
    public boolean deleteFile(String pathName) {

        log.info("====>delete file from ftp, pathName is {}", pathName);

        FTPUtil ftpUtil = new FTPUtil(ftpHost, ftpPort, ftpUsername, ftpPassword);

        return ftpUtil.deleteFile(pathName);
    }
}
