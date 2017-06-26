package com.dookay.coral.shop.message.service.impl;

import com.dookay.coral.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dookay.coral.common.persistence.Mapper;
import com.dookay.coral.common.service.impl.BaseServiceImpl;
import com.dookay.coral.shop.message.mapper.EmailMapper;
import com.dookay.coral.shop.message.domain.EmailDomain;
import com.dookay.coral.shop.message.service.IEmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 邮件的业务实现类
 * @author : luxor
 * @since : 2017年06月13日
 * @version : v0.0.1
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("emailService")
public class EmailServiceImpl extends BaseServiceImpl<EmailDomain> implements IEmailService {

	@Autowired
	private EmailMapper emailMapper;
	@Autowired
	private JavaMailSender mailSender;// spring配置中定义
	@Autowired
	private SimpleMailMessage simpleMailMessage;// spring配置中定义
	@Override
	public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
		if (StringUtils.isEmpty(toEmail)) {
			throw new ServiceException("邮件接收人不允许空");
		}
		if (StringUtils.isEmpty(subject)) {
			throw new ServiceException("邮件标题不允许空");
		}
		if (StringUtils.isEmpty(body)) {
			throw new ServiceException("邮件内容不允许空");
		}

		//写入待发送邮件
		EmailDomain email = new EmailDomain();
		email.setTitle(subject);
		email.setBody(body);
		email.setEmail(toEmail);
		email.setCreateTime(new Date());
		email.setIsSent(false);
		super.create(email);

		//调用发送邮件插件发送邮件，采用spring提供的JavaMailSender
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(simpleMailMessage.getFrom());//TODO 从配置获取
		helper.setTo(email.getEmail());
		helper.setSubject(email.getTitle());
		helper.setText(email.getBody(), true);
		mailSender.send(message);
		//发送后写入邮件历史
	}

}
