package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class GetPurchaseAction extends Action{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("구매 GET 액션 시작!!");
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase(tranNo);		
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("구매 GET 액션 종료"+tranNo);
		
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
