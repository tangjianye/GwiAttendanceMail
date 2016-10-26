package com.gwi.mail.constant;

/**
 * Created by Administrator on 2016-10-25.
 */
public class GwiConfigs {
    public static final String LABEL_DOCUMENT = "Document";
    public static final String LABEL_CHOICE = "Choice";
    public static final String LABEL_FILE = "File";
    public static final String LABEL_FOLDER = "Folder";

    public static final String MAIL_FROM = "GWI Abnormal attendance";
    public static final String MAIL_SUBJECT = "Abnormal attendance";
    public static final String MAIL_CONTENT = "Abnormal attendance list: ";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_TIME = "HH:mm:ss";

    public static class Mail {
        // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
        // public static final String SEND_EMAIL_ACCOUNT = "ccylyfb@gwi.com.cn";
        // public static final String SEND_EMAIL_PASSWORD = "ccylyfb0048";
        public static final String SEND_EMAIL_ACCOUNT = "1110825@gwi.com.cn";
        public static final String SEND_EMAIL_PASSWORD = "password";

        // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com
        // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
        public static final String SEND_EMAIL_SMTPHOST = "mail.gwi.com.cn";

        // 收件人邮箱后缀
        public static final String RECEIVE_EMAIL_SUFFIX = "@gwi.com.cn";
    }

    public static class WorkTime {
        public static final String MORNING = "08:35:59";
        public static final String AFTERNOON = "16:55:00";
    }
}
