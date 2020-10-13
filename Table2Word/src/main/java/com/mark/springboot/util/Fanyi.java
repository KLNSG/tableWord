package com.mark.springboot.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * @author ：Lxin
 * @date ：Created in 2020/10/12 14:41
 */
public class Fanyi {

    private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    public static void main(String[] args) throws Exception {
        System.out.println(easy("BIZ TYPE CODE"));
    }

    public static String easy(String str){
        String fanyi=null;
        try {
            fanyi=translate("en","zh-CN",str);
            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fanyi;
    }

    public static String translate(String langFrom, String langTo,
                            String word) throws Exception {
        String appid="20201012000587318";
        String salt="baidu";

        String sign = md5(appid + word + salt + "nNmMcbFOM9rnyjr4zt0c");
        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate?appid="+appid+"&salt="+salt+"&sign="+sign+"&from=en&to=zh&" +
                "q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        Gson gson = new Gson();
        Map map = gson.fromJson(response.toString(), Map.class);
        List translateResult = (List) map.get("trans_result");
        Map map1 = (Map) translateResult.get(0);
        String tgt = (String) map1.get("dst");
        return tgt;
    }
    public static String md5(String input) throws UnsupportedEncodingException {
        if (input == null)
            return null;

        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes("utf-8");
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        // 字符数组组合成字符串返回
        return new String(resultCharArray);

    }

}
