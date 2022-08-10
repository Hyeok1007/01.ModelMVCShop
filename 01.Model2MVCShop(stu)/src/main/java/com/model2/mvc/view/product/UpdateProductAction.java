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


public class UpdateProductAction extends Action {


	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String prodNo = request.getParameter("prodNo");
		
		
		
		if(FileUpload.isMultipartContent(request)) {
			String temDir = "C:\\Users\\bitcamp\\git\\01.ModelMVCShop\\01.Model2MVCShop(stu)\\src\\main\\webapp\\images\\uploadFiles";
//			String temDir = "C:\\Users\\bitcamp\\git\\01.Model2MVCShop(stu)\\src\\main\\webapp\\images\\uploadFiles\\";
//		 	String temDir = C:\Users\bitcamp\git\01.ModelMVCShop\01.Model2MVCShop(stu)\src\main\webapp\images\\uploadFiles
			//String temDir2 = "/uploadFiles/";
				
		DiskFileUpload fileUpload = new DiskFileUpload();
		fileUpload.setRepositoryPath(temDir);
		fileUpload.setSizeMax(1024*1024*10);
		fileUpload.setSizeThreshold(1024*100);
		
		if(request.getContentLength() < fileUpload.getSizeMax()) {
			Product product = new Product();
			StringTokenizer token = null;
			
			ProductServiceImpl service = new ProductServiceImpl();
			
			List fileItemList = fileUpload.parseRequest(request);
			int Size = fileItemList.size();
			for (int i = 0 ; i < Size ; i++) {
				FileItem fileItem = (FileItem) fileItemList.get(i);
				if(fileItem.isFormField()) {
					if(fileItem.getFieldName().equals("manuDate")) {
						token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
						String manuDate = token.nextToken();
						while(token.hasMoreTokens()) 
							manuDate+=token.nextToken();
						product.setManuDate(manuDate);
						} else if(fileItem.getFieldName().equals("prodName")) { 
							product.setProdName(fileItem.getString("euc-kr"));
						}else if(fileItem.getFieldName().equals("prodDetail")) {
							product.setProdDetail(fileItem.getString("euc-kr"));
						}else if(fileItem.getFieldName().equals("price")) {
							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
						} else if(fileItem.getFieldName().equals("prodNo")) {
//							prodNo = Integer.parseInt(fileItem.getString("euc-kr"));							
//							product.setProdNo(prodNo);
							
							prodNo = fileItem.getString("euc-kr");							
							product.setProdNo(Integer.parseInt(prodNo));
						}
					} else { //파일 형식이면...
						if(fileItem.getSize() > 0) { //파일을 저장하는 if
							int idx = fileItem.getName().lastIndexOf("\\");
							if(idx == -1) {
								idx=fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx+1);
							product.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							} catch(IOException e) {
								System.out.println(e);
							}
						}else {
								product.setFileName("../../images/empty.GIF");
							}
						} //else
					} //for
				service.updateProduct(product);
				} else {
					int overSize = (request.getContentLength() / 1000000);
					System.out.println("<script>alert('파일의 크기는 1MB까지 입니다. 올리신 파일 용량은" +overSize + "MB입니다')");
					System.out.println("history.back();</script>");
				}
			} else {
				System.out.println("인코딩 타입이 multipart/form-data가 아닙니다..");
			}
		return "redirect:/getProduct.do?prodNo=" +prodNo+"&menu = ok";
				
/*		String prodNo = request.getParameter("prodNo");
		
		System.out.println("업뎃 액션시작");
		System.out.println("값 가져왔나? : "+prodNo);
		
		Product product = new Product();
		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		product.setProdNo(Integer.parseInt(prodNo));
		
		
		System.out.println("잘넘어갔나? : "+product);
		
		ProductService service = new ProductServiceImpl();
		service.updateProduct(product);
		
		
		System.out.println("중간");
		
	//	HttpSession session = request.getSession();
	//	String sessionId=((ProductVO)session.getAttribute("prod")).getProdNo();
		
		System.out.println("세션넘어감");
		
//		if(sessionId.equals(prodNo)) {
//			session.setAttribute("prod", productVO);
//		}
		request.setAttribute("productVO", product);
		
		return "forward:/getProduct.do?prodNo="+prodNo;
		
		*/
	}
}
