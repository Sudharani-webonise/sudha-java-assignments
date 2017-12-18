package com.netmagic.spectrum;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(Application.class)
public class ApplicationTest {

    @InjectMocks
    private Application applicationSpy;

    @Mock
    private ServletContext servletContext;

    @Mock
    private AnnotationConfigWebApplicationContext dispatcherServlet;

    @Mock
    private ServletRegistration.Dynamic dispatcher;

    @Test
    public void testOnstartUp() {
        Mockito.when(servletContext.addServlet(Mockito.anyString(), Mockito.any(DispatcherServlet.class)))
                .thenReturn(dispatcher);
        applicationSpy.onStartup(servletContext);
    }

}
