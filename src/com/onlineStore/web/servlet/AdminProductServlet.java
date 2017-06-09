package com.onlineStore.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.onlineStore.domain.Product;
import com.onlineStore.service.AdminProductService;
import com.onlineStore.service.ProductService;
import com.onlineStore.utils.CommonUtils;

public class AdminProductServlet extends BaseServlet {

	public void adminProductList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminProductService adminProductService = new AdminProductService();
		List<Product> productList = adminProductService.adminProductList();
		request.setAttribute("productList", productList);
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	}
	
	public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AdminProductService adminProductService = new AdminProductService();
		String pid = request.getParameter("pid");
		adminProductService.deleteProductById(pid);
		response.sendRedirect(request.getContextPath() + "/adminProduct?method=adminProductList");
	}
	
	public void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AdminProductService adminProductService = new AdminProductService();
		Product product = new Product();
		Map<String, Object> map = new HashMap<>();

		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			@SuppressWarnings("unchecked")
			List<FileItem> parseRequest = upload.parseRequest(request);
			for(FileItem fileItem : parseRequest) {
				boolean isFormField = fileItem.isFormField();
				if (isFormField) {
					String fieldName = fileItem.getFieldName();
					String fieldValue = fileItem.getString("UTF-8");
					map.put(fieldName, fieldValue);
				} else {
					String filename = fileItem.getName();
					String path = request.getServletContext().getRealPath("upload");
					InputStream in = fileItem.getInputStream();
					OutputStream out = new FileOutputStream(path + "/" + filename);
					IOUtils.copy(in, out);
					in.close();
					out.close();
					fileItem.delete();
					map.put("pimage", "upload/" + filename);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		try {
			BeanUtils.populate(product, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		product.setPid(CommonUtils.getUUID());
		product.setPflag(0);
		product.setPdate(new Date().toString());
		System.out.println(product);
		adminProductService.addProduct(product);
		response.sendRedirect(request.getContextPath() + "/adminProduct?method=adminProductList");
	}
	
	public void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService productService = new ProductService();
		String pid = request.getParameter("pid");
		Product product = productService.findProductByPid(pid);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	}
	
	public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AdminProductService adminProductService = new AdminProductService();
		Map<String, Object> map = new HashMap<>();
		Product product = new Product();
		

		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> parseRequest = upload.parseRequest(request);
			for (FileItem fileItem : parseRequest) {
				boolean isFormField = fileItem.isFormField();
				if (isFormField) {
					String fieldName = fileItem.getFieldName();
					String fieldValue = fileItem.getString("UTF-8");
					map.put(fieldName, fieldValue);
				} else {
					String filename = fileItem.getName();
					String path = this.getServletContext().getRealPath("upload");
					InputStream in = fileItem.getInputStream();
					OutputStream out = new FileOutputStream(path + "/" + filename);
					IOUtils.copy(in, out);
					in.close();
					out.close();
					fileItem.delete();
					map.put("pimage", "upload/" + filename);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		try {
			BeanUtils.populate(product, map);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		adminProductService.updateProduct(product);
		response.sendRedirect(request.getContextPath() + "/adminProduct?method=adminProductList");
		
	}
}