package com.example.spring5mvcrest.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseControllerTest {

    public String objectToJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}