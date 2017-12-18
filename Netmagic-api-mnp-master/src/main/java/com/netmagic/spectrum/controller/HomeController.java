package com.netmagic.spectrum.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private static final String HOME = "home";

    private static final String SERVER_TIME = "serverTime";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(final Locale locale, final Model model) {
        LOGGER.info("Welcome home! The client locale is {}.", locale);
        final Date date = new Date();
        final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        final String formattedDate = dateFormat.format(date);
        model.addAttribute(SERVER_TIME, formattedDate);
        return HOME;
    } 
}
