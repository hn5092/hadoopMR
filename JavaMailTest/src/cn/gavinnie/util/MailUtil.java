package cn.gavinnie.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.beanutils.BeanUtils;

import cn.gavinnie.dto.MimeMessageDTO;

/**
 * 类名称:  mailUtil
 * 功能描述: TODO 邮件发送例子
 * 创建人:  Gavin-Nie 
 * 创建时间: 2014-12-4 上午9:20:16 
 * @version  V1.0  
 */
public class MailUtil {
	
	/**   
	 * 变量名 userName: TODO 邮箱用户名
	 */   
	private String userName;
	
	/**   
	 * 变量名 password: TODO 邮箱地址
	 */   
	private String password;
	
	/**   
	 * 变量名 smtpHost: TODO 邮箱smtp地址，发送地址
	 */   
	private String smtpHost;
	
	/**   
	 * 变量名 targetAddress: TODO 目标邮箱地址
	 */   
	private String targetAddress;
	
	/** 
	 * 方法名: sendEmail 
	 * 功能描述: TODO 发送邮件
	 * @param: @param userName 邮箱账号
	 * @param: @param password 邮箱密码
	 * @param: @param targetAddress 目标邮箱地址
	 * @param: @param mimeDTO 邮件部分参数
	 * @return: boolean 
	 */
	public static boolean sendEmail(String userName,String password,String targetAddress,MimeMessageDTO mimeDTO){
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTPUtil.SimpleMailSender(userName));
		props.setProperty("mail.smtp.auth", "true");
		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.password", password);
		Session session = Session.getInstance(props, new PopupAuthenticator(userName, password));
		
		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(userName));
			msg.setRecipients(Message.RecipientType.TO, targetAddress);
			//把DTO设置的内容，复制到msg中
			BeanUtils.copyProperties(msg, mimeDTO);
			//发送邮件
			Transport.send(msg);
			
		} catch (Exception mex) {
			mex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getTargetAddress() {
		return targetAddress;
	}
	public void setTargetAddress(String targetAddress) {
		this.targetAddress = targetAddress;
	}
}
