package com.easyar.samples.ai.cloud;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import java.util.stream.Collectors;


public class Auth {
    public  static final String HOST       = "http://ai-api.easyar.com:8080";
    private static final String KEY_APIKEY = "apiKey";
    private static final String KEY_APPID = "appId";
    private static final String TIMESTAMP  = "timestamp";
    private static final String KEY_AI_KEY = "aiKey";
    private static final String SIGNATURE  = "signature";

    private String appId;
    private String apiKey;
    private String apiSecret;

    private static String generateSignature(JSONObject jso, String aiSecret) {
        String paramStr = jso.keySet().stream()
                .sorted()
                .map(key -> key + String.valueOf(jso.get(key)))
                .collect(Collectors.joining());
        return DigestUtils.sha256Hex(paramStr + aiSecret);
    }

    @Deprecated
    public static JSONObject signParam(JSONObject param, String aiKey, String aiSerect) {
        param.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        param.put(KEY_AI_KEY, aiKey);
        param.put(SIGNATURE, generateSignature(param, aiSerect));
        return param;
    }

    public static JSONObject signParam(JSONObject param, String aiAppId, String apiKey, String apiSecret) {
        param.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        param.put(KEY_APPID, aiAppId);
        param.put(KEY_APIKEY, apiKey);
        param.put(SIGNATURE, generateSignature(param, apiSecret));
        return param;
    }

    public Auth(String appId, String apiKey, String apiSecret) {
        this.appId = appId;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public String getAppId() {
        return appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public static void main(String[] args) {
        Auth auth   = new Auth("test_app_id", "test_api_key",  "test_api_secret");
        System.out.println(signParam(new JSONObject(), auth.getAppId(), auth.getApiKey(), auth.getApiSecret()));
    }

}
