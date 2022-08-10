package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.User;

public class ListPurchaseAction extends Action {

	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.println("구매 리스트 액션 시작!@~@");
		Search search = new Search();
		String userId = ((User)request.getSession(true).getAttribute("user")).getUserId();
		
		int currentPage=1;
		
		if(request.getParameter("page") != null) {
			currentPage=Integer.parseInt(request.getParameter("page"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		Map<String, Object>map=purchaseService.getPurchaseList(search, userId);
		
		Page resultPage = new Page(currentPage,(int)map.get("totalCount"), pageUnit, pageSize);
		System.out.println("ListPurchaseAction :: "+resultPage);
	
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("menu", request.getParameter("menu"));
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
