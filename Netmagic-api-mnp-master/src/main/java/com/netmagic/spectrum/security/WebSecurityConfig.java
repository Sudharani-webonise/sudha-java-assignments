package com.netmagic.spectrum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.netmagic.spectrum.commons.AuthType;

/**
 * 
 * The class WebSecurityConfig extends
 * {@link org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter}
 * used to customize configure method
 * 
 * @see EnableWebSecurity
 * @see EnableGlobalMethodSecurity
 * 
 * @author webonise
 * @version Spectrum 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private HttpSessionSecurityContextRepository httpSessionSecurityContextRepository;

    /**
     * The method configure is overridden the default configurations
     * 
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     * 
     * @param http
     *            the {@link HttpSecurity} to modify
     * @throws Exception
     *             if an error occurs
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.securityContext().securityContextRepository(httpSessionSecurityContextRepository);
        http.authorizeRequests().antMatchers("/api/user/login").permitAll().antMatchers("/api/user/internal/login")
                .permitAll().antMatchers("/api/user/signup").permitAll().antMatchers("/api/user/verify").permitAll()
                .antMatchers("/api/user/verify/existing").permitAll().antMatchers("/api/user/password/update/tempUser")
                .permitAll().antMatchers("/api/user/password/update").permitAll()
                .antMatchers("/api/user/generate/token").permitAll().antMatchers("/api/tickets/download/**").permitAll()
                .antMatchers("/api/shoppingCart/locations").permitAll().antMatchers("/api/shoppingCart/subCategories")
                .permitAll().antMatchers("/api/shoppingCart/subSubCategories/**").permitAll()
                .antMatchers("/api/shoppingCart/products/**").permitAll().antMatchers("/api/shoppingCart/product/**")
                .permitAll().antMatchers("/api/shoppingCart/lineItem/add").permitAll()
                .antMatchers("/api/shoppingCart/lineItem/update").permitAll()
                .antMatchers("/api/shoppingCart/lineItem/read/token").permitAll()
                .antMatchers("/api/shoppingCart/lineItem/read/instanceId").permitAll()
                .antMatchers("/api/shoppingCart/cartProducts/**").permitAll().antMatchers("/api/**/**")
                .hasAnyAuthority(AuthType.USER.getAuthType(), AuthType.INTERNAL_USER.getAuthType()).and().logout();
    }

}
