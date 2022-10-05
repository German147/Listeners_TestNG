package com.solvd.testing.zebrunner.api;


import com.solvd.testing.helper.ConfigPropertiesHelper;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author fgimeno
 */
//Se va cuando se implemente lo del grupo A
public class RestApiWrapper {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final static Logger LOGGER = LogManager.getLogger(RestApiWrapper.class);
    private static OkHttpClient client = new OkHttpClient();

    /**
     * Makes an okhttp3 POST call using JSON content type
     *
     * @param url  The full REST API endpoint url
     * @param json The JSON payload as String
     * @return Response The REST API call okhttp3 Response object
     */
    public static Response postJson(String url, String json, String header) throws IOException {
        RequestBody body = RequestBody.create(json, JSON); // new
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + header)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response postScreenshot(String url, byte[] screenshotByte, String header) throws IOException {
        RequestBody body = RequestBody.create(screenshotByte); // new
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + header)
                .addHeader("Content-Type", "image/png")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response putJson(String url, String json, String header) throws IOException {
        RequestBody body = RequestBody.create(json, JSON); // new
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + header)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response callPutApi(String endPoint, String jsonString) {
        try {
            Response response = RestApiWrapper.putJson(ConfigPropertiesHelper.getProperty("api_url") + endPoint, jsonString, AuthToken.getInstance().getAuthToken());
            return response;
        } catch (IOException e) {
            LOGGER.error(e);
            return null;
        }
    }

    /**
     * Exchanges the access token for a new or current auth token for using with subsequent Zebrunner API Calls
     *
     * @return String The exchanged authToken
     */
    public static String getAuthToken() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("refreshToken", ConfigPropertiesHelper.getProperty("access_token"));
        Path url = Paths.get(ConfigPropertiesHelper.getProperty("api_url"), ConfigPropertiesHelper.getProperty("auth_endpoint"));
        try {
            Response resp = postJson(url.toString(), jsonObject.toString(), "");
            if (resp.code() == 200) {
                return new JSONObject(resp.body().string()).getString("authToken");
            } else {
                throw new RuntimeException("Zebrunner Auth API Call returned unsuccessful code: " + resp.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Response callPostApi(String endPoint, String jsonString) {
        try {
            Response response = RestApiWrapper.postJson(ConfigPropertiesHelper.getProperty("api_url") + endPoint, jsonString, AuthToken.getInstance().getAuthToken());
            return response;
        } catch (IOException e) {
            LOGGER.error(e);
            return null;
        }
    }
}
