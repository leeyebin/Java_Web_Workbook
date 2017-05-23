package spms.temp;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

// MemberDao 사용  
@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
  @Override
  protected void doGet(
      HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      ServletContext sc = this.getServletContext();
      MySqlMemberDao memberDao = (MySqlMemberDao)sc.getAttribute("memberDao");
       
      Member member = memberDao.selectOne(
          Integer.parseInt(request.getParameter("no")));

      request.setAttribute("member", member);

      request.setAttribute("viewUrl", "/member/MemberUpdateForm.jsp");
      /*RequestDispatcher rd = request.getRequestDispatcher(
          "/member/MemberUpdateForm.jsp");
      rd.forward(request, response);*/

    } catch (Exception e) {
    	throw new ServletException(e);
      /*e.printStackTrace();
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);*/
    }
  }

  @Override
  protected void doPost(
      HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      ServletContext sc = this.getServletContext();
      MySqlMemberDao memberDao = (MySqlMemberDao)sc.getAttribute("memberDao");
      
      Member member = (Member)request.getAttribute("member");
      memberDao.update(member);
      
      request.setAttribute("viewUrl", "redirect:list.do");
      /*memberDao.update(new Member()
      .setNo(Integer.parseInt(request.getParameter("no")))
      .setName(request.getParameter("name"))
      .setEmail(request.getParameter("email")));

      response.sendRedirect("list");*/

    } catch (Exception e) {
    	throw new ServletException(e);
      /*e.printStackTrace();
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);*/
    }
  }
}
