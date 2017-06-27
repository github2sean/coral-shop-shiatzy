<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left"><spring:message code="returnOrderInfo.return"/></p>
    <a style="float: right;" href="/returnOrder/returnOrderInfo?orderId=${order.id}" >< <spring:message code="goBack"/></a>
</div>
<div class="verify-message">
    <div class="return-way clearfix">
        <h3><spring:message code="consignee.confirmBack"/></h3>
        <p><a href="#"><img src="${ctx}/static/images/questionMark.png" alt=""><spring:message
                code="returnOrderInfo.returnInfo"/></a></p>
    </div>
    <p><spring:message code="order.details.no"/>：<span>${order.orderNo}</span></p>
    <p><spring:message code="order.details.time"/>：<span><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></span></p>
    <div class="item-group">
        <h4 class="title j_dropdown"><spring:message code="consignee.returnGoods"/> <span class="arrow">></span></h4>
        <div class="item">
            <div class="goods-list return-goods">
                <c:forEach var="item" items="${cartList}">
                    <div class="goods-item clearfix ">
                        <div class="thumb">
                            <img src="${ImageModel.toFirst(item.goodsItemDomain.thumb).file}" alt="">
                        </div>
                        <div class="goods-info">
                            <div class="name">${item.goodsDomain.name}&nbsp;&nbsp;&nbsp;&nbsp;</div>
                            <p class="code"><spring:message code="shoppingCart.no"/>：${item.goodsCode}</p>
                            <p style="float:left;margin-right: 3.0918rem;">${sessionScope.language=='en_US'?item.goodsItemDomain.enName:item.goodsItemDomain.name}</p>
                            <p><spring:message code="shoppingCart.size"/>
                                ：${sessionScope.language=='en_US'?item.sizeDomain.enName:item.sizeDomain.name}</p>
                            <p class=""><spring:message code="shoppingCart.number"/>：${item.num} &nbsp;</p>
                            <p class=""><spring:message code="shoppingCart.unitPrice"/>：<font class="coinSymbol">
                                <c:choose>
                                    <c:when test="${order.currentCode=='CNY'}">
                                        &nbsp;<spring:message code="coin.ZH"/>
                                    </c:when>
                                    <c:when test="${order.currentCode=='USD'}">
                                        &nbsp;<spring:message code="coin.USA"/>
                                    </c:when>
                                    <c:when test="${order.currentCode=='EUR'}">
                                        &nbsp;<spring:message code="coin.EU"/>
                                    </c:when>
                                </c:choose>
                            </font>&nbsp;<fmt:formatNumber value="${item.goodsPrice}" pattern="#,###"/></p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <p style="padding-left: 3rem;padding-right: 1.5rem;font-size: 0.9rem;margin-bottom: 0;margin-top: 0.4rem;"><spring:message code="payment.failed.fee"/><span style="float: right;">&nbsp;<font class="coinSymbol">
                    <c:choose>
                        <c:when test="${order.currentCode=='CNY'}">
                            &nbsp;<spring:message code="coin.ZH"/>
                        </c:when>
                        <c:when test="${order.currentCode=='USD'}">
                            &nbsp;<spring:message code="coin.USA"/>
                        </c:when>
                        <c:when test="${order.currentCode=='EUR'}">
                            &nbsp;<spring:message code="coin.EU"/>
                        </c:when>
                    </c:choose>
                </font>&nbsp;<fmt:formatNumber value="${empty row.shipFee?0:row.shipFee}" pattern="#,###"/></span></p>
        </div>
    </div>

    <!--预计退回总额-->
    <div class="verify-message-bottom" style=" border-top: 2px solid #cccccc;">
        <h2 style="text-align: right;padding-right: 1.5rem;"><spring:message code="consignee.preBackAmt"/>：&nbsp;<font class="coinSymbol">
            <c:choose>
                <c:when test="${order.currentCode=='CNY'}">
                    &nbsp;<spring:message code="coin.ZH"/>
                </c:when>
                <c:when test="${order.currentCode=='USD'}">
                    &nbsp;<spring:message code="coin.USA"/>
                </c:when>
                <c:when test="${order.currentCode=='EUR'}">
                    &nbsp;<spring:message code="coin.EU"/>
                </c:when>
            </c:choose>
        </font>&nbsp;<fmt:formatNumber value="${preBackMoney}" pattern="#,###"/></h2>
    </div>


    <div class="returnWay">

        <a href="/returnOrder/chooseReturnWay"><h4><spring:message code="consignee.selectBackWay"/>&nbsp;*<span style="float: right;">></span></h4></a>
        <p><spring:message code="consignee.backWay"/>：<span data-value="${sessionScope.backWay}" id="backWay">
        <c:choose>
            <c:when test="${backWay==1}">
                <spring:message code="consignee.shippingbackWay"/>
            </c:when>
            <c:when test="${backWay==2}">
                <spring:message code="consignee.storebackWay"/>
            </c:when>
        </c:choose>
        </span></p>
        <p><spring:message code="consignee.backAddress"/>：<span class="addressInfo">
        <c:choose>
            <c:when test="${backWay==1}">
                ${return_order.shipAddress}
            </c:when>
            <c:when test="${backWay==2}">
                ${sessionScope.language=='en_US'?return_order.storeDomain.enAddress:return_order.storeDomain.address}
            </c:when>
        </c:choose>
        </span></p>
    </div>

    <div class="return-btn">
        <a href="#" class="returnBtn btn-default" data-value="${order.id}"><spring:message code="orderinfo.next"/></a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;margin-left: -10px">> </span>
            <span style="float: left;"><spring:message code="order.details.7day"/></span>
        </a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;margin-left: -10px">> </span>
            <span style="float: left;" class="privacyNotice"><spring:message code="privacyPolicy"/></span>
        </a>
    </div>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function(){

        $(".j_dropdown").on("click", function () {
            $(this).toggleClass("active");
            $(this).next().slideToggle();
        });

        $(".returnBtn").click(function () {
            $(this).css("color","#333");
            var method = $("#backWay").attr("data-value");
            if(method==''){
                layer.msg("选择退货方式");
                $(this).css("color","#333");
                return false;
            }
            var orderId = $(this).attr("data-value");
            $.post("/returnOrder/applyReturn",{"orderId":orderId},function (data) {
                console.log(data);
                if(data.code==200){
                    location.href = "/returnOrder/details?orderId="+data.data;
                }else{
                    layer.msg(data.message);
                }
            });
        });

    });
</script>
