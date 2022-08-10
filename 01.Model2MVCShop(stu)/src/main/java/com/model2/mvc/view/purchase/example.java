package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.domain.Product;
import com.model2.mvc.service.purchase.domain.Purchase;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {

	public PurchaseDAO() {
		System.out.println("PurchaseDao default constructor");
	}
	
	public Purchase findPurchase(int tranNo) throws Exception {
		System.out.println("PurchaseDao findPurchase(int tranNo) start...");
		Connection con = DBUtil.getConnection();
		String sql = " SELECT * FROM transaction WHERE tran_no=? ";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setInt(1, tranNo);
		ResultSet rs = psmt.executeQuery();
		
		Product productVO = new Product();
		UserVO userVO = new UserVO();
		Purchase purchaseVO = new Purchase();
		while(rs.next()) {
			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			productVO.setProdNo(rs.getInt("PROD_NO"));
			purchaseVO.setPurchaseProd(productVO);
			userVO.setUserId(rs.getString("BUYER_ID"));
			purchaseVO.setBuyer(userVO);
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchaseVO.setOrderDate(rs.getDate("ORDER_DATA"));
			purchaseVO.setDivyDate(rs.getDate("DLVY_DATE")+"");
		}
		DBUtil.close(con, psmt, rs);
		return purchaseVO;
	}
	
	public Map<String, Object> getPurchaseList(Search searchVO, String userID) throws Exception {
		System.out.println("PurchaseDao getPurchaseList(SearchVO searchVO) start...");
		
		Connection con = DBUtil.getConnection();
		System.out.println("userID : " + userID);
		String sql = " SELECT * FROM transaction WHERE buyer_id='"+userID+"' ORDER BY order_data DESC ";
		System.out.println("PurchaseDAO Original sql : " + sql);
		
		int total = getTotalCount(sql);
		System.out.println("PurchaseDAO 전체 레코드 수 : " + total);		

		ArrayList<Purchase> list = new ArrayList<Purchase>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", total);
		
		sql = makeGetCurrentSql(sql, searchVO);
		System.out.println("PurchaseDAO currentPage 가져오는 sql : " + sql);
		PreparedStatement ptmt = con.prepareStatement(sql);
		ResultSet rs = ptmt.executeQuery();

		Product productVO = new Product();
		UserVO userVO = new UserVO();
		if(total > 0) {
			while(rs.next()) {
				Purchase purchaseVO = new Purchase();
				purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
				productVO.setProdNo(rs.getInt("PROD_NO"));
				purchaseVO.setPurchaseProd(productVO);
				userVO.setUserId(rs.getString("BUYER_ID"));
				purchaseVO.setBuyer(userVO);
				purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
				purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
				purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				purchaseVO.setDivyDate(rs.getDate("DLVY_DATE")+"");
				
				list.add(purchaseVO);
				
				//if(!rs.next()) { // 첫번째 list add하고 한번 rs.next()를 했기에 두번째 list 건너뛰고 세번째 list add한다 
				//	break;
				//}
			}
		}
		map.put("list", list);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("PurchaseDAO list : "+list.get(i));
		}
		DBUtil.close(con, ptmt, rs);		
		return map;
	}
	
	public Map<String, Object> getSaleList(Search searchVO) throws Exception {
		System.out.println("PurchaseDao getSaleList(SearchVO searchVO) start...");
		return null;
	}
	
	public void insertPurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseDao insertPurchase(PurchaseVO purchaseVO) start...");
		Connection conn = DBUtil.getConnection();
		String sql = " INSERT INTO transaction VALUES (seq_transaction_tran_no.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, '1', sysdate, ?) ";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		psmt.setString(2, purchaseVO.getBuyer().getUserId());
		psmt.setString(3, purchaseVO.getPaymentOption());
		psmt.setString(4, purchaseVO.getReceiverName());
		psmt.setString(5, purchaseVO.getReceiverPhone());
		psmt.setString(6, purchaseVO.getDivyAddr());
		psmt.setString(7, purchaseVO.getDivyRequest());
		psmt.setString(8, purchaseVO.getDivyDate());
		
		int i = psmt.executeUpdate();
		if(i==1) {
			System.out.println("구매하기 성공");
		}
		DBUtil.close(conn, psmt);
		System.out.println("PurchaseDao insertPurchase(PurchaseVO purchaseVO) end...");
	}
	
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseDao updatePurchase(PurchaseVO purchaseVO) start...");
		Connection con = DBUtil.getConnection();
		
		String sql = " UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=? "
				+ " , demailaddr=?, dlvy_request=?, dlvy_date=? WHERE tran_no=? ";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, purchaseVO.getPaymentOption());
		psmt.setString(2, purchaseVO.getReceiverName());
		psmt.setString(3, purchaseVO.getReceiverPhone());
		psmt.setString(4, purchaseVO.getDivyAddr());
		psmt.setString(5, purchaseVO.getDivyRequest());
		psmt.setString(6, purchaseVO.getDivyDate());
		psmt.setInt(7, purchaseVO.getTranNo());
		int i = psmt.executeUpdate();
		
		DBUtil.close(con, psmt);
		
		if(i==1) {
			System.out.println("수정 성공");
		}else {
			System.out.println("수정 실패");
		}
	}
	
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseDao updateTranCode(PurchaseVO purchaseVO) start...");
		Connection con = DBUtil.getConnection();
		PreparedStatement psmt = null;
		String sql = " UPDATE transaction SET tran_status_code=? where ";
		int i = 0;
		if(purchaseVO.getTranNo() != 0) {
			sql += " tran_no=? ";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, purchaseVO.getTranCode());
			psmt.setInt(2, purchaseVO.getTranNo());
			i = psmt.executeUpdate();
		}else {
			sql += " prod_no=? ";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, purchaseVO.getTranCode());
			psmt.setInt(2, purchaseVO.getPurchaseProd().getProdNo());
			i = psmt.executeUpdate();
		}
		
		System.out.println("sql : " + sql);		
		System.out.println(purchaseVO.getTranCode());
		System.out.println(purchaseVO.getTranNo());
		
		DBUtil.close(con, psmt);
		
		if(i!=1) {
			System.out.println("상태코드 수정 실패");
		}else {
			System.out.println("상태코드 수정 성공");
		}
		System.out.println("PurchaseDao updateTranCode(PurchaseVO purchaseVO) end...");
	}
	
	//totalCount : 총 레코드 수 가져온다
	public int getTotalCount(String originalSql) throws Exception {
		Connection con = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) FROM ("+originalSql+")";
		PreparedStatement psmt = con.prepareStatement(sql);
		ResultSet rs = psmt.executeQuery();
		
		int totalCount = 0;
		while(rs.next()) {
			totalCount = rs.getInt(1);
		}

		DBUtil.close(con, psmt, rs);
		return totalCount;
	}
	
	//currentPage의 레코드만 가져온다
	public String makeGetCurrentSql(String sql, Search search) throws Exception {
		String currentSql = " SELECT * "
							+ "	FROM ( SELECT ROWNUM r, vt1.* "
							+ 		 " FROM ("+sql+") vt1 ) vt2 "
							+ " WHERE r BETWEEN "+((search.getCurruntPage()-1)*search.getPageSize()+1)+" AND "+search.getCurruntPage()*search.getPageSize();
		return currentSql;
	}

}


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {

	public ProductDAO() {
		System.out.println("ProductDAO default constructor");
	}
	
	public int insertProduct(Product productVO) throws Exception {
		System.out.println("ProductDAO insertProduct(ProductVO productVO) start...");
		
		Connection con = DBUtil.getConnection();
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.NEXTVAL,?,?,TO_CHAR(TO_DATE(?),'yyyymmdd'),?,?,sysdate)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, productVO.getProdName());
		pstmt.setString(2, productVO.getProdDetail());
		pstmt.setString(3, productVO.getManuDate());
		pstmt.setInt(4, productVO.getPrice());
		pstmt.setString(5, productVO.getFileName());
		int i = pstmt.executeUpdate();
		
		DBUtil.close(con, pstmt);
		System.out.println("등록한 상품 정보 : " + productVO.toString());
		System.out.println("ProductDAO insertProduct(ProductVO productVO) end...");
		return i;		
	}
	
	public Product findProduct(int prodNo) throws Exception {
		System.out.println("ProductDAO findProduct(int prodNo) start...");
		System.out.println("찾을 상품의 고유번호 : " + prodNo);
		
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * from product WHERE prod_no=? ";
		PreparedStatement pst = con.prepareStatement(sql);
		pst.setInt(1, prodNo);
		ResultSet rs = pst.executeQuery();

		Product productVO = new Product();
		while(rs.next()) {
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		DBUtil.close(con, pst, rs);		
		System.out.println("찾은 상품의 정보 : " + productVO.toString());		
		System.out.println("ProductDAO findProduct(int prodNo) end...");
		return productVO;
	}
	
	public Map<String, Object> getProductList(Search searchVO) throws Exception {
		System.out.println("ProductDAO getProductList(SearchVO searchVO) start...");
		Connection con = DBUtil.getConnection();
		
		//original sql
		String sql = " select p.prod_no, p.prod_name, p.price, p.reg_date, nvl(tran_status_code, 0) as tcode "
				+ " from transaction t, product p "
				+ " where t.prod_no(+)=p.prod_no ";
		if(searchVO.getSearchCondition() != null && !searchVO.getSearchKeyword().trim().equals("")) {
			if(searchVO.getSearchCondition().equals("0")) {
				sql += " AND p.PROD_NO=" + searchVO.getSearchKeyword();
			}else if(searchVO.getSearchCondition().equals("1")) {
				sql += " AND UPPER(p.PROD_NAME) LIKE UPPER('%" + searchVO.getSearchKeyword() + "%') ";
			}else if(searchVO.getSearchCondition().equals("2")) {
				sql += " AND p.PRICE=" + searchVO.getSearchKeyword();
			}
		}
		sql += " ORDER BY p.PROD_NO ";
		System.out.println("ProductDAO Original sql : " + sql);
		
		int total = getTotalCount(sql);
		System.out.println("ProductDAO 전체 레코드 수 : " + total);		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(total));
		
		sql = makeGetCurrentSql(sql, searchVO);
		System.out.println("ProductDAO currentPage 가져오는 sql : " + sql);
		
		PreparedStatement psmt = con.prepareStatement(sql);
		ResultSet rs = psmt.executeQuery();
		System.out.println("현재페이지 : "+searchVO.getCurruntPage()+", 화면에 나오는 레코드 수 : "+searchVO.getPageSize());

		ArrayList<Product> list = new ArrayList<Product>();
		if(total > 0) {
			while(rs.next()) {
				Product productVO = new Product();
				productVO.setProdNo(rs.getInt("prod_no"));
				productVO.setProdName(rs.getString("PROD_NAME"));
				productVO.setPrice(rs.getInt("PRICE"));
				productVO.setRegDate(rs.getDate("reg_date"));
				productVO.setProTranCode(rs.getString("tcode"));
				
				list.add(productVO);
			}
		}//end of if(total > 0)
		map.put("list", list);

		System.out.println("map().size() : "+ map.size()+", list.size() : "+list.size()+", list안의 데이터 : ");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		DBUtil.close(con, psmt, rs);
		System.out.println("ProductDAO getProductList(SearchVO searchVO) end...");
		return map;
	}
	
	public int updateProduct(Product productVO) throws Exception {
		System.out.println("ProductDAO updateProduct(ProductVO productVO) start...");
		System.out.println("수정할 상품 정보 : "+productVO.toString());
		
		Connection con = DBUtil.getConnection();
		String sql = "UPDATE PRODUCT set PROD_DETAIL=?,MANUFACTURE_DAY=TO_CHAR(TO_DATE(?),'YYYYMMDD'),PRICE=? where PROD_NO=?";
		PreparedStatement pstmt;
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, productVO.getProdDetail());
		pstmt.setString(2, productVO.getManuDate());
		pstmt.setInt(3, productVO.getPrice());
		pstmt.setInt(4, productVO.getProdNo());
		int i = pstmt.executeUpdate();
		
		DBUtil.close(con, pstmt);		
		System.out.println("ProductDAO updateProduct(ProductVO productVO) end...");
		return i;
	}
	
	//totalCount : 총 레코드 수 가져온다
	public int getTotalCount(String originalSql) throws Exception {
		Connection con = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) FROM ("+originalSql+")";
		PreparedStatement psmt = con.prepareStatement(sql);
		ResultSet rs = psmt.executeQuery();
		
		int totalCount = 0;		
		while(rs.next()) {
			totalCount = rs.getInt(1);
		}

		DBUtil.close(con, psmt, rs);
		return totalCount;
	}
	
	//currentPage의 레코드만 가져온다
	public String makeGetCurrentSql(String sql, Search search) throws Exception {
		String currentSql = " SELECT * "
							+ "	FROM ( SELECT ROWNUM r, vt1.* "
							+ 		 " FROM ("+sql+") vt1 ) vt2 "
							+ " WHERE r BETWEEN "+((search.getCurruntPage()-1)*search.getPageSize()+1)+" AND "+search.getCurruntPage()*search.getPageSize();
		return currentSql;
	}

}




