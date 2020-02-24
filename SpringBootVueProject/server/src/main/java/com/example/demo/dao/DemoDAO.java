package com.example.demo.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DemoDAO {
    Logger logger = LoggerFactory.getLogger(DemoDAO.class);
    public String getRepos(String keyword) throws JSONException {
        final String uri = "https://api.github.com/search/repositories?q=" + keyword + "+in:name+language:php+language:javascript";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
         JSONArray resultArray = new JSONArray();
        JSONObject resultObj = new JSONObject();

        JSONObject mainObject = new JSONObject(result);

        JSONArray jsonArray = (JSONArray) mainObject.get("items");
        for(int i = 0; i < jsonArray.length(); i++)
        {
            String name = jsonArray.getJSONObject(i).getString("name");
            String full_name = jsonArray.getJSONObject(i).getString("full_name");
            String repo_url = jsonArray.getJSONObject(i).getString("html_url");
            String repo_desc = jsonArray.getJSONObject(i).getString("description");

            JSONObject ownerObj = jsonArray.getJSONObject(i).getJSONObject("owner");

            String avatar = "";
            if(ownerObj != null) {
                avatar = ownerObj.getString("avatar_url");
            }

            JSONObject newObj = new JSONObject();
            newObj.put("id",i);
            newObj.put("name",name);
            newObj.put("fname",full_name);
            newObj.put("repo_url",repo_url);
            newObj.put("repo_description",repo_desc);
            newObj.put("avatar", avatar);
            resultArray.put(newObj);
        }
        resultObj.put("repos",resultArray);
        return resultObj.toString();

    }


}
