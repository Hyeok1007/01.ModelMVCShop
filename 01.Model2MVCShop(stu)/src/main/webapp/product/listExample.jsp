<%@page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

function fncGetList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();
}
function fncGetProductList() {
	document.detailForm.searchCondition.value = document.detailForm.searchCondition.value;
	//document.detailForm.searchKeyword.value = document.detailForm.searchKeyword.value;
	document.forms[0].elements[2].value = document.forms[0].elements[2].value;
   	document.detailForm.submit();
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?" method="post">
<!-- hidden name : menu, currentPage -->
<input type="hidden" name="menu" value="${ menu }">
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01"> ${ sessionScope.user.role } ${ menu } 
						<c:if test="${ menu == 'manage' }">
							상품관리
						</c:if>
						<c:if test="${ menu != 'manage' }">
							상품 목록조회
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
				<option value="0" ${ (searchVO.searchCondition == '0')?"selected":"" } >상품번호</option>
				<option value="1" ${ (searchVO.searchCondition == '1')?"selected":"" } >상품명</option>
				<option value="2" ${ (searchVO.searchCondition == '2')?"selected":"" } >상품가격</option>
			</select>
			<input type="text" name="searchKeyword" value="${ searchVO.searchKeyword }" class="ct_input_g" style="width:200px; height:19px" />
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
		<td colspan="11" >전체 ${ resultPage.totalCount } 건수, 현재 ${ resultPage.currentPage } 페이지</td>
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

	<c:set var="size" value="${ fn:length(list) }" />

	<c:if test="${ !empty sessionScope.user && sessionScope.user.role == 'admin' }">
		<c:forEach var="i" begin="0" end="2" step="1">
			<tr class="ct_list_pop">
				<td align="center">${ size-i }</td>
				<td></td>
					<td align="left">
					<c:if test="${ menu == 'manage' }">
						<!-- 판매코드가 0이 아니면 상품수정 불가 -->
						<a href="/updateProductView.do?prodNo=${ list[size-1-i].prodNo }">${ list[size-1-i].prodName }</a>
					</c:if>
					<c:if test="${ menu != 'manage' }">
						<a href="/getProduct.do?prodNo=${ list[size-1-i].prodNo }">${ list[size-1-i].prodName }</a>
					</c:if>
					</td>				
				<td></td>
				<td align="left">${ list[size-1-i].price }</td>
				<td></td>
				<td align="left">${ list[size-1-i].regDate }</td>
				<td></td>
				<td align="left">
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '0' }">
						판매중
					</c:if>
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '1' }">
						구매완료
						<c:if test="${ menu == 'manage' }">
							-<a href="/updateTranCodeByProd.do?prodNo=${ list[size-1-i].prodNo }&currentPage=${ resultPage.currentPage }&tanscode=2&menu=${ menu }">배송하기</a>
						</c:if>
					</c:if>
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '2' }">
						배송중
					</c:if>
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '3' }">
						배송완료
					</c:if>									
				</td>	
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>
		</c:forEach>
	</c:if>
	<!-- 회원, 비회원 -->
	<c:if test="${ sessionScope.user.role == 'user' || empty sessionScope.user }">
		<c:forEach var="i" begin="0" end="${ size-1 }" step="1">
			<tr class="ct_list_pop">
				<td align="center">${ size-i }</td>
				<td></td>
					<td align="left">
						<c:if test="${ list[size-1-i].proTranCode == '0' }">
							<a href="/getProduct.do?prodNo=${ list[size-1-i].prodNo }&menu=${ menu }">${ list[size-1-i].prodName }</a>
						</c:if>
						<c:if test="${ list[size-1-i].proTranCode != '0' }">
							${ list[size-1-i].prodName }
						</c:if>
					</td>
				
				<td></td>
				<td align="left">${ list[size-1-i].price }</td>
				<td></td>
				<td align="left">${ list[size-1-i].regDate }</td>
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
			<input type="hidden" name="currentPage" id="currentPage" value=""/>
			<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>

<%@page import="com.model2.mvc.service.product.domain.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.service.user.vo.UserVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.model2.mvc.common.Search"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>

<%
System.out.println("여기는 listProduct.jsp");

//상품정보 : list, 검색정보 : searchVO, 페이지정보 : resultPage, 접속한사람정보 : user, 상품관리 or 상품검색 정보 : menu
List<Product> list = (ArrayList<Product>)request.getAttribute("list");
Search searchVO = (Search)request.getAttribute("searchVO");	
Page resultPage = (Page)request.getAttribute("resultPage");
UserVO userVO = (UserVO)session.getAttribute("user");
String menu = (String)request.getAttribute("menu");

for(int i=0; i<list.size(); i++){
	System.out.println("list : " + list.get(i));
}
System.out.println("searchVO : " + searchVO);
System.out.println("resultPage : " + resultPage);
System.out.println("userVO : " + userVO);
System.out.println("menu : " + menu);
%>

<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

function fncGetProductList(){
	document.detailForm.submit();
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=<%=menu %>" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						<% if( menu.equals("manage") ) { %>
						상품 관리
						<% } else { %>
						상품 목록조회
						<% } %>
					
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
				<option value="0">상품번호</option>
				<option value="1">상품명</option>
				<option value="2">상품가격</option>
			</select>
			<input type="text" name="searchKeyword" class="ct_input_g" style="width:200px; height:19px" />
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
		<td colspan="11" >전체 <%=resultPage.getTotalCount() %> 건수, 현재 <%=resultPage.getCurrentPage() %> 페이지</td>
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
	
	<% if( userVO != null && userVO.getRole().equals("admin") ) {%><%-- admin --%>
		<% for(int i=list.size()-1; i>=0; i--){ %>
			<tr class="ct_list_pop">
				<td align="center"><%=i+1 %></td>
				<td></td>
					<td align="left">
					<% if( menu.equals("manage") ) { %>
						<a href="/updateProductView.do?prodNo=<%=list.get(i).getProdNo() %>"><%=list.get(i).getProdName() %></a>
					<% }else{ %>
						<a href="/getProduct.do?prodNo=<%=list.get(i).getProdNo() %>"><%=list.get(i).getProdName() %></a>
					<% } %>
					</td>				
				<td></td>
				<td align="left"><%=list.get(i).getPrice() %></td>
				<td></td>
				<td align="left"><%=list.get(i).getRegDate() %></td>
				<td></td>
				<td align="left">
					
					<% if( list.get(i).getProTranCode().trim().equals("0") ){ %>
						판매중
					<% }else if( list.get(i).getProTranCode().trim().equals("1") ){ %>
						구매완료
						<% if( menu.equals("manage") ) { %>
						-<a href="/updateTranCodeByProd.do?prodNo=<%=list.get(i).getProdNo() %>&tanscode=2">배송하기</a>
						<% } %>
					<% }else if( list.get(i).getProTranCode().trim().equals("2") ){ %>
						배송중
					<% }else{ %>
						배송완료
					<% } %>
									
				</td>	
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>		
		<% } %>
	<% }else { %><%-- user, 비회원 --%>
		<% for(int i=list.size()-1; i>=0; i--){ %>
			<tr class="ct_list_pop">
				<td align="center"><%=i+1 %></td>
				<td></td>
					<td align="left">
						<% if( list.get(i).getProTranCode().trim().equals("0") ){ %>
							<a href="/getProduct.do?prodNo=<%=list.get(i).getProdNo() %>"><%=list.get(i).getProdName() %></a>
						<% }else{ %>
							<%=list.get(i).getProdName() %>
						<% } %>
					</td>
				
				<td></td>
				<td align="left"><%=list.get(i).getPrice() %></td>
				<td></td>
				<td align="left"><%=list.get(i).getRegDate() %></td>
				<td></td>
				<td align="left">
					<% if( list.get(i).getProTranCode().trim().equals("0") ){ %>
						판매중
					<% }else { %>
						재고없음
					<% } %>
				</td>	
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>
		<% } %>
	<% } %>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">		
		
			<% if( resultPage.getBeginUnitPage() != 1 ) { %>
				<a href="/listProduct.do?page=<%=resultPage.getBeginUnitPage()-1 %>&menu=<%=menu %>">이전&nbsp;</a>
			<% } %>
			
			
			<% for(int i=0; i<resultPage.getPageUnit(); i++) { %>
				<% if(i+resultPage.getBeginUnitPage() <= resultPage.getEndUnitPage()) { %>
					<a href="/listProduct.do?page=<%=i+resultPage.getBeginUnitPage() %>&menu=<%=menu %>&searchCondition=<%=searchVO.getSearchCondition() %>&searchKeyword=<%=searchVO.getSearchKeyword() %>"><%=i+resultPage.getBeginUnitPage() %></a>
				<% } %>
			<% } %>
			
			
			<% if( resultPage.getEndUnitPage() != resultPage.getMaxPage() ) { %>
				<a href="/listProduct.do?page=<%=resultPage.getEndUnitPage()+1 %>&menu=<%=menu %>">&nbsp;다음</a>
			<% } %>

    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>






