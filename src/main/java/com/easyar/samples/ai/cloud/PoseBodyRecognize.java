package com.easyar.samples.ai.cloud;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


public class PoseBodyRecognize {
    private static final String AI_APPID        = "--here is your AI AppId--";
    private static final String API_KEY         = "--here is your API Key--";
    private static final String API_SECRET      = "--here is your API Secret--";
    private static final String BODY_IMG_PATH   = "twohandonhip.jpg";

    public String recognize(Auth auth, String imgPath) throws IOException{
        JSONObject params = new JSONObject()
                .put("image", Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(imgPath))));
        Auth.signParam(params, auth.getAppId(), auth.getApiKey(), auth.getApiSecret());
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), params.toString());
        Request request = new Request.Builder()
                .url(Auth.HOST + "/v1/pose/body")
                .post(requestBody)
                .build();
        return new OkHttpClient.Builder().build().newCall(request).execute().body().string();
    }

    public static void main(String[] args) throws IOException {
        Auth accessInfo  =  new Auth(AI_APPID, API_KEY, API_SECRET);
        System.out.println(new PoseBodyRecognize().recognize(accessInfo, BODY_IMG_PATH));
    }
}
