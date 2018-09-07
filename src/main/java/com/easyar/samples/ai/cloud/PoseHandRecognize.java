package com.easyar.samples.dl.cloud;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


public class PoseHandRecognize {

    private static final String HOST       = "http://my_uuid.cn1.crs.easyar.com:8001";
    private static final String AI_KEY    = "--here is your crs dl body image space's key--";
    private static final String AI_SECRET = "--here is your crs dl body image space's secret--";

    public static void main(String[] args) throws IOException {

        String testImagePath = "onehandheart.jpg";

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(120,TimeUnit.SECONDS);

        OkHttpClient client = builder.build();

        JSONObject params = new JSONObject();
        params.put("image", Base64.getEncoder().encodeToString(
                Files.readAllBytes(Paths.get(testImagePath))));

        Auth.signParam(params, AI_KEY, AI_SECRET);

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , params.toString());
        Request request = new Request.Builder()
                .url(HOST+"/v1/pose/hand")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
