package com.dookay.coral.common.oss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.dookay.coral.common.exception.ExceptionUtils;
import com.dookay.coral.common.web.utils.SpringContextHolder;

/**
 * OSS 工具包
 * @author : kezhan
 * @since : 2017年1月21日
 * @version : v0.0.1
 */
public class OSSKit {
	
	private static Logger logger = Logger.getLogger(OSSKit.class);
	
	private static OSSObject ossObject;
	
	static{
		if(ossObject == null) {
			ossObject = SpringContextHolder.getBean(OSSObject.class);
		}
	}
	
	public static OSSObject getOSSObject() {
		if(ossObject == null) {
			ossObject = SpringContextHolder.getBean(OSSObject.class);
		}
		return ossObject;
	}
	
	
	/**
	 * 创建OSSClient
	 * @return
	 * @author : kezhan	
	 * @since : 2017年1月21日
	 */
	private static OSSClient openOSSClient(){
		/*// 创建ClientConfiguration实例
		ClientConfiguration conf = new ClientConfiguration();
		// 设置OSSClient使用的最大连接数，默认1024
		conf.setMaxConnections(200);
		// 设置请求超时时间，默认50秒
		conf.setSocketTimeout(10000);
		// 设置失败请求重试次数，默认3次
		conf.setMaxErrorRetry(5);*/
		OSSClient client = new OSSClient(ossObject.getEndpoint(), ossObject.getAccessKeyId(), ossObject.getAccessKeySecret());
		return client;
	}
	
	
	/**
	 * 关闭OSSClient
	 * @param client
	 * @author : kezhan	
	 * @since : 2017年1月21日
	 */
	public static void closeOSSClient (OSSClient client) {
		if (client != null) {
			client.shutdown();
		}
	}
	
	/**
	 * 上传字符串
	 * @param str 字符串
	 * @param path 相对路径
	 * @author : kezhan	
	 * @since : 2017年1月21日
	 */
	public static void uploadStr(String str, String path) {
		if(StringUtils.isBlank(str) || StringUtils.isBlank(path)) {
			ExceptionUtils.throwBaseException(" oss 上传请输入上传内容或路径...");
		}
		OSSClient ossClient = openOSSClient();
		//上传字符串
		try {
			PutObjectResult objectResult = ossClient.putObject(ossObject.getDefaultBucketName(), path,
					new ByteArrayInputStream(str.getBytes()));
			//TODO 处理OSS 返回信息流 objectResult
			
		} catch (OSSException e) {
			e.printStackTrace();
			logger.error(e);
			ExceptionUtils.throwBaseException(" OSSException " + e.getMessage());
		} catch (ClientException e) {
			e.printStackTrace();
			logger.error(e);
			ExceptionUtils.throwBaseException(" ClientException " + e.getMessage());
		} finally {
			closeOSSClient (ossClient); //close
		}
	
		
		
		
	
	}
	
	/**
	 * 上传 byte[] 数组
	 * @param bs byte值
	 * @param path 路径
	 * @author : kezhan	
	 * @since : 2017年1月21日
	 */
	public static void uploadBytes(byte[] bs, String path) {
		if(StringUtils.isBlank(path) || bs.length <= 0) {
			ExceptionUtils.throwBaseException(" oss 上传请输入上传内容或路径...");
		}
		
		OSSClient ossClient = openOSSClient();
		try {
			//上传字符串
			PutObjectResult objectResult = ossClient.putObject(ossObject.getDefaultBucketName(), path,
					new ByteArrayInputStream(bs));
			//TODO 处理OSS 返回信息流 objectResult
			
		} catch (OSSException e) {
			e.printStackTrace();
			logger.error(e);
			ExceptionUtils.throwBaseException(" OSSException " + e.getMessage());
		} catch (ClientException e) {
			e.printStackTrace();
			logger.error(e);
			ExceptionUtils.throwBaseException(" ClientException " + e.getMessage());
		} finally {
			closeOSSClient (ossClient); //close
		}
		
		
		
		
	
	}
	
	
	
	
	/**
	 * 上传网络流
	 * @param url
	 * @param path
	 * @author : kezhan	
	 * @since : 2017年1月21日
	 */
	public static void uploadNetworkStream(String url, String path) {
		if(StringUtils.isBlank(path) || StringUtils.isBlank(url)) {
			ExceptionUtils.throwBaseException(" oss 上传请输入上传内容或路径...");
		}
		InputStream inputStream = null;
		OSSClient ossClient = openOSSClient();
		try {
			inputStream = new URL(url).openStream();
			//上传字符串
			PutObjectResult objectResult = ossClient.putObject(ossObject.getDefaultBucketName(), path, inputStream);
			//TODO 处理OSS 返回信息流 objectResult
			logger.debug("objectResult :"+ objectResult);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.error(e);
			ExceptionUtils.throwBaseException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			ExceptionUtils.throwBaseException(e.getMessage());
		}finally {
			closeOSSClient (ossClient); //close
			//close inputStream
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e);
					ExceptionUtils.throwBaseException(" oss close inputStream " + e.getMessage());
				}
			}		
		}
	}
	
	/**
	 * 上传文件流
	 * @param filePath
	 * @param path
	 * @author : kezhan	
	 * @since : 2017年1月21日
	 */
	public static void uploadFileStream(String filePath, String path) {
		if(StringUtils.isBlank(path) || StringUtils.isBlank(filePath)) {
			ExceptionUtils.throwBaseException(" oss 上传请输入上传内容或路径...");
		}
		InputStream inputStream = null;
		OSSClient ossClient = openOSSClient();
		try {
			inputStream = new FileInputStream(filePath);
			//上传字符串
			PutObjectResult objectResult = ossClient.putObject(ossObject.getDefaultBucketName(), path, inputStream);
			//TODO 处理OSS 返回信息流 objectResult
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
			ExceptionUtils.throwBaseException(e.getMessage());
		} finally {
			closeOSSClient (ossClient); //close
			//close inputStream
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error(e);
					ExceptionUtils.throwBaseException(" oss close inputStream " + e.getMessage());
				}
			}	
		} 
	}
	
	/**
	 * 上传文件
	 * @param file 文件名
	 * @param path 上传路径
	 * @author : kezhan	
	 * @since : 2017年1月21日
	 */
	public static void uploadFile(String file, String path) {
		if(StringUtils.isBlank(path) || StringUtils.isBlank(file)) {
			ExceptionUtils.throwBaseException(" oss 上传请输入上传内容或路径...");
		}
		
		OSSClient ossClient = openOSSClient();
		try {
			// 上传文件
			PutObjectResult objectResult = ossClient.putObject(ossObject.getDefaultBucketName(), path, new File(file));
			objectResult.getCallbackResponseBody();
			//TODO 处理OSS 返回信息流 objectResult
		} catch (OSSException e) {
			e.printStackTrace();
			logger.error(e);
			ExceptionUtils.throwBaseException(" OSSException " + e.getMessage());
		} catch (ClientException e) {
			e.printStackTrace();
			logger.error(e);
			ExceptionUtils.throwBaseException(" ClientException " + e.getMessage());
		} finally {
			ossClient.shutdown();
		}
	}
	


}
