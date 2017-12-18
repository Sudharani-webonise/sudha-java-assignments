package com.netmagic.spectrum.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * This class SecurityInitializer extended
 * {@link org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer}
 * and which will register their filters before any other {@link filter} This
 * means that you will typically want to ensure this class invoked first since
 * it is subclas of AbstractDispatcherServletInitializer
 * 
 * @author webonise
 * @version Spectrum 1.0.0
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}
