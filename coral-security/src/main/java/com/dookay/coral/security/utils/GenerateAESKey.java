package com.dookay.coral.security.utils;

import org.apache.shiro.codec.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 针对shiro  生成key  shiro默认key不安全
 * @since : 2016年11月12日
 * @author : kezhan
 * @version : v0.0.1
 */
public class GenerateAESKey {
	
    public static void main(String[] args) {
        try {
            System.out.println(Base64.encodeToString(initKey()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static byte[] initKey() throws Exception{
        //实例化
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(128);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        //返回密钥的二进制编码
        return skey.getEncoded();
    }
}
