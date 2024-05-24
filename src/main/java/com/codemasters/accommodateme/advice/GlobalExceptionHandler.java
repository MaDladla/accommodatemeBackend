package com.codemasters.accommodateme.advice;

import com.codemasters.accommodateme.exception.ApiException;
import com.codemasters.accommodateme.exception.ApplicationNotFoundException;
import com.codemasters.accommodateme.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
//    @ExceptionHandler(ApplicationNotFoundException.class)
//    public Map<String, String> handleInvalidArgument(ApplicationNotFoundException ex, HttpServletRequest request){
//        Map<String, String> errorMap = new HashMap<>();
//        errorMap.put(request.getRequestURI(),ex.getMessage());
//
//        return errorMap;
//    }

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<Object> applicationNotFound(ApplicationNotFoundException applicationNotFoundException ){

        ApiException exceptionApi= new ApiException(
                applicationNotFoundException.getMessage(),
                applicationNotFoundException,
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(exceptionApi,HttpStatus.NOT_FOUND);
    }

}
