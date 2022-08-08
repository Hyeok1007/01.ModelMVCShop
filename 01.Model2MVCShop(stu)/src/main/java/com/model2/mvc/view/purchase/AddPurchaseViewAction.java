package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddPurchaseViewAction extends Action {

	public AddPurchaseViewAction() {
		System.out.println("[AddPurchaseAction ����Ʈ ������]");
	}
		
	public String execute(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		
		System.out.println("���� View �׼� ����~!");
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(Integer.parseInt(request.getParameter("prod_no")));
		request.setAttribute("product", product);
		
		System.out.println("���� View �׼� ����~");		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
