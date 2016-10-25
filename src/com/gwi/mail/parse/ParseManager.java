package com.gwi.mail.parse;

import com.gwi.mail.bean.AttendanceBean;
import com.gwi.mail.config.GwiConfigs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Administrator on 2016-10-25.
 */
public class ParseManager {
    private static final String ENCODING = "GBK";
    private ArrayList<AttendanceBean> mParseList;
    private HashMap<String, String> mHashMap;

    private static ParseManager ourInstance = new ParseManager();

    public static ParseManager getInstance() {
        return ourInstance;
    }

    private ParseManager() {
        mParseList = new ArrayList<>();
        mHashMap = new HashMap<>();
    }

    private Date getParseDate(String clock) {
        Date time = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(GwiConfigs.DEFAULT_DATE_FORMAT, Locale.CHINA);
        SimpleDateFormat timeFormat = new SimpleDateFormat(GwiConfigs.DATE_FORMAT_TIME, Locale.CHINA);
        try {
            Date date = dateFormat.parse(clock);
            time = timeFormat.parse(timeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    private Date getParseTime(String clock) {
        Date time = null;
        SimpleDateFormat timeFormat = new SimpleDateFormat(GwiConfigs.DATE_FORMAT_TIME, Locale.CHINA);
        try {
            time = timeFormat.parse(clock);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：
     * 1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     *
     * @param filePath
     */
    private void readTxtFile(String filePath) {
        mParseList.clear();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                    AttendanceBean bean = parseStr(lineTxt);
                    if (null != bean) {
                        mParseList.add(bean);
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    private AttendanceBean parseStr(String str) {
        if (null == str) {
            return null;
        }
        AttendanceBean bean = new AttendanceBean();
        String strr = str.trim();
        String[] abc = strr.split("\\t");
        bean.setJobNumber(abc[0]);
        bean.setClock(abc[1]);
        System.out.println(bean.getJobNumber());
        System.out.println(bean.getClock());
        return bean;
    }

    /**
     * 符合的第一个string是工号|第二个string是打卡异常的时间
     *
     * @param filePath
     * @return
     */
    public HashMap<String, String> parse(String filePath) {
        // 解析原始的打卡数据
        readTxtFile(filePath);
        // 解析打卡异常的工号
        return getAbnormalJobNomber();
    }

    /**
     * 获取打卡异常员工工号
     *
     * @return
     */
    public HashMap<String, String> getAbnormalJobNomber() {
        mHashMap.clear();
        final Date MORNING = getParseTime(GwiConfigs.WorkTime.MORNING);
        final Date AFTERNOON = getParseTime(GwiConfigs.WorkTime.AFTERNOON);

        for (AttendanceBean attendanceBean : mParseList) {
            Date date = getParseDate(attendanceBean.getClock());

            // 打卡异常的员工
            if (date.after(MORNING) && date.before(AFTERNOON)) {
                // 有过打卡异常
                StringBuffer sb = new StringBuffer();
                sb.append(attendanceBean.getClock());
                if (mHashMap.containsKey(attendanceBean.getJobNumber())) {
                    String clock = mHashMap.get(attendanceBean.getJobNumber());
                    sb.append("    ").append(clock).append("    ").append(attendanceBean.getClock());
                }
                mHashMap.put(attendanceBean.getJobNumber(), sb.toString());
            }
        }
        return mHashMap;
    }
}
