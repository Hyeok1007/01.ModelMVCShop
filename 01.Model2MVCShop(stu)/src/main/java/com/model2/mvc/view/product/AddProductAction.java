package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class AddProductAction extends Action{

	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Product product = new Product();
		System.out.println("액션시작");
		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
								
		ProductService service = new ProductServiceImpl();
		service.addProduct(product);
		request.setAttribute("product", product);
		
		System.out.println("VO확인1 : "+product);
					
		
		return "forward:/product/addProduct.jsp";
	}
}
