package com.saucelabs.helper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by mattdunn on 15/08/2018.
 */
public class RDCAssetHelper {

    private final String username;

    private final String apiKey;

    private final String apiUrl;

    public RDCAssetHelper(String username, String apiKey, String apiUrl) {

        this.username = username;
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    public HashMap<String,String> getAssets() throws UnirestException, InterruptedException {

        HashMap<String,String> assets = new HashMap<String,String>();

        HttpResponse<JsonNode> response = Unirest.get(apiUrl).basicAuth(username, apiKey).asJson();

        // retrieve the parsed JSONObject from the response
        JSONObject myObj = response.getBody().getObject();

        System.out.println(myObj.toString());

        return assets;
    }

    public String getReport() throws UnirestException {

        HttpResponse<JsonNode> response = Unirest.get(apiUrl).basicAuth(username, apiKey).asJson();

        return response.getBody().toString();

    }
}
