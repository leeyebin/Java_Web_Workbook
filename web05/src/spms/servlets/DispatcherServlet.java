package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.controls.Controller;

@SuppressWarnings("serial")
@WebServlet("*.do") //클라이언트 요청 중에서 서블릿 경로 이름이 .do로 끝나는 경우는 DispatcherServlet이 처리하겠다는 의미
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void service(// service를 오버라이딩 한 이유는 GET, POST뿐만 아니라 다양한 요청 방식에도
							// 대응하기 위해
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath(); // 서블릿의 경로를 알아내기 위해 사용
		try {
			ServletContext sc = this.getServletContext();
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("session", request.getSession());
			
			Controller pageController = (Controller)sc.getAttribute(servletPath);
			
			//매개변수 값을 사용하는 페이지 컨트롤러를 추가하더라도 조건문을 삽입할 필요가 없다. 대신 데이터 준비를 자동으로 수행하는 prepareRequestData()를 호출한다.
			//DataBinding을 구현했는지 여부를 검사하여, 해당 인터페이스를 구현한 경우에만 prepareRequestData()를 호출하여 페이지컨트롤러를 위한 데이터를 준비한다.
			if(pageController instanceof DataBinding){
				prepareRequestData(request, model, (DataBinding)pageController);
			}
			
			String viewUrl = pageController.execute(model);
			
			for(String key : model.keySet()){
				request.setAttribute(key,  model.get(key));
			}
			
			if (viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}

	private void prepareRequestData(HttpServletRequest request, HashMap<String, Object> model,
			DataBinding dataBinding) throws Exception {
		//컨트롤러에게 필요한 데이터가 무엇인지 묻는다.
		Object[] dataBinders = dataBinding.getDataBinders();
		String dataName = null;
		Class<?> dataType = null;
		Object dataObj = null;
		
		//데이터 이름과 타입을 꺼내기 쉽게 2씩 증가한다.
		for(int i=0; i<dataBinders.length; i+=2){
			dataName = (String)dataBinders[i];
			dataType = (Class<?>)dataBinders[i+1];
			dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
			model.put(dataName, dataObj);
		}
	}
}
