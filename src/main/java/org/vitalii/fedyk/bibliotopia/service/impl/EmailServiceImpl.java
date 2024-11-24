package org.vitalii.fedyk.bibliotopia.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.vitalii.fedyk.bibliotopia.config.mvc.LocaleHolder;
import org.vitalii.fedyk.bibliotopia.constant.EmailConstants;
import org.vitalii.fedyk.bibliotopia.service.EmailService;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Async     //TODO: add a mechanism for handling exceptions
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final String senderEmailAddress;
    private final LocaleHolder localeHolder;
    private final MessageSource messageSource;
    private final String emailVerificationUrl;

    public EmailServiceImpl(final JavaMailSender mailSender,
                            final SpringTemplateEngine templateEngine,
                            final MessageSource messageSource,
                            final LocaleHolder localeHolder,
                            @Value("${spring.mail.username}") final String senderEmailAddress,
                            @Value("${variables.url.email-verification}") final String emailVerificationUrl) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
        this.localeHolder = localeHolder;
        this.senderEmailAddress = senderEmailAddress;
        this.emailVerificationUrl = emailVerificationUrl;
        //fixme: fix a problem with localeHolder
    }

    @Override
    public void sendConfirmationEmail(final String firstName, final String receiverEmailAddress, final String token) {
        final Map<String, Object> data = new HashMap<>();
        final String url = emailVerificationUrl + token;
        data.put(EmailConstants.FIRST_NAME_PARAMETER, firstName);
        data.put(EmailConstants.EMAIL_VERIFICATION_URL_PARAMETER, url);
        final String template = createEmailTemplate(data, EmailConstants.CONFIRMATION_TEMPLATE);
        sendEmail(receiverEmailAddress, messageSource.getMessage(EmailConstants.CONFIRMATION_SUBJECT, null, localeHolder.getLocale()), template);
    }

    private String createEmailTemplate(final Map<String, Object> data, final String template) {
        final Context context = new Context();
        data.put(EmailConstants.LANGUAGE, localeHolder.getLocale().getLanguage());
        context.setVariables(data);
        context.setLocale(localeHolder.getLocale());
        return templateEngine.process(template, context);
    }

    private void sendEmail(final String receiverEmailAddress, final String subject, final String content) {
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setFrom(senderEmailAddress);
            mimeMessageHelper.setTo(receiverEmailAddress);
            mimeMessageHelper.setSubject(subject);
            mimeMessage.setContent(content, EmailConstants.HTML_CONTENT_TYPE);
            mailSender.send(mimeMessage);
        } catch (final MessagingException e) {
            log.error(e.getMessage());
        }
    }
}
