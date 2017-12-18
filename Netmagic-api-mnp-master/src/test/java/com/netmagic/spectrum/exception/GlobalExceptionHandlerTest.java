package com.netmagic.spectrum.exception;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.netmagic.spectrum.dto.ErrorInfo;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandlerSpy;

    @Mock
    private HttpServletRequest httpServletReqSpy;

    @Mock
    private Exception exceptionSpy;

    private ResponseEntity<ErrorInfo> entity;

    @Test
    public void testIllegalArgException() {
        entity = exceptionHandlerSpy.handleIllegalArgException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testCacheServiceException() {
        entity = exceptionHandlerSpy.handleCacheServiceException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testPasswordExpiredException() {
        entity = exceptionHandlerSpy.handlePasswordExpiredException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.LOCKED);
    }

    @Test
    public void testRequestViolationException() {
        entity = exceptionHandlerSpy.handleRequestViolationException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testResourceAccessException() {
        entity = exceptionHandlerSpy.handleResourceAccessException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.BAD_GATEWAY);
    }

    @Test
    public void testUnauthorizedException() {
        entity = exceptionHandlerSpy.handleUnauthorizedException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testUnAuthUserException() {
        entity = exceptionHandlerSpy.handleUnAuthUser(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testUserNotException() {
        entity = exceptionHandlerSpy.handleUserNotException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDataNotFoundExceptionForFile() {
        entity = exceptionHandlerSpy.handleDataNotFoundException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUnProcessableExceptionForFile() {
        entity = exceptionHandlerSpy.handleUnProcessableException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testDuplicateDataExceptionForFile() {
        entity = exceptionHandlerSpy.handleDuplicateDataException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void testWrongBillGroupExceptionForFile() {
        entity = exceptionHandlerSpy.handleWrongBillGroupException(httpServletReqSpy, exceptionSpy);
        assertEquals(entity.getStatusCode(), HttpStatus.CONFLICT);
    }
}
