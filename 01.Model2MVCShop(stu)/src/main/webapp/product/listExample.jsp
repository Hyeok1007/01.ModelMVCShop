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
							��ǰ����
						</c:if>
						<c:if test="${ menu != 'manage' }">
							��ǰ �����ȸ
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
				<option value="0" ${ (searchVO.searchCondition == '0')?"selected":"" } >��ǰ��ȣ</option>
				<option value="1" ${ (searchVO.searchCondition == '1')?"selected":"" } >��ǰ��</option>
				<option value="2" ${ (searchVO.searchCondition == '2')?"selected":"" } >��ǰ����</option>
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
						<a href="javascript:fncGetProductList();">�˻�</a>
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
		<td colspan="11" >��ü ${ resultPage.totalCount } �Ǽ�, ���� ${ resultPage.currentPage } ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
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
						<!-- �Ǹ��ڵ尡 0�� �ƴϸ� ��ǰ���� �Ұ� -->
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
						�Ǹ���
					</c:if>
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '1' }">
						���ſϷ�
						<c:if test="${ menu == 'manage' }">
							-<a href="/updateTranCodeByProd.do?prodNo=${ list[size-1-i].prodNo }&currentPage=${ resultPage.currentPage }&tanscode=2&menu=${ menu }">����ϱ�</a>
						</c:if>
					</c:if>
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '2' }">
						�����
					</c:if>
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) == '3' }">
						��ۿϷ�
					</c:if>									
				</td>	
			</tr>
			<tr>
				<td colspan="11" bgcolor="D6D7D6" height="1"></td>
			</tr>
		</c:forEach>
	</c:if>
	<!-- ȸ��, ��ȸ�� -->
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
						�Ǹ���
					</c:if>
					<c:if test="${ fn:trim(list[size-1-i].proTranCode) != '0' }">
						������
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
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>






