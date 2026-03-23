package com.svalero.games.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    public int code;
    public String title;
    public String message;
    private Map<String, String> errors; //mapa de errores

    //Al usar lombok con Access no hace falta esto.
//    private ErrorResponse(int code ,String title, String message, Map<String, String> errors) {
//        this.code = code;
//        this.title = title;
//        this.message = message;
//        this.errors = errors;
//
//    }


    public static ErrorResponse generalError(int code, String title, String message) {
        return new ErrorResponse(code,title,message, new HashMap<>());
    }

    public static ErrorResponse validationError(int code, String title, String message, Map<String, String> errors) {
        return new ErrorResponse(code,title,message,errors);
    }
}
