package com.example.diet.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ChatGpt {

    private static final String api_endpoint = "https://api.openai.com/v1/completions";
    private static final String access_token = "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";  //调用chatGPT需要的api-Key

    public static void main(String[] args) throws IOException {
        // create a new http client
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // set the api request parameters
        String prompt = "hello, how are you today?";
        int maxtokens = 60;
        double temperature = 0.7;
        double topp = 1.0;
        double frequencypenalty = 0.5;
        double presencepenalty = 0.0;

        // create a new http post request
        HttpPost httppost = new HttpPost(api_endpoint);
        httppost.addHeader("content-type", "application/json");
        httppost.addHeader("authorization", "bearer " + access_token);

        // set the request body as a json String
        ObjectMapper objectmapper = new ObjectMapper();
        String requestbody = objectmapper.writeValueAsString(
                new chatgptrequest(prompt, maxtokens, temperature, topp, frequencypenalty, presencepenalty));
        httppost.setEntity(new StringEntity(requestbody));

        // send the api request and parse the response
        CloseableHttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        String responsebody = EntityUtils.toString(entity);
        EntityUtils.consume(entity);
        response.close();

        JsonNode responsejson = objectmapper.readTree(responsebody);
        String responsetext = responsejson.get("choices").get(0).get("text").asText();

        // print the response text to the console
        System.out.println("chatgpt response: " + responsetext);

        // close the http client
        httpclient.close();
    }

    static class chatgptrequest {
        public String prompt;
        public int max_tokens;
        public double temperature;
        public double top_p;
        public double frequency_penalty;
        public double presence_penalty;

        public chatgptrequest(String prompt, int maxtokens, double temperature, double topp,
                              double frequencypenalty, double presencepenalty) {
            this.prompt = prompt;
            this.max_tokens = maxtokens;
            this.temperature = temperature;
            this.top_p = topp;
            this.frequency_penalty = frequencypenalty;
            this.presence_penalty = presencepenalty;
        }
    }
}



