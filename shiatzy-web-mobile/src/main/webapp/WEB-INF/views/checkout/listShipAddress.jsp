<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-shipping">
    <div class="dx-title">送货地址设定 <a href="javascript:history.go(-1)">回上页</a></div>
    <div class="content">
        <a href="/checkout/createShipAddress" class="new-address">新增地址 +</a>
        <c:forEach var="row" items="${addressList}" varStatus="num">
            <div class="address addressDiv" >
                <div class="title">地址${num.count} <span data-value="${row.id}" class="icon iconfont" style="background-color: inherit;border:1px solid #333">&#xe618;</span></div>
                <div class="text clearfix">
                    <div class="text-left">
                        <div class="postcode">${row.title}</div>
                        <div class="street">${row.address}</div>
                        <div class="tel">电话：${row.phone}</div>
                    </div>
                    <div class="text-right">
                        <div class="compile"><a href="/checkout/updateShipAddress?addressId=${row.id}" class="updateBtn" data-value="${row.id}">编辑 ></a></div>
                        <div class="del delBtn"><a href="#" class="delBtn" data-value="${row.id}">删除 -</a></div>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>
    <div class="btn">
        <a href="/checkout/confirm" type="button" class="accounts-btn">&lt; 确认</a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function(){

        $(".iconfont").click(function () {
            $(this).css("background-color","#333");
            $(this).parents(".addressDiv").siblings().find(".iconfont").css({"background-color":"white","border":"1px solid black"});
            var id = $(this).attr("data-value");
            $.post("/checkout/setAddress",{"addressId":id},function (data) {
                console.log(data);
                if(data.code==200){

                }
            });
        });

        $(".delBtn").click(function () {
            var $now = $(this);
            var id = $now.attr("data-value");
            $.post("/checkout/removeAddress",{"addressId":id},function (data) {
                console.log(data);
                if(data.code==200){
                    $now.parents(".addressDiv").remove();
                }
            });
        });

    });
</script>
