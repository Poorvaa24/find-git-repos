package com.example.demo.dao;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

@Component
public class DemoDAO {
    Logger logger = LoggerFactory.getLogger(DemoDAO.class);
    public String getRepos(String keyword) throws JSONException {
        logger.info("----------------------------An INFO Message");
        final String uri = "https://api.github.com/search/repositories?q=" + keyword + "+in:name+language:php+language:javascript";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
//        JSONObject jsonObj = new JSONObject(result);
//        JSONArray arr = jsonObj.getJSONArray();
         JSONArray resultArray = new JSONArray();
        JSONObject resultObj = new JSONObject();

        JSONObject mainObject = new JSONObject(result);

        JSONArray jsonArray = (JSONArray) mainObject.get("items");
        for(int i = 0; i < jsonArray.length(); i++)
        {
            String name = jsonArray.getJSONObject(i).getString("name");
            String full_name = jsonArray.getJSONObject(i).getString("full_name");
            JSONObject newObj = new JSONObject();
            newObj.put("id",i);
            newObj.put("name",name);
            newObj.put("fname",full_name);
            resultArray.put(newObj);

        }
        resultObj.put("repos",resultArray);

        return resultObj.toString();

    }


}
