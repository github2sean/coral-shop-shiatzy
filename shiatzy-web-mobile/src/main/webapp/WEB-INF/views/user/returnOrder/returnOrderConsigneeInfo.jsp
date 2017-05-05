<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left">选择退货方式</p>
    <a style="float: right;" href="#">< 我的账户</a>
</div>
<div class="verify-message">
    <div class="return-way clearfix">
        <h3>选择退货方式</h3>
        <p><a href="#"><img src="images/questionMark.png" alt="">退货说明</a></p>
    </div>
    <p>订单编号：<span>${order.orderNo}</span></p>
    <p>订单日期：<span>${order.orderTime}</span></p>
    <div class="verify-message-middle">
        <h2>退货商品*</h2>
        <c:forEach var="row" items="${cartList}">
        <div style="padding-left: 3rem;position:relative;" class="verify-main">
            <img src="images/verify_01.png" alt="">
            <div style="margin-left: 6rem;" class="img-message">
                <h3>${row.goodsName}</h3>
                <h6>${row.goodsCode}</h6>
                <div style="display: inline-block;" class="size">
                    <p style="float:left;margin-right: 3.0918rem;">黑色</p>
                    <p>M号</p>
                </div>
                <p>数量：${row.num}</p>
                <p>单价　${row.goodsPrice}</p>
            </div>
        </div>
        </c:forEach>
    </div>
    <p style="padding-left: 3rem;padding-right: 1.5rem;font-size: 0.9rem;margin-bottom: 0;">快遞費用<span style="float: right;">¥ 150</span></p>
    <div class="verify-message-bottom">
        <h2 style="text-align: right;padding-right: 1.5rem;">預計退回总额：¥ 12,000</h2>
    </div>
    <div class="returnWay" style="height: 20px">
        <a href="/returnOrder/listAddress"><h4>选择退貨方式*<span style="float: right;">></span></h4></a>
        <p>退貨方式：<span>
        <c:choose>
            <c:when test="${sessionScope.backWay==1}">
                快递退货
            </c:when>
            <c:when test="${sessionScope.backWay==2}">
                门店退货
            </c:when>
        </c:choose>

        </span></p>
        <p>取件地址：<span>${sessionScope.customerAddress.address}</span></p>
    </div>

    <div class="return-btn">
        <a href="#" class="returnBtn" data-value="${order.id}">確認退货</a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">> </span>
            <span style="float: left;">收到商品后7天内享有轻松退货政策</span>
        </a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">> </span>
            <span style="float: left;">隐私权政策</span>
        </a>
    </div>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function(){

        $(".returnBtn").click(function () {
            var orderId = $(this).attr("data-value");
            $.post("/returnOrder/applyReturn",{"orderId":orderId},function (data) {
                console.log(data);
                if(data.code==200){
                    location.href = "/returnOrder/details?orderId="+data.data;
                }
            });
            
        });

    });
</script>
