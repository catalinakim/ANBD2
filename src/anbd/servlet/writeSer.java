package anbd.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import anbd.*;

/**
 * Servlet implementation class writeSer
 */
@WebServlet("/writeSer")
public class writeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public writeSer() {super();}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("This is doGet()...");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("This is doPost()...");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/x-json;charset=utf-8");
		HttpSession session = request.getSession();
		PrintWriter out		= response.getWriter();
		AnbdDAO 	dao 	= new AnbdDAO();
		AnbdVO 		vo 		= new AnbdVO();
		
		
		//String id = (String)session.getAttribute("loginId");
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		vo.setUploadPath(uploadPath);
		
		
		dao.inWrite(vo, request, vo.getLoginUserNo());
//		out.print("글 번호 = "+vo.getNo());
//		out.print("메뉴 = "+vo.getMenu());
//		out.print("유저 번호 = "+userNo);
//		//vo.setUserNo(userNo);
//		System.out.println("글 번호 = "+vo.getNo());
//		//System.out.println("jsp 유저 번호 = "+userNo);
//		System.out.println("vo 유저 번호 = "+vo.getUserNo());
//
//		if(vo.getMenu().equals("아나"))
//		{
//			out.print("메뉴 = share");
//			System.out.println("메뉴 = share");
//		}
//		else if(!vo.getMenu().equals("아나")) 
//		{
//			out.print("메뉴 = reuse");
//			System.out.println("메뉴 = reuse");
//		}
//		//String url = "/main/write.jsp?no="+userNo+"&menu=share";
//		String url = "/main/write.jsp?menu=share";
//		
//		request.setAttribute((vo.getNo()+""), vo.getNo());
//		request.setAttribute((vo.getMenu()), vo.getMenu());
//		//response.sendRedirect(url);
		
//		RequestDispatcher dis = request.getRequestDispatcher(url); //클라이언트 요청을 
//		dis.forward(request, response);
		
	}//doPost METHOD

}
