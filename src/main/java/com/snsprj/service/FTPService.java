package com.snsprj.service;

import java.io.InputStream;

/**
 * @author SKH
 * @date 2018-10-16 17:18
 **/

public interface FTPService {

    /**
     * 上传文件到FTP服务器并返回文件地址
     *
     * @param fileName 上传到FTP服务器后文件名称。例如：foo.png
     * @param pathName FTP服务器保存目录。例如：file/
     * @param inputStream 文件输入流
     * @return 文件地址
     * @author skh
     */
    String uploadFile(String fileName, String pathName, InputStream inputStream);

    /**
     * 根据文件全路径删除ftp服务器上的文件
     *
     * @author skh
     * @param pathName 文件在ftp服务器的路径。例如：file/foo.png
     * @return true/false
     */
    boolean deleteFile(String pathName);
}
