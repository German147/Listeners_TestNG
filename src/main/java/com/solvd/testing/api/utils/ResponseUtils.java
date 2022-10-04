package com.solvd.testing.api.utils;

public class ResponseUtils {
    public static String splitResponse(String responseJson, String target) {
        String[] splitToken = responseJson.split(":");
        String token= "";
        for (int i = 0; i < splitToken.length; i++) {
            if (splitToken[i].contains(target)) {
                token = splitToken[i + 1].split(",")[0];
                break;
            }
        }
        return token.replace("\"", "");
    }
}
