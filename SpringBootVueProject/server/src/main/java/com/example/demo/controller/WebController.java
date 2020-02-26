package com.example.demo.controller;

import com.example.demo.dao.DemoDAO;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class WebController {

    @Autowired
    DemoDAO dao;

    @RequestMapping(value = "/repos/bookmarks", method = RequestMethod.GET)
    public String getAllBookmarks() throws JSONException, IOException {

        return dao.getAllBookmarks();
    }

    @RequestMapping(value = "/repos", method = RequestMethod.GET)
    public String getRepositories(@RequestParam(name = "keyword", required = false) String keyword) throws JSONException, IOException {
        if (keyword == null || keyword.isEmpty()) {
            return "No Repository Found";
        } else {
            return dao.getRepos(keyword);
        }
    }

    @RequestMapping(value = {"/repos/bookmarks"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String addBookmark(@RequestParam(name = "url",required = true) String url) throws JSONException, IOException {

        return dao.addBookMark(url);
    }

    @RequestMapping(value = {"/repos/bookmarks"}, method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public String removeBookmark(@RequestParam(name = "url",required = true) String url) throws JSONException, IOException {

        return dao.removeBookMark(url);
    }
}