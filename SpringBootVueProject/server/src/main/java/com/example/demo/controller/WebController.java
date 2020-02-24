package com.example.demo.controller;

import com.example.demo.dao.DemoDAO;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
public class WebController {

    @Autowired
    DemoDAO dao;

    @GetMapping("/demo")
    @ResponseBody
    public String getRepositories(@RequestParam(value = "keyword", required = false) String keyword) throws IOException, JSONException {

        if (keyword == null || keyword.isEmpty()) {
            return "Poorva";
        } else {
            return dao.getRepos(keyword);
        }
    }
}