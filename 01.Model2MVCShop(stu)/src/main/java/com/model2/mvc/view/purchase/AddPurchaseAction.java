package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Purchase purchase = new Purchase();
		System.out.println("구매액션 시작========================");
		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		
		///상품번호 상품 가져오기
		ProductService pService = new ProductServiceImpl();
		Product product = pService.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		purchase.setPurchaseProd(product);
		
		///유저 연결
		UserService uService = new UserServiceImpl();
		User user = uService.getUser(request.getParameter("buyerId"));
		purchase.setBuyer(user);
		
		PurchaseService service = new PurchaseServiceImpl();
		purchase = service.addPurchase(purchase);
		System.out.println("구매 중 여기까지 왔나"+purchase.toString());
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("VO확인1 : "+purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
