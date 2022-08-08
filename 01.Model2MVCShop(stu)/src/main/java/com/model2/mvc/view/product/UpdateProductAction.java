package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class UpdateProductAction extends Action {


	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String prodNo = request.getParameter("prodNo");
		
		System.out.println("업뎃 액션시작");
		System.out.println("값 가져왔나? : "+prodNo);
		
		Product product = new Product();
		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		product.setProdNo(Integer.parseInt(prodNo));
		
		
		System.out.println("잘넘어갔나? : "+product);
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(product);
		
		
		System.out.println("중간");
		
	//	HttpSession session = request.getSession();
	//	String sessionId=((ProductVO)session.getAttribute("prod")).getProdNo();
		
		System.out.println("세션넘어감");
		
//		if(sessionId.equals(prodNo)) {
//			session.setAttribute("prod", productVO);
//		}
		request.setAttribute("productVO", product);
		
		return "forward:/getProduct.do?prodNo="+prodNo;
	}
}
