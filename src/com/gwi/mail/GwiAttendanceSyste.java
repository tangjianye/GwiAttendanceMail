package com.gwi.mail;

import com.gwi.mail.config.GwiConfigs;
import com.gwi.mail.mail.MailManager;
import com.gwi.mail.parse.ParseManager;

import java.util.HashMap;
import java.util.Map;

public class GwiAttendanceSyste {

    public static void main(String[] args) throws Exception {
        System.out.println("GwiAttendanceSyste");

        new Thread(new Runnable() {
            @Override
            public void run() {
                String filePath = "C:/Users/Administrator/Desktop/kaoqin/kq.txt";
                HashMap<String, String> hashMap = ParseManager.getInstance().parse(filePath);

                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    System.out.println("Email:" + entry.getKey() + " Content:" + entry.getValue());
                    String email = MailManager.getInstance().creatGwiMail(entry.getKey());
                    String content = GwiConfigs.MAIL_CONTENT + entry.getValue();
                    try {
                        MailManager.getInstance().sendMimeMail(email, content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
