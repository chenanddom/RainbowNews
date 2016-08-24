package com.dom.rainbownews.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读取流的工具
 *
 * @author adminster
 */
public class StreamUtils {
    /**
     * 将输入流读取成String
     *
     * @param is
     * @return
     */
    public static String readFromStream(InputStream is) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        try {
            while ((len = is.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            String result = out.toString();
            is.close();
            out.close();
            return result;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}