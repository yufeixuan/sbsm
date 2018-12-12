package com.snsprj.utils;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp工具类
 *
 * @author SKH
 * @date 2018-09-18 16:30
 **/
@Slf4j
public class FTPUtil {

    /**
     * FTP服务器地址
     */
    private static String hostname;

    /**
     * FTP服务器端口号
     */
    private static int port;

    /**
     * FTP登录账号
     */
    private static String username;

    /**
     * FTP登录密码
     */
    private static String password;

    public FTPUtil(String hostname, int port, String username, String password) {

        FTPUtil.hostname = hostname;
        FTPUtil.port = port;
        FTPUtil.username = username;
        FTPUtil.password = password;
    }

    /**
     * 上传文件到FTP服务器
     *
     * @param fileName 上传到FTP服务器后文件名称
     * @param inputStream 文件输入流
     * @param pathName FTP服务器保存目录
     * @return true/false
     * @author skh
     */
    public boolean uploadFile(String fileName, InputStream inputStream, String pathName) {

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient = getFTPConnection(ftpClient);

            if (ftpClient == null) {
                return false;
            }

            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.makeDirectory(pathName);
            ftpClient.changeWorkingDirectory(pathName);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            log.info("====>uploadFile success! fileName is {}, pathName is {}", fileName,
                    pathName);


        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 删除ftp服务器上指定目录地址下的文件
     *
     * @author skh
     * @param pathName ftp目录地址.例如：file/
     * @param fileName 文件名称。例如：foo.png
     * @return
     */
    public boolean deleteFile(String pathName, String fileName) {

        log.info("====>delete file from FTP, pathName is {}, fileName is {}", pathName, fileName);

        return this.deleteFile(pathName + fileName);
    }

    /**
     * 根据文件全路径删除ftp服务器上的文件
     *
     * @author skh
     * @param pathName 文件在ftp服务器的路径。例如：file/foo.png
     * @return true/false
     */
    public boolean deleteFile(String pathName) {

        log.info("====>delete file from FTP, pathName is {}", pathName);

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient = getFTPConnection(ftpClient);

            if (ftpClient == null) {
                return false;
            }

            ftpClient.dele(pathName);
            ftpClient.logout();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 获取FTPClient
     *
     * @param ftpClient FTPClient
     * @return FTPClient
     * @throws IOException e
     * @author skh
     */
    private FTPClient getFTPConnection(FTPClient ftpClient) throws IOException {

        ftpClient.setControlEncoding("UTF-8");

        // 连接FTP服务器
        ftpClient.connect(hostname, port);

        // 登录FTP服务器
        ftpClient.login(username, password);

        // 是否成功登录FTP服务器
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {

            log.info("====>connect ftp filed, host is {}, port is {}, username is {},"
                    + " password is {}", hostname, port, username, password);
            return null;
        } else {
            return ftpClient;
        }
    }
}
