package com.sample;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GSHandler
 */
@WebServlet("/GSHandler")
public class GSHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String apikey ="29a25d75b09d4bebcbaa4ea47418577d";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GSHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String contextobj = request.getParameter("contextobj");
		String botname  = request.getParameter("botname");
		String messageobj = request.getParameter("messageobj");
		String senderobj = request.getParameter("senderobj");
		System.out.println("contextobj:"+contextobj);
		System.out.println("botname:"+botname);
		System.out.println("messageobj:"+messageobj);
		System.out.println("senderobj:"+senderobj);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
