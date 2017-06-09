package com.onlineStore.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlineStore.domain.Category;
import com.onlineStore.service.AdminCategoryService;
import com.onlineStore.utils.CommonUtils;

public class AdminCategoryServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void adminCategoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminCategoryService adminCategoryService = new AdminCategoryService();
		List<Category> categoryList = adminCategoryService.findCategoryList();
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
	}
	
	public void editCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		request.setAttribute("cid", cid);
		String cname = request.getParameter("cname");
		request.setAttribute("cname", cname);
		request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);
	}
	
	public void submitEditCategoryName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminCategoryService adminCategoryService = new AdminCategoryService();
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		adminCategoryService.editCategoryName(cid, cname);
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=adminCategoryList");
	}
	
	public void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cname = request.getParameter("cname");
		AdminCategoryService adminCategoryService = new AdminCategoryService();
		String cid = CommonUtils.getUUID();
		adminCategoryService.addCategory(cname, cid);
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=adminCategoryList");
	}
	
	public void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String cid = request.getParameter("cid");
		AdminCategoryService adminCategoryService = new AdminCategoryService();
		try {
			adminCategoryService.deleteCategory(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath() + "/adminCategory?method=adminCategoryList");
		
	}

}