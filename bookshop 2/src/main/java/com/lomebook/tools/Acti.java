package com.lomebook.tools;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.*;
import java.util.UUID;

public class Acti {


    private static Map< String ,String > codelist = new HashMap<String, String>();
    private String title = "Acti ur Lomebook account";
    private String content = "<h1>Use code below to Acti ur account in Lomebook!</h1>";

    public boolean checkCode(String email,String code){
        String code_coorect = codelist.get(email);
        if(code.equals(code_coorect)){
            codelist.remove(email);
            return true;
        }
        return false;
    }

    public void sendEmail(String to){
        String code = UUID.randomUUID().toString();
        codelist.put(to,code);
        String from = "lometsj@foxmail.com";
        String host = "smtp.qq.com";
        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("lometsj@foxmail.com", "wnupsbxkgrulbbba"); //发件人邮件用户名、密码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject(title);

            // 设置消息体
            message.setContent(content + "<h3>" + code + "</h3>","text/html");

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....from lomebook.com");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    //private map<int,
}
