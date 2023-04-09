package com.eyerubic.socialintegrator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.eyerubic.socialintegrator.Constants;
import com.eyerubic.socialintegrator.helpers.AppLogger;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

/**
 * This class handles all the exceptions occurs in controller level and send appropriate
 * response payload. ControllerAdvice annotation captures all the exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Resource(name = "requestScopedBeanAppLogger")
    AppLogger appLogger;

    private static final String ERROR_LOG_MSG = "Error occured. Error Msg:";

    /**
     * Handles missing parameter exception
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> missingServletRequestParameterException(
        MissingServletRequestParameterException e) {

        ErrorResponse errorResponse = new ErrorResponse(Constants.CODE_MISSING_MANDATORY_FIELD, 
            Constants.MSG_MISSING_MANDATORY_FIELD, e.getParameterName());
            
        appLogger.error(ERROR_LOG_MSG + e.getMessage());

        return new ResponseEntity<>(
            errorResponse,
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<FieldError> bindingResult = e.getBindingResult().getFieldErrors();
        FieldError fieldError = bindingResult.get(0);

        int errorCode = Constants.CODE_INVALID_INPUT;
        String errorMsg = Constants.MSG_INVALID_INPUT;

        String errorIdentifier = fieldError.getCode();

        if (errorIdentifier != null) {
            switch (errorIdentifier) {
                case "Size":
                    errorCode = Constants.CODE_EXCEED_MAX_CHAR_LENGTH;
                    errorMsg = Constants.MSG_EXCEED_MAX_CHAR_LENGTH;
                    break;

                case "NotEmpty":
                    errorCode = Constants.CODE_MISSING_MANDATORY_FIELD;
                    errorMsg = Constants.MSG_MISSING_MANDATORY_FIELD;
                    break;

                default:
                    errorCode = Constants.CODE_UNKNONW;
                    errorMsg = "";
                    break;
            }
        }

        ErrorResponse errorResponse = new ErrorResponse(errorCode, errorMsg, fieldError.getField());
            
        appLogger.error(ERROR_LOG_MSG + errorMsg + ", Attribute:" + fieldError.getField());

        return new ResponseEntity<>(
            errorResponse,
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customException(CustomException e) {

        ErrorResponse errorResponse = new ErrorResponse(e.getCode(), e.getMessage(), e.getAttribute());

        appLogger.error(ERROR_LOG_MSG + e.getMessage() + ", " + e.getData());

        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        if (e.getHttpStatus() != null) {
            httpStatus = e.getHttpStatus();
        }

        return new ResponseEntity<>(
            errorResponse,
            httpStatus
        );
    }

    /**
     * Handles any other unhandled exceptions
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(ServletRequest servletRequest, final Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(Constants.CODE_INTERNAL_ERROR, 
            Constants.MSG_INTERNAL_ERROR, "");

        appLogger.error(ERROR_LOG_MSG + e.getMessage());

        return new ResponseEntity<>(
            errorResponse,
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
