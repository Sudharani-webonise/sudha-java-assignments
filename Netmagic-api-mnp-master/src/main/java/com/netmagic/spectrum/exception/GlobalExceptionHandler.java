package com.netmagic.spectrum.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

import com.netmagic.spectrum.dto.ErrorInfo;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> handleIllegalArgException(HttpServletRequest httpServletReq, Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorInfo> handleUnAuthUser(HttpServletRequest httpServletReq, Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorInfo> handleResourceAccessException(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleUserNotException(HttpServletRequest httpServletReq, Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestViolationException.class)
    public ResponseEntity<ErrorInfo> handleRequestViolationException(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CacheServiceException.class)
    public ResponseEntity<ErrorInfo> handleCacheServiceException(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorInfo> handleResourceAccessExceptionForFile(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorInfo> handleUnauthorizedException(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordExpiredException.class)
    public ResponseEntity<ErrorInfo> handlePasswordExpiredException(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.LOCKED);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleDataNotFoundException(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnProcessableException.class)
    public ResponseEntity<ErrorInfo> handleUnProcessableException(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ErrorInfo> handleDuplicateDataException(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongBillGroupException.class)
    public ResponseEntity<ErrorInfo> handleWrongBillGroupException(HttpServletRequest httpServletReq,
            Exception exception) {
        return getErrorInfoResponseEntity(httpServletReq, exception, HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorInfo> getErrorInfoResponseEntity(HttpServletRequest httpServletReq, Exception exception,
            HttpStatus httpStatus) {
        ErrorInfo error = new ErrorInfo();
        error.setMessage(exception.getLocalizedMessage());
        error.setUri(httpServletReq.getRequestURI());
        return new ResponseEntity<ErrorInfo>(error, httpStatus);
    }

}