package com.example.comp3504inventorysystem;

import java.util.ArrayList;
import org.json.JSONObject;
import java.util.Iterator;
import java.net.*;
import java.io.*;

// to prevent API error "NetworkOnMainThreadException"
import android.os.StrictMode;

public class API {
    private String host;
    private int port;

    public API(String host, int port) {
        this.host = host;
        this.port = port;

        // to prevent API error "NetworkOnMainThreadException"
        // https://stackoverflow.com/a/9289190
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    // requestType: "GET" || "POST" || "PUT" || "DELETE" / jsonParams ex: '{"id":"3001", "qty":"bye"...}'
    // @returns a JSONObject
    // {"0":{"itemID":6666,"itemName":"hello","price":12.1,"quantity":10,"supplierID":50001}} || {}
    // {"success":true}
    // {"error":"some error"}
    // testing
    //talkToApi("GET", "{\"id\":\"3001\"}");
    //talkToApi("POST", "{\"id\":\"6666\", \"name\":\"hello\", \"qty\":\"10\", \"price\":\"12.10\", \"sid\":\"50001\"}");
    //talkToApi("PUT", "{\"id\":\"6666\", \"qty\":\"20\"}");
    //talkToApi("DELETE", "{\"id\":\"6666\"}");
    // help from:
    // https://www.digitalocean.com/community/tutorials/java-httpurlconnection-example-java-http-request-get-post
    // https://stackoverflow.com/a/34150284

    public JSONObject talkToApi(String requestType, String jsonParams)
    {
        String response = "";
        HttpURLConnection connection = null;
        try {
            // API URL + GET request
            String apiUrl = "http://34.105.39.147/items/";
            //String apiUrl = "http://127.0.0.1:7777/items/";
            if (requestType.equals("GET")) {
                try {apiUrl += "?json=" + URLEncoder.encode(jsonParams, "UTF-8");}
                catch(Exception e) {}
            }

            // Create connection
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestType);

            // Set body with non-GET requests
            if (!requestType.equals("GET")) {
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);
                OutputStream os = connection.getOutputStream();
                os.write(jsonParams.getBytes("UTF-8"));
                os.close();
            }

            // Response status code
            int status = connection.getResponseCode();
            if (status == 400) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    if (!line.startsWith("<p>")) continue;
                    // remove <p> and </p>
                    line = line.substring(3);
                    line = line.substring(0, line.length() - 4);
                    response = "{\"error\":" + "\"API 400: " + line + "\"}";
                    break;
                }
                in.close();
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                response = in.readLine();
                in.close();
                if (!response.startsWith("{")) {
                    response = "{\"error\":" + "\"API: " + response + "\"}";
                }
            }
        }
        catch (Exception e) {
            response = "{\"error\":" + "\"" + e + "\"}";
        }

        JSONObject output = null;
        try {output = new JSONObject(response);}
        catch (Exception e) {}

        return output;
    }

    public Boolean apiResponseError(JSONObject jsonObj)
    {
        if (jsonObj.toString().startsWith("{\"error\"")) return true;
        else return false;
    }

    public Boolean apiResponseSuccess(JSONObject jsonObj)
    {
        if (jsonObj.toString().startsWith("{\"success\"")) return true;
        else return false;
    }

    public Boolean apiResponseEmpty(JSONObject jsonObj)
    {
        if (jsonObj.toString().equals("{}")) return true;
        else return false;
    }

    public String apiResponseGetError(JSONObject jsonObj)
    {
        try {
            return jsonObj.get("error").toString();
        } catch(Exception e) {
            return "";
        }
    }

    public ArrayList<Item> getItemsListFromApiResponse(JSONObject jsonObj, String jsonStr)
    {
        ArrayList<Item> output = new ArrayList<Item>();

        if (jsonStr != null) {
            try {
                jsonObj = new JSONObject(jsonStr);
            } catch(Exception e) {
                return output;
            }
        }

        Iterator<String> keys = jsonObj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            try {
                String singleItemJsonStr = jsonObj.get(key).toString();
                JSONObject singleItemJsonObj = new JSONObject(singleItemJsonStr);

                int id = Integer.parseInt(singleItemJsonObj.get("itemID").toString());
                String name = singleItemJsonObj.get("itemName").toString();
                int quantity = Integer.parseInt(singleItemJsonObj.get("quantity").toString());
                float price = Float.parseFloat(singleItemJsonObj.get("price").toString());
                int supplierId = Integer.parseInt(singleItemJsonObj.get("supplierID").toString());

                output.add(new Item(id, name, quantity, price, supplierId));
            } catch(Exception e) {
                continue;
            }
        }

        return output;
    }
}
