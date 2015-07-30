package com.lm2a.mailoglibrary;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by lemenzm on 25/07/2015.
 */
public class LogConfig {

    private static LogConfig instance = null;
    private AssetsPropertyReader assetsPropertyReader;
    private Properties p;
    private MailConfiguration mailConfiguration;
    private Context context;

    public LogConfig(Context context) {
        this.context=context;
        //reading properties
        assetsPropertyReader = new AssetsPropertyReader(context);
        p = assetsPropertyReader.getProperties("log_config.properties");

        mailConfiguration = new MailConfiguration(
                p.getProperty("email_subject"),
                p.getProperty("receiver_email"),
                p.getProperty("sender_email"),
                p.getProperty("sender_email_password"),
                p.getProperty("log_on")
                );
    }

    public MailConfiguration getMailConfiguration() {
        return mailConfiguration;
    }

    public class MailConfiguration {

        private String senderEmail;
        private String senderEmailPassword;
        private String receiverEmail;
        private String emailSubject;
        private boolean logOn;

        public MailConfiguration(String emailSubject, String receiverEmail, String senderEmail, String senderEmailPassword, String logOn) {
            this.emailSubject = emailSubject;
            this.receiverEmail = receiverEmail;
            this.senderEmail = senderEmail;
            this.senderEmailPassword = senderEmailPassword;
            this.logOn = Boolean.parseBoolean(logOn);;
        }

        public String getEmailSubject() {
            return emailSubject;
        }

        public String getReceiverEmail() {
            return receiverEmail;
        }

        public String getSenderEmail() {
            return senderEmail;
        }

        public String getSenderEmailPassword() {
            return senderEmailPassword;
        }

        public boolean isLogOn() {
            return logOn;
        }
    }

    StringBuilder log;

    public void saveLogcat() {
        try {
            Process process = Runtime.getRuntime().exec("logcat -d "+MAiLog.TAG+":V *:S");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            log=new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
            }
        } catch (IOException e) {
            Log.e("MailConfig-saveLogcat", e.getMessage());
        }
    }

    public void sendLogcatMail(){
        try {
            saveLogcat();
            GMailSender sender = new GMailSender(mailConfiguration.getSenderEmail(), mailConfiguration.getSenderEmailPassword());
            sender.sendMail(mailConfiguration.getEmailSubject(),
                    log.toString(),
                    mailConfiguration.getSenderEmail(),
                    mailConfiguration.getReceiverEmail());
        } catch (Exception e) {
            Log.e("MailConfig-send", e.getMessage());
        }
    }



}
