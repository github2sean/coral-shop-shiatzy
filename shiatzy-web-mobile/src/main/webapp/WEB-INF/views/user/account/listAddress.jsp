<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-shipping">
    <div class="dx-title">運送地址设定 <a href="/u/account/index">回上页</a></div>
    <div class="content">
        <a href="/checkout/toAddAddress" class="new-address">新增地址 +</a>
       <c:forEach var="row" items="${addressList}">
        <div class="address addressDiv" >
            <div class="title">地址一 <span class="icon iconfont">&#xe618;</span></div>
            <div class="text clearfix">
                <div class="text-left">
                    <div class="postcode">248</div>
                    <div class="street">${row.address}</div>
                    <div class="tel">电话：${row.phone}</div>
                </div>
                <div class="text-right">
                    <div class="compile"><a href="/checkout/toUpdateAddress?addressId=${row.id}" class="updateBtn" data-value="${row.id}">编辑 ></a></div>
                    <div class="del "><a href="" class="delBtn" data-value="${row.id}">删除 -</a></div>
                </div>
            </div>
        </div>
       </c:forEach>

    </div>
    <%--<div class="btn">
        <a href="购物车.结算页.html" type="button" class="accounts-btn">< 确认</a>
    </div>--%>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function(){

        $(".delBtn").click(function () {
            var $this = $(this);
            var addressId = $this.attr("data-value");

            $.post("/checkout/removeAddress",{"addressId":addressId},function (data) {
                if(data.code==200){
                    $this.parents(".addressDiv").remove();
                }else{
                    alert(data.message);
                }

            });
        });

    });
</script>
