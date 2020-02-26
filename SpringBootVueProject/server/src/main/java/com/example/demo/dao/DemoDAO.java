package com.example.demo.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Component
public class DemoDAO {
    Logger logger = LoggerFactory.getLogger(DemoDAO.class);
    public String getRepos(String keyword) throws JSONException, IOException {
        final String uri = "https://api.github.com/search/repositories?q=" + keyword + "+in:name+language:php+language:javascript";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
         JSONArray resultArray = new JSONArray();
        JSONObject resultObj = new JSONObject();

        JSONObject mainObject = new JSONObject(result);
        Set<String> bookMarkedUrls = readBookmarkFile();
        JSONArray jsonArray = (JSONArray) mainObject.get("items");
        for(int i = 0; i < jsonArray.length(); i++)
        {
            int isBookmarked = 0;
            String name = jsonArray.getJSONObject(i).getString("name");
            String full_name = jsonArray.getJSONObject(i).getString("full_name");
            String repo_url = jsonArray.getJSONObject(i).getString("html_url");
            String repo_desc = jsonArray.getJSONObject(i).getString("description");

            JSONObject ownerObj = jsonArray.getJSONObject(i).getJSONObject("owner");

            String avatar = "";
            if(ownerObj != null) {
                avatar = ownerObj.getString("avatar_url");
            }

            if(bookMarkedUrls.contains(repo_url))
            {
                isBookmarked = 1;
            }
            JSONObject newObj = new JSONObject();
            newObj.put("id",i);
            newObj.put("name",name);
            newObj.put("fname",full_name);
            newObj.put("repo_url",repo_url);
            newObj.put("repo_description",repo_desc);
            newObj.put("avatar", avatar);
            newObj.put("isBookmarked",isBookmarked);
            resultArray.put(newObj);
        }
        resultObj.put("repos",resultArray);
        return resultObj.toString();

    }

    public String addBookMark(String url) throws IOException, JSONException {
        String result = "Repository already bookmarked";
        Set<String> bookMarkedUrls = readBookmarkFile();
        if(!bookMarkedUrls.contains(url))
        {
            bookMarkedUrls.add(url);
            writingBookmarkedReposTotxt(bookMarkedUrls);
            addLog(url,true);
            result = "Success";
        }
        JSONObject resultObj = new JSONObject();
        resultObj.put("result",result);
        return resultObj.toString();
    }

    public String removeBookMark(String url) throws IOException, JSONException {
        String result = "Repository not bookmarked";
        Set<String> bookMarkedUrls = readBookmarkFile();
        if(bookMarkedUrls.contains(url))
        {
            bookMarkedUrls.remove(url);
            writingBookmarkedReposTotxt(bookMarkedUrls);
            addLog(url,false);
            result = "Removed repository from bookmarks";
        }
        JSONObject resultObj = new JSONObject();
        resultObj.put("result",result);
        return resultObj.toString();
    }

    public String getAllBookmarks() throws IOException, JSONException {

        Set<String> bookMarkedUrls = readBookmarkFile();
        JSONObject resultObj = new JSONObject();
        JSONArray resultArray = new JSONArray();
        int count = 0;

        for(String url : bookMarkedUrls)
        {
            JSONObject newObj = new JSONObject();
            newObj.put("id", count++);
            newObj.put("url", url);
            resultArray.put(newObj);
        }
        resultObj.put("bookmarks",resultArray);
        return resultObj.toString();
    }

    private void addLog(String url, boolean op) throws IOException {

        String operation = "";
        if(op) {
            operation = "write";
        } else {
            operation = "remove";
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String entry = now + " " + operation + " " + url;

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource  = classLoader.getResource("event_log.txt");

        if(resource == null)
        {
            //System.out.println("************ file not found *********** ");
        }
        else {
            File f = new File(resource.getFile());
            if(f != null)
            {
                FileWriter fileWriter = new FileWriter(f, true);
                if(fileWriter != null)
                {
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    if(bufferedWriter != null)
                    {
                        bufferedWriter.write(entry);
                        bufferedWriter.newLine();

                        bufferedWriter.close();
                    }
                }
            }
        }
    }


    public void writingBookmarkedReposTotxt(Set<String> bookmarkedUrls) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource  = classLoader.getResource("bookmarks.txt");

        if(resource == null)
        {
            //System.out.println("************ file not found *********** ");
        }
        else {
            File f = new File(resource.getFile());
            if(f != null)
            {
                FileWriter fileWriter = new FileWriter(f, false);
                if(fileWriter != null)
                {
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    if(bufferedWriter != null)
                    {
                        for(String url:bookmarkedUrls)
                        {
                            bufferedWriter.write(url);
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.close();
                    }
                }
            }
        }
    }

    public Set<String> readBookmarkFile() throws IOException
    {
        HashSet<String> bookMarkedUrls = new HashSet<>();

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource  = classLoader.getResource("bookmarks.txt");

//        // input stream
//        InputStream inputStream = classLoader.getResourceAsStream("bookmarks.txt");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//        if(bufferedReader != null)
//        {
//            String line = "";
//            while((line = bufferedReader.readLine()) != null)
//            {
//                //System.out.println(line);
//                bookMarkedUrls.add(line);
//            }
//        }

        if(resource == null)
        {
        }
        else
        {
            File f = new File(resource.getFile());
            if(f != null)
            {
                FileReader fr = new FileReader(f);
                if(fr != null)
                {
                    BufferedReader bfr = new BufferedReader(fr);
                    if(bfr != null)
                    {
                        String line = "";
                        while((line = bfr.readLine()) != null)
                        {
                            //System.out.println(line);
                            bookMarkedUrls.add(line);
                        }
                    }
                }
            }
        }

        return bookMarkedUrls;
    }
}
