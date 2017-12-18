package com.netmagic.spectrum.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ HomeController.class })
public class HomeControllerTest {

    private static final String HOME = "home";

    private MockMvc mockMvc;

    @InjectMocks
    private HomeController homeControllerSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeControllerSpy).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name(HOME));
    }

}
