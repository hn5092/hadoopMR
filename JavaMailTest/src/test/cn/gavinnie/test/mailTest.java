package test.cn.gavinnie.test;

import java.util.Date;

import org.junit.Test;

import cn.gavinnie.dto.MimeMessageDTO;
import cn.gavinnie.util.MailUtil;

public class mailTest {

	@Test
	public void testSendEmail() {
		//设置邮件内容
		MimeMessageDTO mimeDTO = new MimeMessageDTO();
		mimeDTO.setSentDate(new Date());
		mimeDTO.setSubject("邮件的标题");
		mimeDTO.setText("邮件的内容");
		
		//设置相关参数
		String userName = "crzlinkin@sina.com";
		String password = "l3575129";
		String targetAddress = "100650920@qq.com";
		
		//发送邮件
		if(MailUtil.sendEmail(userName, password, targetAddress, mimeDTO)){
			System.out.println("邮件发送成功！");
		}else{
			System.out.println("邮件发送失败!!!");
		}
		
	}

}
