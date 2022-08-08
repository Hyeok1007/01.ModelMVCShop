package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class GetProductAction extends Action {

	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String prodNo = request.getParameter("prodNo");
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(Integer.parseInt(prodNo));
		
		request.setAttribute("product", product);
		
		System.out.println("여기선 받나"+prodNo);
				
		return "forward:/product/getProduct.jsp";
	}
	
}
