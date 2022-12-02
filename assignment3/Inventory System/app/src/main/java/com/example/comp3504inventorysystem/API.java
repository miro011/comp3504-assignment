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

    /**
     * Add a new item to inventory (not just increase qty).
     * @param item
     * @return "success" || "some error message"
     */
    public String addItem(Item item)
    {
        JSONObject response = talkToApi("POST", item.toJsonString());
        if (apiResponseError(response)) return apiResponseGetError(response);
        else return "success";
    }

    /**
     * Remove an item from inventory entirely (not just zero qty).
     * @param id
     */
    public void removeItem(int id) {
        System.out.format("Removing item %d\n", id);
    }

    /**
     * Search for an item by the ID.
     * @param id
     * @return A list of matching results
     */
    public ArrayList<Item> searchItem(int id) {
        System.out.format("Searching items by id=%d", id);
        return new ArrayList<Item>();
    }

    /**
     * Search for an item by a regex matched against item names.
     * @param nameRegex
     * @return A list of matching results
     */
    public ArrayList<Item> searchItem(String nameRegex) {
        System.out.format("Searching items by nameRegex like %s", nameRegex);
        return new ArrayList<Item>();
    }

    /**
     * Get all items in inventory and their information.
     * @return A list of all items currently in inventory
     */
    public ArrayList<Item> getItems() {
        System.out.println("Getting all items");
        return new ArrayList<Item>();
    }

    /**
     * Modifies the contents of the item in inventory specified by newItem.id.
     * Overwrites the entire item, so if you don't want something to change, set it to the current
     * value.
     * @param newItem ID is the item to overwrite, all other fields are the new values.
     */
    public void modifyItem(Item newItem) {
        System.out.format("Modifying item %d\n", newItem);
    }


    // requestType: "GET" || "POST" || "PUT" || "DELETE" / jsonParams ex: '{"id":"3001", "qty":"bye"...}'
    // @returns a JSONObject
    // {"0":{"itemID":6666,"itemName":"hello","price":12.1,"quantity":10,"supplierID":50001}}
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
        String response;
        HttpURLConnection connection = null;
        try {
            // API URL + GET request
            String apiUrl = "http://34.105.39.147/items/";
            //String apiUrl = "http://127.0.0.1:7777/items/";
            if (requestType == "GET") {
                try {apiUrl += "?json=" + URLEncoder.encode(jsonParams, "UTF-8");}
                catch(Exception e) {}
            }

            // Create connection
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Set body with non-GET requests
            if (requestType != "GET") {
                OutputStream os = connection.getOutputStream();
                os.write(jsonParams.getBytes("UTF-8"));
                os.close();
            }

            // Get response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = in.readLine();
            in.close();
            if (!response.startsWith("{")) {
                response = "{\"error\":" + "\"API: " + response + "\"}";
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

    public String apiResponseGetError(JSONObject jsonObj)
    {
        try {
            return jsonObj.get("error").toString();
        } catch(Exception e) {
            return "";
        }
    }

    // don't know what to call this function yet
    // it takes the output of talktoapi and loops through it
    public void itterateJson(JSONObject jsonObj)
    {
        /*
        Iterator<String> keys = jsonObj.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            //System.out.println(key);

            // After having retrieved item details
            if (jsonObj.get(key) instanceof JSONObject) {
                // do something
            }
            // {"success":true} & {"error":"some error"}
            else {
                // do something
            }
        }
        System.out.println(jsonObj.toString());
        */
    }

}
