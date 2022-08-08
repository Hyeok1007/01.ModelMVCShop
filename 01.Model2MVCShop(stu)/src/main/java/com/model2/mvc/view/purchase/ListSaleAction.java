package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;

public class ListSaleAction extends Action {

	public ListSaleAction() {
		System.out.println("ListSaleAction Default 생성자");
	}
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("ListSaleAction 시작!");
		System.out.println("ListSaleAction 종료!");
		
		return null;
	}

}
