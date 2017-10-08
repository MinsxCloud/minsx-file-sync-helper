package com.minsx.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 提供操作系统的某些接口工具
 *	
 * @Author Joker
 * @Date 2017年2月27日
 */
public class SystemUtil {

    /**
     * 执行cmd命令
     *
     * @param cmdScript cmd脚本
     * @return 是否执行完成
     */
    public static boolean excuteCmd(String cmdScript) {
        boolean isSuccess = false;
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(cmdScript);
            p.waitFor();
            int flag = p.exitValue();// 0表示执行完成
            isSuccess = flag == 0 ? true : false;
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 执行cmd命令并返回结果
     *
     * @param cmdScript cmd脚本
     * @return 执行结果
     */
    public static String excuteCmdWithResult(String cmdScript) {
        String result = null;
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(cmdScript);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("UTF-8")));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void openWebPage(String address) {
        try {
            java.net.URI uri = new java.net.URI(address);
            java.awt.Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
