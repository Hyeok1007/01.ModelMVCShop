package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	public UpdateTranCodeByProdAction() {
		System.out.println("[UpdateTranCodeByProdAction default 생성자.....");
	}

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String menu = request.getParameter("menu");
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String tanscode = request.getParameter("tanscode");
		System.out.println("업뎃 트랜 코드 바이 프로덕트 값 잘 왔나? prodNo : "+prodNo+ ", tanscode : "+tanscode);
		
		Purchase purchase = new Purchase();
		Product product = new Product();
		product.setProdNo(prodNo);
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tanscode);
		System.out.println("purchase : "+purchase);		
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchase);
		
		Search search = new Search();
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) { 
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setCurrentPage(currentPage);
		search.setPageSize(pageSize);
		
		ProductServiceImpl productService = new ProductServiceImpl();
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("menu", menu);
		
		System.out.println("업뎃 트랜 코드 바이 프로덕트 액션 종료");
		return "forward:/product/listProduct.jsp";
	}
}
