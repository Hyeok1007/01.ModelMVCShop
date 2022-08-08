package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action {

	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		
		
		System.out.println("구매 업뎃 액션 시작~!@");
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
//		String paymentOption = request.getParameter("paymentOption");
//		String receiverName = request.getParameter("receiverName");
//		String receiverPhone = request.getParameter("receiverPhone");
//		String receiverAddr = request.getParameter("receiverAddr");
//		String receiverRequest = request.getParameter("receiverRequest");
//		String divyDate = request.getParameter("divyDate"); 
				
		
		Purchase purchase = new Purchase();
		
		purchase.setTranNo(tranNo);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		System.out.println("구매 업뎃 액션 잘 받았나? "+ purchase.toString());
		
		PurchaseService service = new PurchaseServiceImpl();
		purchase = service.updatePurchase(purchase);
		
		request.setAttribute("purchase", purchase);
						
		return "forward:/purchase/updatePurchase.jsp";
	}
	

}
