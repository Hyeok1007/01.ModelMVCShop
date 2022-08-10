//package com.model2.mvc.view.product;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.model2.mvc.common.Page;
//import com.model2.mvc.common.Search;
//import com.model2.mvc.common.util.CommonUtil;
//import com.model2.mvc.framework.Action;
//import com.model2.mvc.service.product.ProductService;
//import com.model2.mvc.service.product.impl.ProductServiceImpl;
//
//public class ListProductAction extends Action {
//
//	public ListProductAction() {
//		System.out.println("[ListProductAction default Constructor()]");
//	}
//
//	@Override
//	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("[ListProductAction execute() start...]");
//		//admin �����϶� �ǸŻ�ǰ������ ��ǰ�˻��� �����ؼ� ��ǰ������ �������� ��ȸ���� ����
//		String menu = request.getParameter("menu");
//
//		// ��ǰ�˻� Ŭ�������� currentPage�� null�̴�
//		int currentPage = 1;
//
//		// �ǸŻ�ǰ���� Ŭ���� searchKeyword, searchCondition �� �� null
//		String searchKeyword = CommonUtil.null2str(request.getParameter("searchKeyword"));
//		String searchCondition = CommonUtil.null2str(request.getParameter("searchCondition"));
//		
//		//��ǰ�˻� Ŭ���� null, �˻���ư Ŭ���� nullString
//		System.out.println("aaaa : " + request.getParameter("currentPage"));
//		if (request.getParameter("currentPage") != null && !request.getParameter("currentPage").equals("")) {
//			currentPage = Integer.parseInt(request.getParameter("currentPage"));
//		}
//
//		// �˻����� ������ ��, �����, Ű����, pageUnit reqeust�� ������ ������ searchVO�� �ִ´�
//		Search searchVO = new Search();
//		searchVO.setCurruntPage(currentPage);
//		
//		//��ǰ��� ��ǰ���ݿ��� searchKeyword�� �����϶� nullString���� ��ȯ
//		if(!searchCondition.trim().equals("1") && !CommonUtil.parsingCheck(searchKeyword)) {
//			searchKeyword = "";
//		}
//		searchVO.setSearchCondition(searchCondition);
//		searchVO.setSearchKeyword(searchKeyword);
//
//		//page�� ���� ������� ���� ����� web.xml���� ������ �ִ´�
//		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
//		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
//		searchVO.setPageSize(pageSize);
//		System.out.println("searchVO.toString() : " + searchVO.toString());
//
//		// �˻������� �־ ���� �������� list�� �����´�
//		ProductService service = new ProductServiceImpl();
//		Map<String, Object> map = service.getProductList(searchVO);
//		
//		//rssultPage�� pagingó��
//		Page resultPage = new Page(currentPage, ((Integer)map.get("count")).intValue() , pageUnit, pageSize);
//		System.out.println("resultPage.toString() : " + resultPage.toString());
//
//		// �˻������� �˻��ؼ� ���� list�� ��´�
//		request.setAttribute("resultPage", resultPage);
//		request.setAttribute("searchVO", searchVO);
//		request.setAttribute("list", map.get("list"));
//		request.setAttribute("menu", menu);
//
//		System.out.println("[ListProductAction execute() end...]");
//		return "forward:/product/listProduct.jsp";
//	}
//
//}
