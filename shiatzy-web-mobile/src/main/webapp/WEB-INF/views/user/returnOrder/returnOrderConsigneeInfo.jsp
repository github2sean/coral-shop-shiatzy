<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left"><spring:message code="consignee.confirmBack"/></p>
    <a style="float: right;" href="/returnOrder/returnOrderInfo?orderId=${order.id}" >< <spring:message code="goBack"/></a>
</div>
<div class="verify-message">
    <div class="return-way clearfix">
        <h3><spring:message code="consignee.confirmBack"/></h3>
        <p><a href="#"><img src="images/questionMark.png" alt=""><spring:message code="returnOrderInfo.returnInfo"/></a></p>
    </div>
    <p><spring:message code="order.details.no"/>：<span>${order.orderNo}</span></p>
    <p><spring:message code="order.details.time"/>：<span><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></span></p>
    <div class="verify-message-middle">
        <h2><spring:message code="consignee.returnGoods"/></h2>
        <c:forEach var="row" items="${cartList}">
        <div style="padding-left: 3rem;position:relative;" class="verify-main">
            <img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="" style="width: 80px;height: 100px;float: left">
            <div style="margin-left: 6rem;" class="img-message">
                <h3>${sessionScope.language=='en_US'?row.goodsDomain.enName:row.goodsDomain.name}</h3>
                <h6>${row.goodsCode}</h6>
                <div style="display: inline-block;" class="size">
                    <p style="float:left;margin-right: 3.0918rem;">${sessionScope.language=='en_US'?row.goodsItemDomain.enName:row.goodsItemDomain.name}</p>
                    <p><spring:message code="shoppingCart.size"/> &nbsp;${sessionScope.language=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}</p>
                </div>
                <p><spring:message code="shoppingCart.number"/>：${row.num}</p>
                <p><spring:message code="shoppingCart.unitPrice"/>&nbsp;<font class="coinSymbol">
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
                </font>&nbsp;<fmt:formatNumber value="${row.goodsPrice}" pattern="#,###"/></p>
            </div>
        </div>
        </c:forEach>
    </div>
    <p style="padding-left: 3rem;padding-right: 1.5rem;font-size: 0.9rem;margin-bottom: 0;"><spring:message code="payment.failed.fee"/><span style="float: right;">&nbsp;<font class="coinSymbol">
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
    <div class="verify-message-bottom">
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
    <div class="returnWay" style="height: 20px">
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
        <a href="#" class="returnBtn" data-value="${order.id}"><spring:message code="consignee.sureBack"/></a>
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
