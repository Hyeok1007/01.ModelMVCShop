package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class AddProductAction extends Action{

	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		if(FileUpload.isMultipartContent(request)) {
			
			String temDir = "C:\\Users\\bitcamp\\git\\01.ModelMVCShop\\01.Model2MVCShop(stu)\\src\\main\\webapp\\images\\uploadFiles";
//			C:\Users\bitcamp\git\01.ModelMVCShop\01.Model2MVCShop(stu)\src\main\webapp\images
				
		DiskFileUpload fileUpload = new DiskFileUpload();
		fileUpload.setRepositoryPath(temDir);
		fileUpload.setSizeMax(1024 * 1024 * 10);
		// �ִ� 1�ް����� ���ε� ���� (1024 * 1024 * 100) <- 100MB
		fileUpload.setSizeThreshold(1024 * 100); // �ѹ��� 100k ������ �޸𸮿� ����
		
		if(request.getContentLength() < fileUpload.getSizeMax()) {
			Product product = new Product();
			
			StringTokenizer token = null;
			
			// parseRequest()�� FileItem�� �����ϰ� �ִ� ListŸ���� �����Ѵ�.
			List fileItemList = fileUpload.parseRequest(request);
			int Size = fileItemList.size();
			for(int i = 0 ; i < Size ; i++) {
				FileItem fileItem = (FileItem) fileItemList.get(i);
				//isFormField()�� ���ؼ� ������������ �Ķ�������� �����Ѵ�. �Ķ���Ͷ�� true
				if(fileItem.isFormField()) {
					if(fileItem.getFieldName().equals("manuDate")) {
						token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
						String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
						product.setManuDate(manuDate);
					}
					else if(fileItem.getFieldName().equals("prodName")) {
						product.setProdName(fileItem.getString("euc-kr"));
					}else if(fileItem.getFieldName().equals("prodDetail")) {
						product.setProdDetail(fileItem.getString("euc-kr"));
					}else if(fileItem.getFieldName().equals("price")) {
						product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
					}
					}else { //���� �����̸�..
						
						if(fileItem.getSize() > 0) {
							int idx = fileItem.getName().lastIndexOf("\\");
							// getName()�� ��θ� �� �������� ������ lastIndexOf�� �߶󳽴�
							if(idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx+1);
							product.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}
						}else {
							product.setFileName("../../images/empty,GIF");
						}
					} //else				 
			}//for
			
			ProductServiceImpl service = new ProductServiceImpl();
			service.addProduct(product);
			
			request.setAttribute("product", product);
		} else {
			// ���ε��ϴ� ������ setSizeMax���� ū ���
			int overSize = (request.getContentLength() / 1000000);
			System.out.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮�� "+overSize+"MB�Դϴ�');");
			System.out.println("history.back();</script>");
		}
	} else {
		System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�..");
	}		
//		
//		Product product = new Product();
//		System.out.println("�׼ǽ���");
//		
//		product.setProdName(request.getParameter("prodName"));
//		product.setProdDetail(request.getParameter("prodDetail"));
//		product.setManuDate(request.getParameter("manuDate"));
//		product.setPrice(Integer.parseInt(request.getParameter("price")));
//		product.setFileName(request.getParameter("fileName"));
//								
//		ProductService service = new ProductServiceImpl();
//		service.addProduct(product);
//		request.setAttribute("product", product);
//		
//		System.out.println("VOȮ��1 : "+product);
//					
		
		return "forward:/product/addProduct.jsp";
//		return "forward:/product/getProduct.jsp";
	}
}
