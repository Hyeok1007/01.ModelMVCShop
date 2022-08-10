package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
				
		System.out.println("[ListProduct 액션 시작~]");
		String menu = request.getParameter("menu");
		
		int currentPage=1;
		
		String searchKeyword = CommonUtil.null2str(request.getParameter("searchKeyword"));
		String searchCondition = CommonUtil.null2str(request.getParameter("searchCondition"));
		
		System.out.println("리스트 중간확인 "+request.getParameter("currentPage"));
		
		if(request.getParameter("currentPage") != null && !request.getParameter("currentPage").equals("")) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
			
		Search search = new Search();
		search.setCurrentPage(currentPage);
		
		if(!searchCondition.trim().equals("1")  && !CommonUtil.parsingCheck(searchKeyword) ) {
			searchKeyword = "";
		}
		
		search.setSearchCondition(searchCondition);
		search.setSearchKeyword(searchKeyword);
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		ProductService productService = new ProductServiceImpl();
		Map<String, Object>map=productService.getProductList(search);
		
		Page resultPage = new Page(currentPage,((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction :: "+resultPage);
	
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		request.setAttribute("menu", menu);
		
		
		System.out.println("[ListProduct 액션 끝~!]");
		return "forward:/product/listProduct.jsp";
	}

}
