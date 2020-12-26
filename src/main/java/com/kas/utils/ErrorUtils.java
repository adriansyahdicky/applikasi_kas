package com.kas.utils;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ErrorUtils {
    public static String customErrors(List<ObjectError> errors) {
        JSONObject jsonObject = new JSONObject();
        try {
            String errorMessage = "";
            jsonObject.put("status", "failure");
            jsonObject.put("title", "Field Errors");
            for (ObjectError objectError : errors) {
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    errorMessage += "<b>" + fieldError.getField() + " :</b>" + fieldError.getDefaultMessage() + "</br>";
                }
            }
            jsonObject.put("message", errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
