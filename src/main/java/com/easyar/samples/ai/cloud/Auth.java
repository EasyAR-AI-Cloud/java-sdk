package com.easyar.samples.dl.cloud;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import java.util.stream.Collectors;


public class Auth {

    private static final String TIMESTAMP  = "timestamp";
    private static final String KEY_AI_KEY = "aiKey";
    private static final String SIGNATURE  = "signature";

    private static String generateSignature(JSONObject jso, String aiSecret) {
        String paramStr = jso.keySet().stream()
                .sorted()
                .map(key -> key + String.valueOf(jso.get(key)))
                .collect(Collectors.joining());
        return DigestUtils.sha256Hex(paramStr + aiSecret);
    }

    public static JSONObject signParam(JSONObject param, String aiKey, String aiSerect) {
        param.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        param.put(KEY_AI_KEY, aiKey);
        param.put(SIGNATURE, generateSignature(param, aiSerect));
        return param;
    }

    public static void main(String[] args) {
        final String testKey = "test_ai_key";
        final String testSecret = "test_ai_secret";

        JSONObject param = new JSONObject();
        param.put("name", "java-sdk-test");
        param.put("meta", "AR picture to display with base64 format");
        param.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        param.put(KEY_AI_KEY, testKey);
        System.out.println(generateSignature(param, testSecret));

        signParam(param, testKey, testSecret);
        System.out.println(param);
    }

}
