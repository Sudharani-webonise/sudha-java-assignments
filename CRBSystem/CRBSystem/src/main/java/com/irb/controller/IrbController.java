package com.irb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.irb.implConn.ConferenceImpl;
import com.irb.implConn.SheduledImpl;

public class IrbController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String target = "index.jsp";
		String uri = request.getRequestURI();

		if (uri.endsWith("select.action")) {
			ConferenceImpl rooms = new ConferenceImpl();
			ArrayList roomlist = rooms.getData();
			System.out.println("hi" + roomlist);

			request.setAttribute("roomList", roomlist);
			target = "select.jsp";
		}

		if (uri.endsWith("status.action")) {
			//int retval=arrlist.get(3);
			String roomnumber = request.getParameter("roomN");
			//String  floorNumber="3";
			SheduledImpl roomstatus=new SheduledImpl();
			
			System.out.println(roomnumber);
			ArrayList availRoom= roomstatus.checkStatus(roomnumber);
			String  floorNumber="5";
			
			request.setAttribute("availabeRoomList", availRoom);
			request.setAttribute("roomnumber", roomnumber);
			request.setAttribute("floorNumber", floorNumber);
			System.out.println(availRoom);
			target = "showStatus.jsp";
		}
		
		
		if(uri.endsWith("book.action")){
			SheduledImpl shedule=new SheduledImpl();
			String roomId=request.getParameter("roomId");
			String empId=request.getParameter("empId");
			String comment=request.getParameter("comment");
			String date=request.getParameter("date");
			String fromTime=request.getParameter("fromTime");
			String toTime=request.getParameter("toTime");
			String florno=request.getParameter("floor");
			
			try {
				shedule.insertBookedRoom(roomId,empId,comment,date,florno,fromTime, toTime);
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
			
			System.out.println(roomId+empId+comment+date+fromTime+toTime);
			target="done.jsp";
		}

		RequestDispatcher rd = request.getRequestDispatcher(target);
		rd.forward(request, response);
	}

}
