package com.gwi.mail.ui;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.mail.MailManager;
import com.gwi.mail.parse.ParseManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Created by Administrator on 2016-10-26.
 */
public class GwiFileChooser extends JFrame implements ActionListener {
    private JButton mOpen;

    public GwiFileChooser() {
        mOpen = new JButton(GwiConfigs.LABEL_DOCUMENT);
        Font font = new Font("宋体", Font.BOLD, 25);
        mOpen.setFont(font);
        mOpen.setBorder(BorderFactory.createLoweredBevelBorder());
        this.add(mOpen);
        this.setBounds(400, 200, 400, 400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mOpen.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.showDialog(new JLabel(), GwiConfigs.LABEL_CHOICE);
        File file = jfc.getSelectedFile();
        if (null != file) {
            if (file.isDirectory()) {
                System.out.println("Folder:" + file.getAbsolutePath());
            } else if (file.isFile()) {
                // "C:/Users/Administrator/Desktop/kaoqin/kq.txt"
                System.out.println("File:" + file.getAbsolutePath());
                work(file.getAbsolutePath());
            }
        }
    }

    private void work(final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {

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