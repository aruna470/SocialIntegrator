package com.eyerubic.socialintegrator.helpers;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class Mail {
    
    @Value("${emailTmp.productName}")
    private String productName;

    @Value("${emailTmp.msgTmpBasePath}")
    private String msgTmpBasePath;

    @Value("${emailTmp.layoutPath}")
    private String layoutPath;

    @Value("${emailTmp.teamMsg}")
    private String teamMsg;

    @Value("${emailTmp.siteLink}")
    private String siteLink;

    @Value("${emailTmp.siteLinkText}")
    private String siteLinkText;

    @Value("${emailTmp.about}")
    private String about;

    @Value("${emailTmp.telephone}")
    private String telephone;

    @Value("${emailTmp.email}")
    private String email;

    @Value("${emailTmp.copyright}")
    private String copyright;

    @Value("${emailTmp.fromEmail}")
    private String fromEmail;

    @Value("${emailTmp.unsubscribeText}")
    private String unsubscribeText;

    @Value("${mg.apiBaseUrl}")
    private String mgApiBaseUrl;

    @Value("${mg.apiPassword}")
    private String mgApiPassword;
    
    public Mail() {
        // Do Nothing
    }

    public void sendSignupEmail(String verificationCode, String toEmail, String firstName) {
        String layoutContent = getLayoutContent();
        String msgContent = getMsgContent("signup.html");

        msgContent = msgContent.replace("{code}", verificationCode);
        msgContent = msgContent.replace("{name}", firstName);
        String content = layoutContent.replace("{content}", msgContent);
        String subject = productName + " email verification code";

        sendSimpleMessage(subject, content, toEmail);
    }

    public boolean sendForgotPwEmail(String verificationCode, String toEmail, String firstName) {
        String layoutContent = getLayoutContent();
        String msgContent = getMsgContent("forgot-pw.html");

        msgContent = msgContent.replace("{code}", verificationCode);
        msgContent = msgContent.replace("{name}", firstName);
        String content = layoutContent.replace("{content}", msgContent);
        String subject = productName + " email verification code";

        return sendSimpleMessage(subject, content, toEmail);
    }

    public String getMsgContent(String fileName) {
        try {
            Resource resource = new ClassPathResource(msgTmpBasePath + fileName);
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);

            return new String(bdata, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "";
        }
    }

    public String getLayoutContent() {
        try {
            Resource resource = new ClassPathResource(layoutPath);
            InputStream inputStream = resource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String content = new String(bdata, StandardCharsets.UTF_8);
            content = setTemplatePlaceHolders(content);

            return content;
        } catch (Exception e) {
            return "";
        }
    }

    private String setTemplatePlaceHolders(String templateContent) {

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);
        
        String cpyRight = this.copyright.replace("{year}", yearInString);

        String[][] replacements = {
            {"{team}", teamMsg}, 
            {"{siteLink}", siteLink},
            {"{siteLinkText}", siteLinkText},
            {"{about}", about},
            {"{telephone}", telephone},
            {"{email}", email},
            {"{copyright}", cpyRight},
            {"{fromEmail}", fromEmail},
            {"{unsubscribeText}", unsubscribeText}
        };

        String strOutput = templateContent;
        for(String[] replacement: replacements) {
            strOutput = strOutput.replace(replacement[0], replacement[1]);
        }

        return strOutput;
    }

    public boolean sendSimpleMessage(String subject, String content, String toEmail)  {
        try {
            HttpResponse<JsonNode> response = Unirest.post(mgApiBaseUrl + "messages")
                .basicAuth("api", mgApiPassword)
                .field("from", this.productName + " <" + fromEmail + ">")
                .field("to", toEmail)
                .field("subject", subject)
                .field("html", content)
                .asJson();

            JsonNode body = response.getBody();
            String message = body.getObject().get("message").toString();

            return message.equals("Queued. Thank you.");
        } catch (Exception e) {
            return false;
        }
    }
}
