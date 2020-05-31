package com.aa183.wibiana.model;


import com.aa183.wibiana.model.Film;

import java.util.List;

public class ResponseData {

    private String value;
    private String message;
    private List<Film> result;

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<Film> getResult() {
        return result;
    }
}