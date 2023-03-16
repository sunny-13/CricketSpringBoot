package com.project.cricket.advice;

import com.project.cricket.exception.InvalidIdException;
import com.project.cricket.exception.ScoreCardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidIdException.class)
    public Map<String,String> handleInvalidIdException( InvalidIdException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Error",ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ScoreCardNotFoundException.class)
    public Map<String,String> handleScoreCardNotFoundException( ScoreCardNotFoundException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Error",ex.getMessage());
        return errorMap;
    }

}
