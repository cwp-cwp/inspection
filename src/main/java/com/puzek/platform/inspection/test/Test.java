package com.puzek.platform.inspection.test;

import com.puzek.platform.inspection.entity.PushContent;
import com.puzek.platform.inspection.util.JsonUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        /*PushContent pushContent = new PushContent();
        //[{"id":21626,"parkcode":"133","status":0,"plateNum":"无","time":"2019-11-02 16:27:51","batchNumber":"20191102161928486","pdaphoto":[]}]
        pushContent.setId(21626);
        pushContent.setParkcode("133");
        pushContent.setStatus(0);
        pushContent.setPlateNum("无");
        pushContent.setTime("2019-11-02 16:27:51");
        pushContent.setBatchNumber("20191102161928486");
        pushContent.setPdaphoto(new ArrayList<>());
        List<PushContent> list = new ArrayList<>();
        list.add(pushContent);
        try {
            System.out.println(sendJson("http://120.24.54.186:6010/api/apitools/", JsonUtil.toJson(list)));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Integer a = 122;
        Integer b = 122;
        Integer c = 345;
        Integer d = 345;

        System.out.println(a == b);
        System.out.println(c == d);
    }

    private static String sendJson(String url, String params) throws Exception {
        HttpURLConnection conn = null;
        String resultStr = "";
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("Accept", "text/plain");
            conn.setRequestMethod("POST");

            PrintWriter writer = new PrintWriter(conn.getOutputStream());
            writer.print(params);
            writer.flush();

            int resp = conn.getResponseCode();
            StringBuilder sbuilder = new StringBuilder();
            if (resp != 200)
                throw new IOException("Response code=" + resp);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sbuilder.append(line);
                sbuilder.append("\n");
            }
            resultStr = sbuilder.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            conn.disconnect();
        }
        System.out.println("推送结果：" + resultStr);
        return resultStr;
    }
}
