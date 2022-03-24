package edu.vn.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

import edu.vn.model.User;
import edu.vn.model.UserDAO;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDAO dao = new UserDAO();
		User user = new User();
		String action = request.getParameter("action");
		try {
			if (action == null || action.equals("")) {
				request.setAttribute("LIST_USER", dao.dummyData());
				RequestDispatcher rd = request.getRequestDispatcher("/views/display-user.jsp");
				rd.forward(request, response);
				return;
			}
			switch (action) {
			case "AddOrEdit": {
				String username = request.getParameter("username");
				user = dao.findByUsername(username);
				if (user == null) {
					user = new User();
				}
				request.setAttribute("USER", user);
				RequestDispatcher rd = request.getRequestDispatcher("/views/register.jsp");
				rd.forward(request, response);
				break;
			}
			case "SaveOrUpdate": {
				DateTimeConverter dtc = new DateConverter(new Date());
				dtc.setPattern("dd/MM/YYYY");
				ConvertUtils.register(dtc, Date.class);
				user = new User();
				BeanUtils.populate(user, request.getParameterMap());
				dao.save(user);
				RequestDispatcher rd = request.getRequestDispatcher("/views/register.jsp");
				rd.forward(request, response);
				break;
			}
			case "List": {
				request.setAttribute("LIST_USER", dao.getAll());
				RequestDispatcher rd = request.getRequestDispatcher("/views/display-user.jsp");
				rd.forward(request, response);
			}
			case "Delete": {
				String username = request.getParameter("username");
				if(dao.findByUsername(username) != null) {
					dao.delete(username);
				}
				request.setAttribute("LIST_USER", dao.getAll());
				RequestDispatcher rd = request.getRequestDispatcher("/views/display-user.jsp");
				rd.forward(request, response);
			}
			}

		} catch (Exception e) {

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
