package com.example.patientmanagementapi.common.exceptionHandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * http 요청시 @Valid 유효성 검사로 인한 MethodArgumentNotValidException 발생시 에러 메세지 출력 용도
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        log.warn(ex.getMessage());
        ex.printStackTrace();

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Arguments Not Valid")
                        .exceptionClassName(ex.getClass().getName())
                        .errMessage(errorList)
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ExceptionResponse> handleEntityNotFoundException(
            RuntimeException ex) {

        log.warn(ex.getMessage());
        ex.printStackTrace();

        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Internal Server Error")
                        .errMessage(new ArrayList<>(Arrays.asList(ex.getMessage())))
                        .exceptionClassName(ex.getClass().getName())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
