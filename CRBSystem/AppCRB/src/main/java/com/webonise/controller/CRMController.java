package com.webonise.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.webonise.entity.ConferenceRooms;
import com.webonise.impl.ConferenceImpl;
import com.webonise.impl.SheduledImpl;

@Controller
public class CRMController {

	private static final Logger logger = LoggerFactory
			.getLogger(CRMController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		return "home";
	}

	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public String getdata(Locale locale, Model model) {
		ConferenceImpl rooms = new ConferenceImpl();
		ArrayList<ConferenceRooms> roomlist = rooms.getData();
		model.addAttribute("roomList", roomlist);
		return "select";
	}

	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public String Staus(@RequestParam String roomN, Model model) {
		String roomnumber = roomN;
		SheduledImpl roomstatus = new SheduledImpl();
		int floorNumber = roomstatus.getFloorNumber(roomnumber);
		model.addAttribute("roomnumber", roomnumber);
		model.addAttribute("floorNumber", floorNumber);
		return "showStatus";
	}

	@RequestMapping(value = "/book", method = RequestMethod.POST)
	public String book(@RequestParam String roomId, @RequestParam String empId,
			@RequestParam String comment, @RequestParam String date,
			@RequestParam String fromTime, @RequestParam String toTime,
			@RequestParam String floor, Model model) {
		SheduledImpl shedule = new SheduledImpl();
		try {
			shedule.insertBookedRoom(roomId, empId, comment, date, floor,
					fromTime, toTime);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(roomId + empId + comment + date + fromTime + toTime);
		return "done";
	}
}
