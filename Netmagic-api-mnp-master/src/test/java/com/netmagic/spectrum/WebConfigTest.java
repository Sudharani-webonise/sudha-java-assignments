package com.netmagic.spectrum;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(WebConfig.class)
public class WebConfigTest {

    @InjectMocks
    private WebConfig webConfigSpy;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private ServletContext servletContext;

    @Mock
    private AnnotationConfigWebApplicationContext dispatcherServlet;

    @Test
    public void testAddResourceHandlers() {
        ResourceHandlerRegistry registrySpy = Mockito
                .spy(new ResourceHandlerRegistry(applicationContext, servletContext));
        webConfigSpy.addResourceHandlers(registrySpy);
    }

    @Test
    public void testViewResolver() {
        webConfigSpy.viewResolver();
    }

}
