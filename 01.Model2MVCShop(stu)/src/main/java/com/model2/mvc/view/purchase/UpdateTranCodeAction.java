package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {

	///Constructor
	public UpdateTranCodeAction() {
		System.out.println("UpdateTranCode Default »ý¼ºÀÚ");
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String menu = request.getParameter("menu");
		String tranCode = request.getParameter("tranCode");
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase = new Purchase();
		purchase.setTranCode(tranCode);
		purchase.setTranNo(tranNo);
		
		PurchaseService service = new PurchaseServiceImpl();
		service.updateTranCode(purchase);
		
		Search search = new Search();
		
		int currentPage = 1;
		if(request.getParameter("currentPage")!= null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		
	}

}
