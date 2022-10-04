package com.solvd.testing.api;

import com.google.gson.JsonObject;
import com.solvd.testing.api.domain.*;
import com.solvd.testing.api.utils.FileUtils;
import com.solvd.testing.api.utils.RequestUpdate;
import com.solvd.testing.api.utils.ResponseUtils;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

import static com.solvd.testing.api.utils.FileUtils.removeInitialSpaces;


public class ZebrunnerAPI extends BaseClass {

    private final String endpoint = properties.getProperty("URL");
    private final ResponseDTO DATA = new ResponseDTO();
    private static ZebrunnerAPI INSTANCE;

    private ZebrunnerAPI() {}
    public void tokenGeneration() {
        String token = FileUtils.propertyValue("access-token").get("access-token");
        TokenGenerationDTO bodyObject = new TokenGenerationDTO(token);
        String tokenGenerationEndpoint = endpoint.concat(FileUtils.readValueInProperties(endpointPath, "ENP_TOKEN_GENERATE"));

        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);
        Request request = new Request.Builder()
                .url(tokenGenerationEndpoint)
                .post(body)
                .build();
        
        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();

            response.body().close();

           DATA.setAccessToken(ResponseUtils.splitResponse(bodyResponse, "\"authToken\""));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void testStartRequest(JsonObject endpointData) {

        String projectKey = removeInitialSpaces(FileUtils.propertyValue("project-key").get("project-key"));

        String endpointTestStart = endpoint.concat(RequestUpdate.addQueryParamsValue("ENP_RUN_START", projectKey));

        TestStartDTO bodyObject = new TestStartDTO(
                endpointData.get("name").getAsString(),
                endpointData.get("framework").getAsString());
        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        Request request = new Request.Builder()
                .url(endpointTestStart)
                .post(body)
                .addHeader("Authorization", "Bearer " + DATA.getAccessToken())
                .build();
        System.out.println(endpointTestStart);
        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();
            System.out.println("response: " + bodyResponse);

            response.body().close();

            DATA.setRunId(ResponseUtils.splitResponse(bodyResponse, "\"id\""));
            
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void testExecutionStart(JsonObject endpointData) {

        String endpointTestExecutionStart = endpoint
                .concat(FileUtils.readValueInProperties(endpointPath,"ENP_EXECUTION"))
                .concat(DATA.getRunId())
                .concat("/tests");

        TestExecutionStartDTO bodyObject = new TestExecutionStartDTO(
                endpointData.get("name").getAsString(),
                endpointData.get("className").getAsString(),
                endpointData.get("methodName").getAsString());

        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        Request request = new Request.Builder().url(endpointTestExecutionStart)
                .post(body)
                .addHeader("Authorization", "Bearer " + DATA.getAccessToken())
                .build();

        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();

            response.body().close();
            DATA.setTestId(ResponseUtils.splitResponse(bodyResponse, "\"id\""));
            
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }


    public void testExecutionFinishRequest(JsonObject endpointData){
        String endpointTestExecutionFinishRun = endpoint
                .concat(FileUtils.readValueInProperties(endpointPath,"ENP_EXECUTION"))
                .concat(DATA.getRunId())
                .concat("/tests/")
                .concat(DATA.getTestId());

        TestExcecutionFinishDTO bodyObject = new TestExcecutionFinishDTO(
                endpointData.get("result").getAsString(),
                endpointData.get("endedAt").getAsString());
        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        Request request = new Request.Builder()
                .url(endpointTestExecutionFinishRun)
                .put(body)
                .addHeader("Authorization", "Bearer " + DATA.getAccessToken())
                .build();

        try  {
            Response response = client.newCall(request).execute();
            response.body().close();

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void testRunFinishRequest(JsonObject endExecution){
        String endpointTestFinishRun = endpoint
                .concat(FileUtils.readValueInProperties(endpointPath,"ENP_RUN_FINISH"))
                .concat(DATA.getRunId());

        TestRunFinishDTO bodyObject = new TestRunFinishDTO(endExecution.get("endedAt").getAsString());
        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        Request request = new Request.Builder().url(endpointTestFinishRun)
                .put(body)
                .addHeader("Authorization", "Bearer " + DATA.getAccessToken())
                .build();

        try  {
            Response response = client.newCall(request).execute();

            response.body().close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static ZebrunnerAPI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ZebrunnerAPI();
        }
        return INSTANCE;
    }
}
