package com.snsprj.sbsm.di;

/**
 * @author SKH
 * @date 2018-09-18 10:10
 **/
public interface Mail {

    /**
     * 发送邮件
     * @param receive 收件人
     */
    void sendMail(String receive);
}
