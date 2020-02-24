package com.example.demo.controller;

import com.example.demo.dao.DemoDAO;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @Autowired
    DemoDAO dao;

    @GetMapping("/repos")
    @ResponseBody
    public String getRepositories(@RequestParam(value = "keyword", required = false) String keyword) throws JSONException {

        if (keyword == null || keyword.isEmpty()) {
            return "No Repository Found";
        } else {
            return dao.getRepos(keyword);
        }
    }
}