package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;

public class PurchaseServiceImpl implements PurchaseService{

	private PurchaseDAO purchaseDAO;
	private ProductDAO productDAO;

	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
		productDAO = new ProductDAO();
	}
	
	public Purchase addPurchase(Purchase purchase) throws Exception {
		purchaseDAO.insertPurchase(purchase);
		return purchase;
	}
	
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}
	
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception {
		return purchaseDAO.getPurchaseList(search, userId);
	}
	
	public Map<String, Object> getSaleList(Search search) throws Exception {
		return purchaseDAO.getSaleList(search);
	}
	
	public Purchase updatePurchase(Purchase purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);
		return purchaseDAO.findPurchase(purchase.getTranNo());
		
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDAO.updateTranCode(purchase);
	}
}
