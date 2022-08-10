package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;


public class ProductDAO {

	public ProductDAO() {
	}
	
	public void insertProduct(Product product) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1,product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		int i = pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
	}
	
	public Product findProduct(int prodNo) throws Exception{
		
		Connection con = DBUtil.getConnection();		
		String sql = "SELECT * FROM product WHERE prod_no=?";		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);		
		ResultSet rs = pStmt.executeQuery();
		
		Product product = null;
		while(rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setRegDate(rs.getDate("REG_DATE"));
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		return product;
	}
	
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		System.out.println("productList DAO 시작!~!~!~!~!~!");
		Connection con = DBUtil.getConnection();
		
//		String sql = "SELECT * FROM product ";
		String sql = "SELECT p.prod_no, p.prod_name, p.price, p.reg_date, nvl(tran_status_code, 0) as tcode "
				+ "from transaction t, product p "
				+ " WHERE t.prod_no(+)=p.prod_no ";
		if(search.getSearchCondition() != null && !search.getSearchKeyword().trim().equals("")) {
			if(search.getSearchCondition().equals("0")) {
				sql += " AND p.prod_no=" + search.getSearchKeyword();
			}else if(search.getSearchCondition().equals("1")) {
				sql += " AND UPPER(p.prod_name) LIKE UPPER('%" + search.getSearchKeyword()+"%') ";
			}else if(search.getSearchCondition().equals("2")) {
				sql += " AND p.price="+ search.getSearchKeyword();
			}
		}
		sql += " ORDER BY p.prod_No ";
		
		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount :: " + totalCount);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("totalCount", new Integer(totalCount));
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		System.out.println(search);
		
		ArrayList<Product> list = new ArrayList<Product>();
		
		if(totalCount >0) {
		while(rs.next()) {
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setPrice(rs.getInt("PRICE"));
			product.setRegDate(rs.getDate("reg_date"));
			product.setProTranCode(rs.getString("tcode"));			
			
			list.add(product);
			
			}
		}		

		map.put("list", list);
		
		System.out.println("map().size() : "+map.size()+", list.size() : "+list.size()+"list의 데이터 ");
		for(int i =0 ; i< list.size() ; i++) {
			System.out.println(list.get(i));
		}
		
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("ProductList DAO 종료");
		
		return map;
	}
		
		
		public void updateProduct(Product product) throws Exception {
			
			Connection con = DBUtil.getConnection();
			
			String sql = "UPDATE product SET prod_name=?,prod_detail=?,manufacture_day=?,price=?,image_file=? WHERE prod_no=?";
			
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setString(1, product.getProdName());
			pStmt.setString(2, product.getProdDetail());
			pStmt.setString(3, product.getManuDate());
			pStmt.setInt(4, product.getPrice());
			pStmt.setString(5, product.getFileName());
			pStmt.setInt(6, product.getProdNo());
			
			pStmt.executeUpdate();
			con.close();
	}
		
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
				  "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if(rs.next() ) {
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
		
		private String makeCurrentPageSql(String sql, Search search) {
			sql = "SELECT * "
					+ "FROM( SELECT inner_table. * , ROWNUM AS row_seq "
					+ " FROM ("+sql+" ) inner_table "
							+ " WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) "
									+ "WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
			System.out.println("ProductDAO :: make SQL :: "+sql);
			
			return sql;
		}				
}

