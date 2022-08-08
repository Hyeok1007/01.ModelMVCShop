package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {

	///Constructor
	public UpdateTranCodeAction() {
		System.out.println("UpdateTranCode Default 생성자");
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
		search.setCurrentPage(currentPage);
		search.setPageSize(pageSize);
		
		String userId = ((User)request.getSession(true).getAttribute("user") ).getUserId();
		Map<String, Object> map = service.getPurchaseList(search, userId);
		
		Page resultPage = new Page(currentPage, (int)map.get("count"), pageUnit, pageSize);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("search", search);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("menu", menu);
		
		System.out.println("[업데이트 TranCode 종료");
		return "forward:/purchase/listPurchase.jsp";
		
	}

}
