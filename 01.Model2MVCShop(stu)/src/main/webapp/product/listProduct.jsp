<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

function fncGetProductList(currentPage){
	document.getElementById("currentPage").value = currentPage;
	document.detailForm.submit();
}
<%-- function fncGetProductList() {
	document.detailForm.searchCondition.value = document.detailFrom.searchCondition.value;
	document.forms[0].elements[2].value = document.forms[0].elements[2].value;
	document.detailFrom.submit();
} --%>

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=${param.menu }" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
<%-- 				<%if(menu.equals("search")){ %>				
					상품 목록조회
				<%}else{ %>
					상품관리											
			<% }%>
			--%>
			<c:if test="${param.menu == 'search'}">
				상품 목록조회
			</c:if>
			<c:if test="${param.menu == 'manage'}">
				상품관리
			</c:if>			
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>			
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${ !empty search.searchCondition && search.searchCondition == 0 ? "selected" : ""}>상품번호</option>
				<option value="1" ${ !empty search.searchCondition && search.searchCondition == 1 ? "selected" : ""}>상품명</option>
				<option value="2" ${ !empty search.searchCondition && search.searchCondition == 2 ? "selected" : ""}>상품가격</option>
			</select>
			<input type="text" name="searchKeyword" value="${ !empty search.searchKeyword ? search.searchKeyword : ""}"  
							class="ct_input_g" style="width:200px; height:20px" />
		</td>
		
		<td align="right" width="70">	
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
		전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage } 페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="size" value="${fn:length(list) }" />
	
	<c:if test="${!empty sessionScope.user && sessionScope.user.role == 'admin' }">
		<c:forEach var="i" begin="0" end="2" step="1">
			<tr class="ct_list_pop">
		 	<td align="center">${size-i}</td> 
			<td></td>
		<c:if test="${param.menu == 'search'}">
			<td align="left"><a href="/getProduct.do?prodNo=${list[size-1-i].prodNo }">${list[size-1-i].prodName }</a></td>
		</c:if>		
		<c:if test="${param.menu == 'manage'}">
			<td align="left"><a href="/updateProductView.do?prodNo=${list[size-1-i].prodNo }">${list[size-1-i].prodName }</a></td>
		</c:if>
		<td></td>
		<td align="left">${list[size-1-i].price }</td>
		<td></td>
		<td align="left">${list[size-1-i].regDate }</td>
		<td></td>
		<td align="left">		 	
		  	<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '0' }"> 
		<%--   	<c:if test="${ list[size-1-i].proTranCode == '0' }"> --%> 
		 <%-- 	<c:if test="${product.proTranCode == '0' }"> --%> 
				판매중
		 	</c:if> 
		 	
		  	<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '1'}"> 
		<%--  	<c:if test="${ list[size-1-i].proTranCode == '1'}"> --%>
		<%--  	<c:if test="${product.proTranCode == '1'}"> --%>  
				구매완료
				<c:if test="${param.menu == 'manage' }">
					<a href="/updateTranCodeByProd.do?prodNo=${list[size-1-i].prodNo}&currentPage=${resultPage.currentPage}&tanscode=2&menu=${menu}">배송하기</a>
		<%-- 			<a href="/updateTranCodeByProd.do?prodNo=${product.prodNo}&tranCode=2">배송하기</a> --%>
				</c:if>
			</c:if>
		 	
		 	<c:if test="${ fn:trim(list[size-1-i].proTranCode)== '2' }">  
		<%--  	<c:if test="${list[size-1-i].proTranCode == '2' }"> --%>
		<%--  	<c:if test="${product.proTranCode == '2' }"> --%> 
				배송중
			</c:if>		 	 
		  	<c:if test="${ fn:trim(list[size-1-i].proTranCode)== '3' }">
		<%--  	<c:if test="${list[size-1-i].proTranCode == '3' }"> --%>
		<%--  	<c:if test="${product.proTranCode == '3' }"> --%>  
				배송완료
			</c:if>			
		</td>
	</tr>
	<tr>
	<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	
	</c:forEach>
	</c:if>
	
	<c:if test="${sessionScope.user.role == 'user' || empty sessionScope.user }">
		<c:forEach var="i" begin="0" end="${size-1 }" step="1">
			<tr class="ct_list_pop">
				<td align="center">${size-i }</td>
				<td></td>
					<td align="left">
						<c:if test="${list[size-1-i].proTranCode == '0' }">
							<a href="/getProduct.do?prodNo=${list[size-1-i].prodNo}&menu=${param.menu}">${list[size-1-i].prodName}</a>
						</c:if>
						<c:if test="${list[size-1-i].proTranCode != '0' }">
							${list[size-1-i].prodName }
						</c:if>
					</td>
				<td></td>
				<td align="left">${list[size-1-i].price }</td>
				<td></td>
				<td align="left">${list[size-1-i].regDate }</td>
				<td></td>
				<td align="left">
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '0' }">
						판매중
					</c:if>
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) != '0' }">
						재고없음
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>
			</c:forEach>
		</c:if>

</table>	


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
	
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>

	<%-- 		<jsp:include page="../common/pageNavigator.jsp"/> --%>

	 	<c:if test="${resultPage.currentPage <= resultPage.pageUnit }">
				◀ 이전
		</c:if>
		
		<c:if test="${resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetProductList('${ resultPage.currentPage-1}')">◀ 이전</a>
		</c:if>
		
		<c:forEach var="i" begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage }" step="1">
			<a href="javascript:fncGetProductList('${ i }');">${ i }</a>
		</c:forEach>
		
		<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
				이후 ▶
		</c:if>
		<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
				<a href="javascript:fncGetProductList('${resultPage.endUnitPage+1}')">이후 ▶</a>
		</c:if>
		

    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
    